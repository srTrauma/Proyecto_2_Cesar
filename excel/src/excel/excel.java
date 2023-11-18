package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excel {

    static String file = "excel/Productos.xlsx";
    static FileInputStream fis;
    static XSSFRow row;
    static String DNI = "";
    static String IdProductoDevolverS = "";
    static String productPriceS = "";
    static Scanner sc = new Scanner(System.in);
    static int userInput = 0;

    public static void mostrarExcel() {

        try {
            // Abre un flujo de entrada para el archivo "Productos.xlsx" ubicado en la
            // carpeta "excel"
            fis = new FileInputStream(new File(
                    file));
            // Crea un objeto XSSFWorkbook para trabajar con el archivo Excel
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            // Obtiene la primera hoja del archivo Excel
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            // Obtiene un iterador de filas en la hoja
            Iterator<Row> rowIterator = spreadsheet.iterator();
            while (rowIterator.hasNext()) {
                // Procesa una fila a la vez
                row = (XSSFRow) rowIterator.next();
                // Obtiene un iterador de celdas en la fila
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    // Procesa una celda a la vez
                    Cell cell = cellIterator.next();
                    int cellColumn = cell.getColumnIndex();
                    // Verifica si la celda se encuentra en la columna 0 o 1
                    if (cellColumn == 0 || cellColumn == 1) {
                        // Dependiendo del tipo de dato en la celda, imprime su contenido
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                // Si es numérico, imprime el valor como entero
                                System.out.print("\t" + (int) cell.getNumericCellValue() + "\t\t");
                                break;
                            case STRING:
                                // Si es una cadena, ajusta el formato de impresión según su longitud
                                if (cell.getStringCellValue().length() < 8) {
                                    System.out.print("\t" + cell.getStringCellValue() + "\t\t");
                                } else {
                                    System.out.print("\t" + cell.getStringCellValue() + "\t");
                                }
                                break;
                            default:
                                // En caso de que el tipo de dato no sea numérico ni cadena, imprime un mensaje
                                // de error
                                System.out.println("Error, tipo de dato no soportado: " + cell.getColumnIndex());
                        }
                    }
                }
                // Imprime un salto de línea al final de cada fila
                System.out.println();
            }
            // Cierra el libro de trabajo de Excel
            workbook.close();
        } catch (Exception e) {
            // En caso de cualquier excepción, imprime la pila de errores
            e.printStackTrace();
        }
    }

    public static void recogerDatosExcel() {
        Integer nigus = 0;
        
        Boolean keepCase2 = true;
        double total = 0.0;
        do {

            excel.mostrarExcel();
            System.out.println("Inserte el ID del producto que quiere comprar , escriba salir si no quiere comprar");

            userInput = sc.nextInt();

            if (nigus == 0) {
                persona persona = new persona();
            System.out.println("Hola, cual es su nombre");
            String nombre = sc.next();
            System.out.println("Hola " + nombre + "\n cual es su dni :");
            System.out.println("INSERTE SOLO LAS PRIMERAS 8 CIFRAS");
            int dni = sc.nextInt();

            System.out.println("Inserte la letra de su dni");
            String letra_Dni = sc.next();
            try {
                Integer a = Integer.parseInt(letra_Dni);

            } catch (Exception el) {
                DNI = dni + letra_Dni;

                System.out.println();
                ArrayList<String> productInfo = new ArrayList<>();

                String productNameSS = "";

                double returnPrice = 0.0 + persona.getTotal();

                try {
                    // Abre el archivo Excel para lectura.
                    fis = new FileInputStream(new File(
                            file));
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
                                    persona.setListaCompra(productInfo);
                                    persona.setDni(DNI);
                                    persona.setNombre(nombre);
                                    persona.setTotal(returnPrice);

                                    System.out.println(persona.getNombre());
                                    System.out.println(persona.getDni());
                                }
                            }
                        }
                    }

                    // Imprime información relevante.
                    System.out.println("Producto : " + productNameSS);
                    System.out.println("Precio : " + returnPrice);
                    System.out.println("Total : " + ((int) persona.getTotal()));
                    System.out.println(persona.getDni());
                    System.out.println("-------------------");

                    System.out.println("Quieres seguir comprando ¿?");
                    System.out.println("1. Continuar");
                    System.out.println("2. Salir");
                    int as = sc.nextInt();
                    if (as == 1) {
                        keepCase2 = true;
                    } else {
                        keepCase2 = false;
                    }

                    workbook.close();

                } catch (IOException k) {
                    System.out.println("Error al acceder al archivo Excel");

                }
                nigus ++;
            }
        }

    }while (keepCase2);
            }
            
}
