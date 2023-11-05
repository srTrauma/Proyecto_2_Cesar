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
    // variables que usaremos despues, son static para poder usarlas en otros
    // metodos del index
    static Persona persona = new Persona();
    static double total = 0.0;
    static String perNombre = "";

    public static void main(String[] args) throws IOException {
        // creamos una lista vacia de tipo listaPersonas
        listaPersonas list = new listaPersonas();
        // creamos una variable de tipo booleana verdadera
        Boolean initCondition = true;
        // mientras sea verdadera ejecuta el codigo que esta dentro de los corchetes
        while (initCondition) {
            System.out.println("Eliga una opcion: ");
            System.out.println("1. Mostrar la lista");
            System.out.println("2. Confirmar compra");
            System.out.println("3. Salir\n");
            System.out.println("Inserte su accion:");

            try {
                Scanner sc = new Scanner(System.in);
                // recogemos la opcion que quiere el usuario
                int option = sc.nextInt();
                switch (option) {
                    case 1:
                        // Ejecuta este codigo en caso de que el usuario inserte 1
                        mostrarExcel();
                        break;

                    case 2:
                        // Ejecuta este codigo en caso de que el usuario inserte 2
                        // creamos un arraylist vacio ya que lo necesitamos poara el objeto
                        // listaPersonas
                        ArrayList<String> setListaArrayListStrings = new ArrayList<String>();
                        System.out.println("----------------------------");
                        System.out.println("Inserte su nombre: ");
                        // recogemos el nombre de la persona
                        String perNombre = sc.next();
                        persona.setNombre("Nombre : " + perNombre);
                        // probamos si el nombre introducido es correcto parseandolo a integer si esto
                        // da error todo bien si no ejecuta lo del try
                        try {
                            Integer.parseInt(perNombre);
                            System.out.println("Nombre no valido");
                        } catch (Exception e) {
                            System.out.println(
                                    "Hola " + perNombre + "\n¿Cual es su DNI?, insertar solo los primeros 7 numeros ");
                            // el usuario inserta el dni
                            String dniPer = sc.next();
                            // comprueba la longitud de los numeros del dni y en caso de que NO sea 7
                            // ejecuta lo de dentro en cualquier otro caso continua
                            if (dniPer.length() != 8) {
                                System.out.println("No es un DNI valido");
                                System.out.println("-----------------------");
                                dniPer = "No es un DNI valido";
                                break;
                            }
                            try {
                                // prueba a pasar a numero los numeros introducidos en caso correcto continua en
                                // caso de error ejecuta el catch de abajo, el ultimo
                                Integer.parseInt(dniPer);
                                System.out.println("¿Cual es la letra de su DNI?");
                                String ltrDni = sc.next();
                                // le pasa un String para la letra
                                if (ltrDni.length() != 1) {
                                    ltrDni = null;
                                    System.out.println("Letra no valida");
                                    break;
                                }
                                try {
                                    // prueba a pasar a numero la letra en caso de que se pueda, da fallo sino sigue
                                    Integer.parseInt(ltrDni);
                                    System.out.println("No es una letra, intentelo otra vez");
                                } catch (Exception p) {
                                    // concatenamos los numeros del dni y la letra
                                    dniPer = dniPer + ltrDni;
                                    // guardamos estos datos en el objeto persona
                                    persona.setDni(dniPer + ltrDni);
                                    System.out.println("Productos disponibles");
                                    // ejecutamos la funcion para mostrar nuestra lista del excel
                                    mostrarExcel();
                                    // creamos una variable boolean verdadera
                                    Boolean keepBuying = true;
                                    // mientras la variable anterior sea cierta sigue comprando
                                    while (keepBuying) {
                                        System.out.println("Inserte 1 para comprar\nInserte 2 para salir: ");
                                        // seteamos una variable para ver si quiere seguir comprando
                                        int buy = sc.nextInt();
                                        // comprueba el valor para ver que ejecutar
                                        switch (buy) {
                                            case 1:
                                                System.out.println("Que producto quiere:\n");
                                                // aqui pide el id del producto para comprar
                                                int userImput = sc.nextInt();
                                                // añadimos datos al arralist que hicimos antes con la funcion
                                                // recogerdatos, que acepta el userimput y ya saca por pantalla lo que
                                                // elija el usuario
                                                setListaArrayListStrings.addAll(recogerDatosExcel(userImput));
                                                // seteamos el arraylist de persona con el resultado de la ejecucion
                                                // anteriror
                                                list.setListaPersonas(setListaArrayListStrings);
                                                break;

                                            case 2:
                                                // en este caso salimos de la ejecucion, mostrando el nombre, y la lista
                                                // de la compra de la persona, en orden nombreProducto, idProducto y
                                                // precio
                                                System.out.println("La lista de la compra de " + perNombre
                                                        + "\ncon DNI: " + dniPer + " es:");
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
                        // añadimos el nombre y el dni a nuestro array
                        setListaArrayListStrings.add(persona.getDni());
                        setListaArrayListStrings.add(perNombre);
                        break;

                    case 3:
                        // Ejecuta este codigo en caso de que el usuario inserte 3
                        System.out.println("Saliendo del programa");
                        // Cierra la condicion inicial del bucle y coratara el bucle inicial while
                        initCondition = false;
                        break;

                    default:
                        System.out.println("Algo salió mal, pruebe otra vez");
                }
            } catch (InputMismatchException e) {
                // En caso de que algun input salga mal ejecuta este codigo
                System.out.println("------------------------------------------");
                System.out.println("Operacion no valida, inserte un numero");
                System.out.println("------------------------------------------");
            }
        }
    }

    public static void mostrarExcel() {
        XSSFRow row;
        FileInputStream fis;
        try {
            // Abre un flujo de entrada para el archivo "Productos.xlsx" ubicado en la
            // carpeta "excel"
            fis = new FileInputStream(new File("excel\\Productos.xlsx"));
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