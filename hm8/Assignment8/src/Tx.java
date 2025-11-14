public class Tx {
    private final String id;
    private final String account;
    private final long epochSec;
    private final long cents;

    public Tx(String id, String account, long epochSec, long cents) {
        this.id = id;
        this.account = account;
        this.epochSec = epochSec;
        this.cents = cents;
    }

    public String getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public long getEpochSec() {
        return epochSec;
    }

    public long getCents() {
        return cents;
    }
}
