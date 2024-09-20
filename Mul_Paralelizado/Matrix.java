public class Matrix {

    private int ROWS;
    private int COLS;
    private double[][] M;

    //------------------------------------------------------
    //------------------------------------------------------
    public Matrix(double[][] M) {
        this.M = M;
        this.ROWS = M.length;
        this.COLS = M[0].length;
    }

    //------------------------------------------------------
    //------------------------------------------------------
    public double GetCell(int i, int j) {
        return M[i][j];
    }

    //------------------------------------------------------
    //------------------------------------------------------
    public void SetCell(int i, int j, double val) {
        M[i][j] = val;
    }

    //------------------------------------------------------
    //------------------------------------------------------
    public int getRows() {
        return ROWS;
    }

    //------------------------------------------------------
    //------------------------------------------------------
    public int getCols() {
        return COLS;
    }

    //------------------------------------------------------
    //------------------------------------------------------
    void imprimir() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.printf("%12.2f", M[i][j]);
            }
            System.out.println();
        }
    }

    //------------------------------------------------------
    //------------------------------------------------------
    public synchronized void incrementar(int fil, int col, double cant) {
        M[fil][col] = M[fil][col] + cant;
    }

    //------------------------------------------------------
    //------------------------------------------------------
    public double prodEsc(int col1, int col2, int filIni, int filFin) {
        double resp = 0;
        for (int k = filIni; k <= filFin; k++) {
            resp += (M[k][col1] * M[k][col2]);
        }
        return resp;
    }

    //------------------------------------------------------
    //------------------------------------------------------
    public double prodEsc(int col1, int col2) {
        double resp = 0;
        for (int k = 0; k < ROWS; k++) {
            resp += (M[k][col1] * M[k][col2]);
        }
        return resp;
    }

    //------------------------------------------------------
    //------------------------------------------------------
    Matrix prod(Matrix B) {
        int col = B.getCols();
        Matrix C = new Matrix(new double[ROWS][col]);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < col; j++) {
                for (int k = 0; k < COLS; k++) {
                    double x = GetCell(i, k) * B.GetCell(k, j);
                    C.SetCell(i, j, C.GetCell(i, j) + x);
                }
            }
        }
        return C;
    }

    //------------------------------------------------------
    //------------------------------------------------------
    // Función que calcula el determinante de una matriz
    private double determinante(double[][] matriz) {
        int n = matriz.length;
        if (n == 1) {
            return matriz[0][0]; // Caso base: determinante de una matriz 1x1 es el elemento
        }
        if (n == 2) {
            // Determinante de una matriz 2x2
            return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
        }

        // Caso general para matrices nxn
        double det = 0;
        for (int j = 0; j < n; j++) {
            det +=
            Math.pow(-1, j) *
            matriz[0][j] *
            determinante(submatriz(matriz, 0, j));
        }
        return det;
    }

    // Función auxiliar que crea una submatriz eliminando la fila y la columna indicadas
    private double[][] submatriz(double[][] matriz, int fila, int columna) {
        int n = matriz.length;
        double[][] submat = new double[n - 1][n - 1];
        int r = -1;
        for (int i = 0; i < n; i++) {
            if (i == fila) continue;
            r++;
            int c = -1;
            for (int j = 0; j < n; j++) {
                if (j == columna) continue;
                submat[r][++c] = matriz[i][j];
            }
        }
        return submat;
    }

    //------------------------------------------------------
    //------------------------------------------------------
    // Función que verifica si la matriz es cuadrada
    public boolean esCuadrada() {
        return ROWS == COLS;
    }

    //------------------------------------------------------
    //------------------------------------------------------
    // Función que determina si la matriz es invertible
    public boolean esInvertible() {
        if (!esCuadrada()) {
            return false; // Si no
            // Si la matriz no es cuadrada, no puede ser invertible
        }

        // Calcular el determinante
        double det = determinante(M);

        // Si el determinante es 0, la matriz no es invertible
        return det != 0;
    }

    public static void main(String[] args) {
        double[][] A1, B1;
        // Leer la matriz desde el archivo
        A1 = DataSet.ReadFile(DataSet.DATAFILE_A, DataSet.filasA, DataSet.columnasA);  
        B1 = DataSet.ReadFile(DataSet.DATAFILE_B, DataSet.filasB, DataSet.columnasB); 


        // Crear una instancia de Matrix con los datos leídos
        Matrix A = new Matrix(A1);
        Matrix B = new Matrix(B1);

        // Verificar si la matriz es invertible
        if (A.esInvertible()) {
            System.out.println("La matriz A es invertible.");
        } else {
            System.out.println("La matriz NO es invertible.");
        }
        
        if (B.esInvertible()) {
            System.out.println("La matriz B es invertible.");
        } else {
            System.out.println("La matriz NO es invertible.");
        }
    }
}
