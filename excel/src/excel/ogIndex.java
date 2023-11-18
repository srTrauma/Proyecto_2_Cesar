package excel;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ogIndex {
    static persona persona = new persona();
    static double total = 0.0;
    static String perNombre = "";

    public static void main(String[] args) throws IOException {
        
        Boolean initCondition = true;
        while (initCondition) {
            System.out.println("Eliga una opcion: ");
            System.out.println("1. Mostrar la lista");
            System.out.println("2. Confirmar compra");
            System.out.println("3. Salir\n");
            System.out.println("Inserte su accion:");

            try {
                Scanner sc = new Scanner(System.in);
                int option = sc.nextInt();
                switch (option) {
                    case 1:
                        excel.mostrarExcel();
                        break;

                    case 2:
                    excel.recogerDatosExcel();
                       
                    break;
                    case 3:
                        System.out.println("Saliendo del programa");
                        initCondition = false;
                        break;

                    default:
                        System.out.println("Algo salió mal, pruebe otra vez");
                }
                
            } catch (InputMismatchException e) {
                System.out.println("------------------------------------------");
                System.out.println("Operacion no valida, inserte un numero");
                System.out.println("------------------------------------------");
            }
        }
    }
}
