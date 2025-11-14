import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Reader implements Runnable {
    private final String filename;
    private final BlockingQueue<MsgTx> outQueue;
    private final int numValidators;

    public Reader(String filename,
                  BlockingQueue<MsgTx> outQueue,
                  int numValidators) {
        this.filename = filename;
        this.outQueue = outQueue;
        this.numValidators = numValidators;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                // skip header
                if (first && line.toLowerCase().startsWith("id")) {
                    first = false;
                    continue;
                }
                first = false;

                Tx tx = parse(line);
                if (tx == null) {
                    continue;
                }
                outQueue.put(MsgTx.of(tx));
            }

            // 
            for (int i = 0; i < numValidators; i++) {
                outQueue.put(MsgTx.stopMessage());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private Tx parse(String line) {
        int idx = line.indexOf('#');
        if (idx >= 0) {
            line = line.substring(0, idx);
        }
        line = line.trim();
        if (line.isEmpty()) {
            return null;
        }

        String[] parts;
        if (line.contains(",")) {
            parts = line.split(",");
        } else {
            parts = line.split("\\s+");
        }

        if (parts.length < 4) {
            return null;
        }

        String id = parts[0].trim();
        String account = parts[1].trim();

        String epochStr = parts[2].split("\\s+")[0].trim();
        String centsStr = parts[3].split("\\s+")[0].trim();

        try {
            long epoch = Long.parseLong(epochStr);
            long cents = Long.parseLong(centsStr);
            return new Tx(id, account, epoch, cents);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
