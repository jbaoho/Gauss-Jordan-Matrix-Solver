import java.util.Scanner;

public class Gauss_Jordan {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a positive integer for the dimension of your square matrix:");
        int n = scan.nextInt();
        // initialize the array representing the system of equations from the given problem
        int[][] A = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("Please enter the integer you would like for row %d column %d of your matrix:", (i + 1), (j + 1));
                A[i][j] = scan.nextInt();
            }
        }

        // initialize the column vector representing the solutions to the system of equations for A
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.printf("Please enter the integer value you would like for entry number %d of the column vector representing the matrix's solution:", i);
            b[i] = scan.nextInt();
        }

        // solve the system of equations
        int[] x = solve(A, b);

        // print the solution
        System.out.println("The solution to the given system of equations is given by:");
        for (int i = 0; i < x.length; i++) {
            System.out.println("x" + (i + 1) + " = " + x[i]);
        }
    }

    public static int[] solve(int[][] a, int[] b) {

        /*
        Algorithm from textbook:
        //Implements Gaussian elimination with partial pivoting
        //Input: Matrix A[1..n, 1..n] and column-vector b[1..n]
        //Output: An equivalent upper-triangular matrix in place of A and the
        //corresponding right-hand side values in place of the (n + 1)st column

        for i ← 1 to n do A[i, n + 1] ← b[i] //appends b to A as the last column
        for i ← 1 to n − 1 do
            pivotrow ← i
            for j ← i + 1 to n do
                if |A[j, i]| > |A[pivotrow, i]| pivotrow ← j
            for k ← i to n + 1 do
                swap(A[i, k], A[pivotrow, k])
            for j ← i + 1 to n do
                temp ← A[j, i] / A[i, i]
                for k ← i to n + 1 do
                    A[j, k]← A[j, k]− A[i, k]∗ temp
         */

        int n = a.length;
        double[][] A = new double[n][n + 1];
        // append b to A as the last column
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (j < n) A[i][j] = a[i][j];
                else A[i][j] = b[i];
            }
        }

        for (int i = 0; i < n; i++) {

            // find the largest element in the ith column to determine the pivot row
            int pivotrow = i;
            if (i != n - 1) {
                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(A[j][i]) > Math.abs(A[pivotrow][i])) {
                        pivotrow = j;
                    }
                }

                // if the pivot row changed, swap the pivot row with the ith row
                for (int k = i; k < n + 1; k++) {
                    double t = A[i][k];
                    A[i][k] = A[pivotrow][k];
                    A[pivotrow][k] = t;
                }
            }

            // perform row reduction on each row that is not the pivot column
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double temp = A[j][i] / A[i][i];
                    for (int k = i; k < n + 1; k++) {
                        A[j][k] = A[j][k] - (A[i][k] * temp);
                    }
                }
            }
        }

        int[] x = new int[n];
        for (int i = 0; i < n; i++) {
            // divide each non-zero element by the pivot for each row such that A
            // becomes the identity matrix and b becomes the solution to the system
            A[i][n] /= A[i][i];
            A[i][i] /= A[i][i];

            // round each element of b to the nearest int after the solution is found
            // to correct for minor inconsistencies with Java math
            x[i] = (int) (A[i][n] + 0.5);

        }

        return x;
    }

}