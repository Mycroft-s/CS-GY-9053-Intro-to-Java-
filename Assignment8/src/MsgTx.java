public class MsgTx {
    private final Tx tx;
    private final boolean stop;
    private String error; 

    private MsgTx(Tx tx, boolean stop) {
        this.tx = tx;
        this.stop = stop;
    }

    public static MsgTx of(Tx tx) {
        return new MsgTx(tx, false);
    }

    public static MsgTx stopMessage() {
        return new MsgTx(null, true);
    }

    public Tx getTx() {
        return tx;
    }

    public boolean isStop() {
        return stop;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
