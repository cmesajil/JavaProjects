public class MatrixMultiplicationSerial {

    //--------------------------------------------------------------
    //--------------------------------------------------------------
    public static void Process_Serial() {
        long Time1, Time2;
        double x;
        double[][] A1, B1;

        // Leer matrices A y B desde sus respectivos archivos
        A1 = DataSet.ReadFile(DataSet.DATAFILE_A, DataSet.filasA, DataSet.columnasA);  
        B1 = DataSet.ReadFile(DataSet.DATAFILE_B, DataSet.filasB, DataSet.columnasB);  

        // Verificar si las matrices son compatibles para multiplicación
        if (DataSet.columnasA != DataSet.filasB) {
            System.out.println("Las matrices no son compatibles para la multiplicación.");
            return;
        }

        Time1 = System.currentTimeMillis();
        Matrix A = new Matrix(A1);
        Matrix B = new Matrix(B1);
        int filasA = A.getRows();
        int columnasA = A.getCols();
        int columnasB = B.getCols();
        
        // Crear matriz de resultado C (filas de A x columnas de B)
        Matrix C = new Matrix(new double[filasA][columnasB]);

        // Realizar la multiplicación de matrices
        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                x = 0;  // Inicializar el valor de la celda
                for (int k = 0; k < columnasA; k++) {
                    x += A.GetCell(i, k) * B.GetCell(k, j);  // Multiplicar fila de A por columna de B
                }
                C.SetCell(i, j, x);  // Asignar el resultado en la matriz C
            }
        }

        // Guardar el resultado en un archivo
        DataSet.WriteFile(C, "Matriz C - Multiplicación Serial");
        Time2 = System.currentTimeMillis();
        
        // Mostrar el tiempo de procesamiento
        System.out.printf("Tiempo de Procesamiento Serial: %d ms%n", (Time2 - Time1));
    }

    //--------------------------------------------------------------
    //--------------------------------------------------------------
    public static void main(String[] args) {
        Process_Serial();
    }
    //--------------------------------------------------------------
    //--------------------------------------------------------------
}
