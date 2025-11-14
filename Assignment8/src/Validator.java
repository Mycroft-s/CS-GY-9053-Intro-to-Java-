import java.util.concurrent.BlockingQueue;

public class Validator implements Runnable {
    private final BlockingQueue<MsgTx> inQueue;
    private final BlockingQueue<MsgTx> errorQueue;
    private final BlockingQueue<MsgTx> aggQueue;

    public Validator(BlockingQueue<MsgTx> inQueue,
                     BlockingQueue<MsgTx> errorQueue,
                     BlockingQueue<MsgTx> aggQueue) {
        this.inQueue = inQueue;
        this.errorQueue = errorQueue;
        this.aggQueue = aggQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                MsgTx msg = inQueue.take();
                if (msg.isStop()) {
                    errorQueue.put(MsgTx.stopMessage());
                    aggQueue.put(MsgTx.stopMessage());
                    break;
                }

                Tx tx = msg.getTx();
                String err = validate(tx);
                if (err != null) {
                    msg.setError(err);
                    errorQueue.put(msg);
                } else {
                    aggQueue.put(msg);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String validate(Tx tx) {
        if (tx.getId() == null || tx.getId().isEmpty()) {
            return "missing_id";
        }
        if (tx.getAccount() == null || tx.getAccount().isEmpty()) {
            return "missing_account";
        }
        if (tx.getCents() < 0 || tx.getCents() > 10_000_000L) { 
            return "amount_out_of_range";
        }
        if (tx.getEpochSec() <= 0) {
            return "invalid_time";
        }
        return null; // ok
    }
}
