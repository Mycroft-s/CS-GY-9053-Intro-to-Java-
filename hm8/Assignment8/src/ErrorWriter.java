import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ErrorWriter implements Runnable {
    private final BlockingQueue<MsgTx> inQueue;
    private final String outFile;
    private final int numValidators; 

    public ErrorWriter(BlockingQueue<MsgTx> inQueue,
                       String outFile,
                       int numValidators) {
        this.inQueue = inQueue;
        this.outFile = outFile;
        this.numValidators = numValidators;
    }

    @Override
    public void run() {
        int stopSeen = 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {
            //  header
            bw.write("id,account,epochSec,cents,error");
            bw.newLine();

            while (stopSeen < numValidators) {
                MsgTx msg = inQueue.take();
                if (msg.isStop()) {
                    stopSeen++;
                    continue;
                }
                Tx tx = msg.getTx();
                String err = msg.getError();
                if (err == null) {
                    continue;
                }

                bw.write(tx.getId() + "," +
                         tx.getAccount() + "," +
                         tx.getEpochSec() + "," +
                         tx.getCents() + "," +
                         err);
                bw.newLine();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
