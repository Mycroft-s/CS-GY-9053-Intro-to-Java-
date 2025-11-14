import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PipelineApp {

    public static void main(String[] args) throws InterruptedException {
        final int QUEUE_CAP = 256;
        final int NUM_VALIDATORS = 2;

        BlockingQueue<MsgTx> qReaderToVal = new ArrayBlockingQueue<>(QUEUE_CAP);
        BlockingQueue<MsgTx> qValToError = new ArrayBlockingQueue<>(QUEUE_CAP);
        BlockingQueue<MsgTx> qValToAgg = new ArrayBlockingQueue<>(QUEUE_CAP);

        String txFile = "transactions_with_errors.csv"; // transactions.csv
        String errorFile = "errors.csv";

        Reader reader = new Reader(txFile, qReaderToVal, NUM_VALIDATORS);
        ErrorWriter errorWriter = new ErrorWriter(qValToError, errorFile, NUM_VALIDATORS);
        Aggregator aggregator = new Aggregator(qValToAgg, NUM_VALIDATORS);

        Validator validator1 = new Validator(qReaderToVal, qValToError, qValToAgg);
        Validator validator2 = new Validator(qReaderToVal, qValToError, qValToAgg);

        Thread tReader = new Thread(reader, "Reader");
        Thread tVal1 = new Thread(validator1, "Validator-1");
        Thread tVal2 = new Thread(validator2, "Validator-2");
        Thread tError = new Thread(errorWriter, "ErrorWriter");
        Thread tAgg = new Thread(aggregator, "Aggregator");

        tReader.start();
        tVal1.start();
        tVal2.start();
        tError.start();
        tAgg.start();

        // wait
        tReader.join();
        tVal1.join();
        tVal2.join();
        tError.join();
        tAgg.join();

        // print top10
        Map<AggKey, Long> totals = aggregator.snapshotTotals();
        List<Map.Entry<AggKey, Long>> entries = new ArrayList<>(totals.entrySet());
        entries.sort(Comparator.comparingLong(Map.Entry<AggKey, Long>::getValue)
                .reversed());

        System.out.println("Top 10 aggregates:");
        int count = 0;
        for (Map.Entry<AggKey, Long> e : entries) {
            System.out.printf("%s          -> %d cents%n",
                    e.getKey().toString(), e.getValue());
            count++;
            if (count >= 10) break;
        }

        System.out.println("Done.");
    }
}
