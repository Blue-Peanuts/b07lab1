public class Polynomial {
    private double[] coefficients;

    public Polynomial() {
        coefficients = new double[0];
    }
    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other)
    {
        double[] new_coeffiecient = new double[Math.max(this.coefficients.length, other.coefficients.length)];
        for(int i = 0; i < new_coeffiecient.length; i++)
        {
            new_coeffiecient[i] = (this.coefficients.length > i ? this.coefficients[i] : 0) + (other.coefficients.length > i ? other.coefficients[i] : 0);
        }
        return new Polynomial(new_coeffiecient);
    }

    public double evaluate(double x) {
        double ans = 0;
        double cumX = 1;
        for(int i = 0; i < this.coefficients.length; i++)
        {
            ans += this.coefficients[i] * cumX;
            cumX *= x;
        }

        return ans;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}