public class MatrixMultiplicationParallel {

    public static final int HILOS = 2;

    //--------------------------------------------------------------
    public static void main(String[] args) {
        long Time1, Time2;
        double[][] A1 = DataSet.ReadFile(DataSet.DATAFILE_A, DataSet.filasA, DataSet.columnasA); // Leer matriz A
        double[][] B1 = DataSet.ReadFile(DataSet.DATAFILE_B, DataSet.filasB, DataSet.columnasB); // Leer matriz B

        if (DataSet.columnasA != DataSet.filasB) {
            System.out.println("Las matrices no son compatibles para la multiplicación.");
            return;
        }

        Time1 = System.currentTimeMillis();
        Matrix A = new Matrix(A1);
        Matrix B = new Matrix(B1);
        Matrix C = new Matrix(new double[A.getRows()][B.getCols()]);

        // Crear los hilos para la multiplicación paralela
        Thread[] threads = new Thread[HILOS];
        for (int t = 0; t < HILOS; t++) {
            threads[t] = new Thread(new MultiplicationTask(t + 1, A, B, C));
            threads[t].start();
        }

        // Esperar a que todos los hilos terminen
        try {
            for (int t = 0; t < HILOS; t++) {
                threads[t].join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        Time2 = System.currentTimeMillis();
        
        // Escribir la matriz resultante
        DataSet.WriteFile(C, "Matriz C - Multiplicación Paralela");
        System.out.printf("Tiempo de Procesamiento Paralelo: %d ms%n", (Time2 - Time1));
    }
}

//--------------------------------------------------------------
class MultiplicationTask implements Runnable {

    private Matrix A;
    private Matrix B;
    private Matrix C;
    private int filIni;
    private int filFin;

    public MultiplicationTask(int nroHilo, Matrix A, Matrix B, Matrix C) {
        this.A = A;
        this.B = B;
        this.C = C;
        
        // Dividir las filas entre los hilos
        filIni = ((nroHilo - 1) * A.getRows()) / MatrixMultiplicationParallel.HILOS;
        filFin = (nroHilo * A.getRows()) / MatrixMultiplicationParallel.HILOS - 1;
    }

    @Override
    public void run() {
        for (int i = filIni; i <= filFin; i++) {
            for (int j = 0; j < B.getCols(); j++) {
                double sum = 0;
                for (int k = 0; k < A.getCols(); k++) {
                    sum += A.GetCell(i, k) * B.GetCell(k, j);
                }
                C.SetCell(i, j, sum);
            }
        }
    }
}
