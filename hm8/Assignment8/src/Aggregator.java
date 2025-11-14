import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Aggregator implements Runnable {
    private final BlockingQueue<MsgTx> inQueue;
    private final int numValidators;

    private final Map<String, Boolean> seenIds = new HashMap<>();
    private final Map<AggKey, Long> totals = new HashMap<>();

    public Aggregator(BlockingQueue<MsgTx> inQueue, int numValidators) {
        this.inQueue = inQueue;
        this.numValidators = numValidators;
    }

    @Override
    public void run() {
        int stopSeen = 0;
        try {
            while (stopSeen < numValidators) {
                MsgTx msg = inQueue.take();
                if (msg.isStop()) {
                    stopSeen++;
                    continue;
                }

                Tx tx = msg.getTx();
                if (tx == null) continue;

                if (seenIds.containsKey(tx.getId())) {
                    continue;
                }
                seenIds.put(tx.getId(), Boolean.TRUE);

                AggKey key = new AggKey(tx.getAccount(), tx.getEpochSec());
                long current = totals.getOrDefault(key, 0L);
                totals.put(key, current + tx.getCents());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Map<AggKey, Long> snapshotTotals() {
        return new HashMap<>(totals);
    }
}
