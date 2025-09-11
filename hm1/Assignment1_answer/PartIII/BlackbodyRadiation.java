public class BlackbodyRadiation {

    public static final double PLANCK = 6.626e-34;
    public static final double C = 3.0e8;
    public static final double BOLTZMANN = 1.381e-23;

    public static double calculateEnergy(double frequency, double temperature) {
        double numerator = 2 * PLANCK * Math.pow(frequency, 3) / (C * C);
        double denominator = Math.exp((PLANCK * frequency) / (BOLTZMANN * temperature)) - 1.0;
        return numerator / denominator;
    }

    public static void main(String[] args) {
        double wavelengthBetelgeuse = 970e-9;
        double wavelengthSun = 500e-9;

        double freqBetelgeuse = C / wavelengthBetelgeuse;
        double freqSun = C / wavelengthSun;

        double tempBetelgeuse = 3500;
        double tempSun = 5800;

        double I_Betelgeuse = calculateEnergy(freqBetelgeuse, tempBetelgeuse);
        double I_Sun = calculateEnergy(freqSun, tempSun);

        System.out.println("I for the Sun is " + I_Sun);
        System.out.println("I for Betelgeuse is " + I_Betelgeuse);
    }
}
