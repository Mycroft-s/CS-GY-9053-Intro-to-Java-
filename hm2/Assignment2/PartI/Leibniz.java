public class Leibniz {

    public static double estimatePi(int iterations) {
        double sum = 0.0;
        for (int n = 0; n < iterations; n++) {
            // (-1)^n / (2n+1)
            double term = Math.pow(-1, n) / (2 * n + 1);
            sum += term;
        }
        return 4 * sum;
    }

    public static void main(String[] args) {
        int iterations = 0;
        double piEstimate = 0.0;

        do {
            iterations++;
            piEstimate = estimatePi(iterations);
        } while (Math.abs(Math.PI - piEstimate) > 1e-5);

        System.out.printf("pi is estimated as %.5f after %d iterations%n", piEstimate, iterations);
    }
}
