import java.io.File;
public class Driver {
    public static void main(String[] args) {
        // Polynomial p = new Polynomial();
        // System.out.println(p.evaluate(3));
        // double[] c1 = { 7, 2, 8};
        // int[] idx1 = { 2, 0, 1};
        // Polynomial p1 = new Polynomial(c1, idx1);
        // double[] c2 = {8, 6, 5};
        // int[] idx2 = { 2, 1, 0};
        // Polynomial p2 = new Polynomial(c2, idx2);
        // Polynomial s = p2.multiply(p1);
        // p1.Print();
        // p2.Print();
        // s.Print();
        Polynomial pr = new Polynomial(new File("file.txt"));
        pr.saveToFile("testoutput");

    }
}
