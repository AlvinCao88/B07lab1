import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Polynomial {
    double[] coef;
    int[] expo;


    public Polynomial() {
        coef = new double[]{0};
        expo = new int[]{0};
    }


    public Polynomial(double[] new_coef, int [] new_expo) {
        this.coef = new_coef;
        this.expo = new_expo;
    }


    public Polynomial(File file) throws FileNotFoundException{
         Scanner input = new Scanner(file);
        String polynomialString = input.nextLine();
        input.close();
        String[] terms = polynomialString.split("(?=[+-])");
        coef = new double[terms.length];
        expo = new int[terms.length];
        for (int i = 0; i < terms.length; i++) {
            String term = terms[i];
            int exponent_number = 0;
            double coefficient_number;
            if (term.contains("x")) {
                String[] parts = term.split("x");
                coefficient_number = Double.parseDouble(parts[0]);
                exponent_number = Integer.parseInt(parts[1]);
            } 
            else {
                coefficient_number = Double.parseDouble(term);
            }
            coef[i] = coefficient_number;
            expo[i] = exponent_number   ;
        }

    }


    public Polynomial add(Polynomial polynomial) {
        int maxLength = coef.length + polynomial.coef.length;
        double[] result_coef = new double[maxLength];
        int[] result_expo = new int[maxLength];
        int i=0;
        int j=0;
        int k=0;
        int flag=0;
        for (i=0;i<coef.length;i++){
            result_coef[i]+=coef[i];
            result_expo[i] = expo[i];
        }
        for (j=0;j<polynomial.coef.length;j++){
            flag=0;
            for(k=0;k<i;k++){
                if(polynomial.expo[j]==result_expo[k]){
                    result_coef[k]+=polynomial.coef[j];
                    flag=1;
                }
            }
            if(flag==0){
                result_coef[i]+=polynomial.coef[j];
                result_expo[i]=polynomial.expo[j];
                i++;
            }
        }
        return new Polynomial(result_coef, result_expo);
    }


    public int findIndex (int[] expo_list, int expo){
        for (int i = 0; i < expo_list.length; i++) {
            if (expo_list[i] == expo) {
                return i;
            }
        }
        return -1;
    }


    public Polynomial multiply(Polynomial polynomial){
        int maxLength = coef.length + polynomial.coef.length;
        double[] result_coef = new double[maxLength];
        int[] result_expo = new int[maxLength];
        int new_expo=0;
        double new_coef=0;
        int i=0,j=0;
        int index=0;
        for(i=0;i<coef.length;i++){
            for(j=0;j<polynomial.coef.length;j++){
                new_expo = expo[i]+polynomial.expo[j];
                new_coef = coef[i]*polynomial.coef[j];
                index= findIndex(result_expo, new_expo);
                if (index == -1) {
                    result_expo[i + j] = new_expo;
                    result_coef[i + j] = new_coef;
                } else {
                    result_coef[index] += new_coef;
                }
            }
        }
        return new Polynomial(result_coef, result_expo);
    }


    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coef.length; i++) {
            result += coef[i] * Math.pow(x, expo[i]);
        }
        return result;
    }


    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }


    public void saveToFile(String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        StringBuilder polynomialString = new StringBuilder();
        for (int i = 0; i < coef.length; i++) {
            if (i != 0) {
                if (coef[i] >= 0) {
                    polynomialString.append("+");
                }
            }
            if (expo[i] == 0) {
                polynomialString.append(coef[i]);
            } else if (coef[i] == 1) {
                polynomialString.append("x").append(expo[i]);
            } else if (coef[i] == -1) {
                polynomialString.append("-x").append(expo[i]);
            } else {
                polynomialString.append(coef[i]).append("x").append(expo[i]);
            }
        }
        writer.write(polynomialString.toString());
        writer.close();
    }
}