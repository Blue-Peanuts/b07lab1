import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Polynomial {
    private double[] coefficients;
    private int[] exponents;

    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
    }

    public void print() {
        System.out.println("\n");
        System.out.println("expo");
        for (int i = 0; i < exponents.length; i++)
            System.out.println(exponents[i] + "\t");
        System.out.println("coeff");
        for (int i = 0; i < coefficients.length; i++)
            System.out.println(coefficients[i] + "\t");
        System.out.println("comb");
        for (int i = 0; i < coefficients.length; i++)
            System.out.println(exponents[i] + " " + coefficients[i] + "\t");
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) {
        try {
            Polynomial cum = new Polynomial();

            Scanner scanner = new Scanner(file);
            String data = scanner.next();

            String[] splitTemp = data.split("\\+");
            String[][] splitFull = new String[splitTemp.length][];
            for (int i = 0; i < splitTemp.length; i++) {
                splitFull[i] = splitTemp[i].split("-");
                for (int j = 1; j < splitFull[i].length; j++)
                    splitFull[i][j] = "-" + splitFull[i][j];
            }
            for (int i = 0; i < splitFull.length; i++) {
                for (int j = 0; j < splitFull[i].length; j++) {
                    if(splitFull[i][j].length() == 0)
                        continue;
                    boolean hasX = splitFull[i][j].contains("x");

                    String[] splitFrag = splitFull[i][j].split("x");

                    double[] subCoefficients = { Double.parseDouble(splitFrag[0]) };

                    int[] subExponents = { 0 };
                    if (hasX)
                        subExponents[0] = 1;
                    if (splitFrag.length == 2)
                        subExponents[0] = Integer.parseInt(splitFrag[1]);

                    cum = cum.add(new Polynomial(subCoefficients, subExponents));
                }
            }
            scanner.close();
            this.exponents = cum.exponents;
            this.coefficients = cum.coefficients;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Polynomial add(Polynomial other) {
        int[] temp_exponents = new int[this.exponents.length + other.exponents.length];
        double[] temp_coefficients = new double[this.exponents.length + other.exponents.length];
        int size = 0;
        for (int i = 0; i < temp_exponents.length; i++)
            temp_exponents[i] = -1;
        for (int i = 0; i < this.exponents.length; i++) {
            for (int j = 0; j < temp_exponents.length; j++)
                if (temp_exponents[j] == -1 || temp_exponents[j] == this.exponents[i]) {
                    if (temp_exponents[j] == -1)
                        size++;

                    temp_exponents[j] = this.exponents[i];
                    temp_coefficients[j] = this.coefficients[i];
                    break;
                }
        }
        for (int i = 0; i < other.exponents.length; i++) {
            for (int j = 0; j < temp_exponents.length; j++)
                if (temp_exponents[j] == -1 || temp_exponents[j] == other.exponents[i]) {
                    if (temp_exponents[j] == -1)
                        size++;

                    temp_exponents[j] = other.exponents[i];
                    temp_coefficients[j] += other.coefficients[i];
                    break;
                }
        }
        double[] coefficients = new double[size];
        int[] exponents = new int[size];
        for (int i = 0; i < size; i++) {
            coefficients[i] = temp_coefficients[i];
            exponents[i] = temp_exponents[i];
        }
        return new Polynomial(coefficients, exponents);
    }

    public double evaluate(double x) {
        double ans = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            ans += Math.pow(x, exponents[i]) * coefficients[i];
        }

        return ans;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial other) {
        int[] temp_exponents = new int[this.exponents.length * other.exponents.length];
        double[] temp_coefficients = new double[this.exponents.length * other.exponents.length];

        for (int i = 0; i < temp_exponents.length; i++) {
            temp_exponents[i] = -1;
            temp_coefficients[i] = 0;
        }

        int size = 0;
        for (int i = 0; i < exponents.length; i++) {
            for (int j = 0; j < other.exponents.length; j++) {
                for (int k = 0;; k++) {
                    if (temp_exponents[k] == -1 ||
                            temp_exponents[k] == this.exponents[i] + other.exponents[j]) {
                        if (temp_exponents[k] == -1)
                            size++;
                        temp_exponents[k] = this.exponents[i] + other.exponents[j];
                        temp_coefficients[k] += this.coefficients[i] * other.coefficients[j];
                        break;
                    }
                }
            }
        }
        double[] coefficients = new double[size];
        int[] exponents = new int[size];
        for (int i = 0; i < size; i++) {
            coefficients[i] = temp_coefficients[i];
            exponents[i] = temp_exponents[i];
        }
        return new Polynomial(coefficients, exponents);
    }

    public void saveToFile(String fileName) {
        String data = "";
        for (int i = 0; i < coefficients.length; i++) {
            if(coefficients[i] >= 0 && i > 0)
                data += "+";
            data += coefficients[i];
            if (exponents[i] == 0) {

            } else if (exponents[i] == 1) {
                data += "x";
            } else {
                data += "x";
                data += exponents[i];
            }
        }
        try {
            PrintStream printStream = new PrintStream(fileName + ".txt");
            printStream.print(data);
            printStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}