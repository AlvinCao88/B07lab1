
public class Polynomial {
    double[] coef;

    public Polynomial() {
        coef = new double[]{0};
    }

    public Polynomial(double[] new_coef) {
        this.coef = new_coef;
    }

    public Polynomial add(Polynomial polynomial) {
        int maxLength = Math.max(coef.length, polynomial.coef.length);
        double[] result = new double[maxLength];
        for (int i = 0; i < coef.length; i++) {
            result[i] += coef[i];
        }
        for (int i = 0; i < polynomial.coef.length; i++) {
            result[i] += polynomial.coef[i];
        }
        return new Polynomial(result);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coef.length; i++) {
            result += coef[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}