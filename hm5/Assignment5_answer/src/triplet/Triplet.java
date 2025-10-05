package triplet;

public class Triplet<A extends Number, B extends Number, C extends Number>
        implements Comparable<Triplet<A, B, C>> {

    private A first;
    private B second;
    private C third;

    public Triplet(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() { return first; }
    public B getSecond() { return second; }
    public C getThird() { return third; }

    public void setFirst(A v) { first = v; }
    public void setSecond(B v) { second = v; }
    public void setThird(C v) { third = v; }

    private double mag2() {
        double x = first.doubleValue();
        double y = second.doubleValue();
        double z = third.doubleValue();
        return x*x + y*y + z*z;
    }

    @Override
    public int compareTo(Triplet<A, B, C> o) {
        return Double.compare(this.mag2(), o.mag2());
    }

    @Override
    public String toString() {
        return "Triplet(" + first + ", " + second + ", " + third + ")";
    }
}
