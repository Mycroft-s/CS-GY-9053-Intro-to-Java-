import java.util.*;

public class SpiralMatrix {

    public static int[] spiralMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[] ans = new int[m * n];
        int idx = 0;

        int top = 0, bottom = m - 1, left = 0, right = n - 1;

        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) ans[idx++] = matrix[top][j];
            top++;

            for (int i = top; i <= bottom; i++) ans[idx++] = matrix[i][right];
            right--;

            if (top <= bottom) {
                for (int j = right; j >= left; j--) ans[idx++] = matrix[bottom][j];
                bottom--;
            }

            if (left <= right) {
                for (int i = bottom; i >= top; i--) ans[idx++] = matrix[i][left];
                left++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] matrix = {
            { 1,  2,  3,  4},
            { 5,  6,  7,  8},
            { 9, 10, 11, 12},
            {13, 14, 15, 16},
            {17, 18, 19, 20}
        };

        System.out.println("Matrix (row by row):");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%3d ", matrix[i][j]);
            }
            System.out.println();
        }

        int[] spiral = spiralMatrix(matrix);
        System.out.println("\nSpiral order:");
        System.out.println(Arrays.toString(spiral));
    }
}
