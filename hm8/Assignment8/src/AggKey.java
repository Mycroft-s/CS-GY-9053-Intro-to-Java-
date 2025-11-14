import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

public class AggKey {
    private final String account;
    private final LocalDate day;

    public AggKey(String account, long epochSec) {
        this.account = account;
        this.day = Instant.ofEpochSecond(epochSec)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public String getAccount() {
        return account;
    }

    public LocalDate getDay() {
        return day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AggKey)) return false;
        AggKey aggKey = (AggKey) o;
        return Objects.equals(account, aggKey.account) &&
               Objects.equals(day, aggKey.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, day);
    }

    @Override
    public String toString() {
        return account + " @ " + day.toString();
    }
}
