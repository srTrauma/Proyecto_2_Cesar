package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class index {
    static Persona persona = new Persona();
    static double total = 0.0;
    static String perNombre = "";

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este programa).
     * @throws IOException Excepción lanzada en caso de errores de E/S.
     */
    public static void main(String[] args) throws IOException {
        listaPersonas list = new listaPersonas();
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
                        mostrarExcel();
                        break;

                    case 2:
                        ArrayList<String> setListaArrayListStrings = new ArrayList<String>();
                        System.out.println("----------------------------");
                        System.out.println("Inserte su nombre: ");
                        String perNombre = sc.next();
                        persona.setNombre("Nombre : " + perNombre);
                        try {
                            Integer.parseInt(perNombre);
                            System.out.println("Nombre no valido");
                        } catch (Exception e) {
                            System.out.println("Hola " + perNombre + "\n¿Cual es su DNI?, insertar solo los primeros 7 numeros ");
                            String dniPer = sc.next();
                            if (dniPer.length() != 7) {
                                System.out.println("No es un DNI valido");
                                System.out.println("-----------------------");
                                dniPer = "No es un DNI valido";
                                break;
                            }
                            try {
                                Integer.parseInt(dniPer);
                                System.out.println("¿Cual es la letra de su DNI?");
                                String ltrDni = sc.next();
                                if (ltrDni.length() != 1) {
                                    ltrDni = null;
                                    System.out.println("Letra no valida");
                                    break;
                                }
                                try {
                                    Integer.parseInt(ltrDni);
                                    System.out.println("No es una letra, intentelo otra vez");
                                } catch (Exception p) {
                                    dniPer = dniPer + ltrDni;
                                    persona.setDni(dniPer + ltrDni);
                                    System.out.println("Productos disponibles");
                                    mostrarExcel();
                                    Boolean keepBuying = true;
                                    while (keepBuying) {
                                        System.out.println("Inserte 1 para comprar, inserte 2 para salir: ");
                                        int buy = sc.nextInt();
                                        switch (buy) {
                                            case 1:
                                                System.out.println("Que producto quiere:\n");
                                                int userImput = sc.nextInt();

                                                setListaArrayListStrings.addAll(recogerDatosExcel(userImput));
                                                list.setListaPersonas(setListaArrayListStrings);
                                                break;

                                            case 2:
                                                System.out.println("La lista de la compra de " + perNombre + "\ncon DNI: " + dniPer + " es:");
                                                for (String listaPer : list.getListaPersonas()) {
                                                    System.out.println(listaPer);
                                                }
                                                System.out.println("Su total es: " + total);
                                                keepBuying = false;
                                                break;

                                            default:
                                        }
                                    }
                                }
                            } catch (Exception j) {
                                System.out.println("El DNI no es valido, pruebe otra vez");
                                break;
                            }
                        }
                        setListaArrayListStrings.add(persona.getDni());
                        setListaArrayListStrings.add(perNombre);
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

    /**
     * Muestra los productos disponibles desde un archivo Excel.
     */
    public static void mostrarExcel() {
        XSSFRow row;
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File("excel\\Productos.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();
            while (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int cellColumn = cell.getColumnIndex();
                    if (cellColumn == 0 || cellColumn == 1) {
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                System.out.print("\t" + (int) cell.getNumericCellValue() + "\t\t");
                                break;
                            case STRING:
                                if (cell.getStringCellValue().length() < 8) {
                                    System.out.print("\t" + cell.getStringCellValue() + "\t\t");
                                } else {
                                    System.out.print("\t" + cell.getStringCellValue() + "\t");
                                }
                                break;
                            default:
                                System.out.println("Error, tipo de dato no soportado: " + cell.getColumnIndex());
                        }
                    }
                }
                System.out.println();
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Recoge datos de productos desde el archivo Excel en función de la entrada del usuario.
     *
     * @param userInput El ID del producto seleccionado por el usuario.
     * @return Una lista de información sobre el producto seleccionado.
     */
    /**
 * Recoge datos de productos desde el archivo Excel en función de la entrada del usuario.
 *
 * @param userInput El ID del producto seleccionado por el usuario.
 * @return Una lista de información sobre el producto seleccionado.
 */
public static ArrayList<String> recogerDatosExcel(int userInput) {
    // Lista para almacenar la información del producto seleccionado.
    ArrayList<String> productInfo = new ArrayList<>();

    XSSFRow row;
    FileInputStream fis;
    String productNameSS = "";
    String IdProductoDevolverS = "";
    String productPriceS = "";
    double returnPrice = 0.0;

    try {
        // Abre el archivo Excel para lectura.
        fis = new FileInputStream(new File("excel\\Productos.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = spreadsheet.iterator();

        boolean firstRow = true;

        while (rowIterator.hasNext()) {
            row = (XSSFRow) rowIterator.next();

            // Ignora la primera fila (encabezados).
            if (firstRow) {
                firstRow = false;
                continue;
            }

            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int cellCol = cell.getColumnIndex();

                if (cellCol == 1) {
                    int productID = (int) cell.getNumericCellValue();
                    Integer g = ((Integer) productID);
                    String IdProductoDevolver = g.toString();

                    // Si el ID del producto coincide con la entrada del usuario.
                    if (productID == userInput) {
                        // Obtiene el nombre del producto y su precio.
                        String productName = row.getCell(0).getStringCellValue();
                        double productPrice = row.getCell(3).getNumericCellValue();
                        total = productPrice + total;

                        // Agrega la información del producto a la lista.
                        productInfo.add(productName);
                        productNameSS = productName;
                        productInfo.add(IdProductoDevolver);
                        IdProductoDevolverS = IdProductoDevolver;
                        productInfo.add(String.valueOf(productPrice));
                        int a = (int) productPrice;
                        returnPrice = (double) a;
                    }
                }
            }
        }

        // Imprime información relevante.
        System.out.println(productNameSS);
        System.out.println(returnPrice);
        System.out.println("Total : " + total);
        System.out.println("-------------------");

        // Cierra el archivo Excel.
        workbook.close();

        return productInfo;
    } catch (IOException e) {
        System.out.println("Error al acceder al archivo Excel");
        return productInfo;
    }
}

}