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

    public static void main(String[] args) throws IOException {
        Persona persona = new Persona();
        listaPersonas list = new listaPersonas();
        Boolean initCondition = true;

        while (initCondition) {
            System.out.println("Eliga una opcion: ");
            System.out.println("1. Mostrar la lista");
            System.out.println("2. confirmar compra");
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
                        System.out.println("inserte su nombre: ");
                        String perNombre = sc.next();
                        persona.setNombre(perNombre);
                        System.out.println("Hola "+ perNombre +"\n¿ Cual es su DNI ? ");
                        String dniPer = sc.next();
                        persona.setDni(dniPer);
                        
                        persona.setListaCompra(recogerDatosExcel());
                        System.out.println(recogerDatosExcel()); 
                        setListaArrayListStrings.add(dniPer);
                        setListaArrayListStrings.add(perNombre);
                        persona.setListaCompra(setListaArrayListStrings);


                        list.setListaPersonas(null);// devuelve la lista de la iteracion actual, hacer metodo para mostrar
                        break;
                    case 3:
                        System.out.println("saliendo del programa");
                        initCondition = false;
                        break;
                    default:
                        break;
                }
                
            } catch (InputMismatchException e) {
                
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
            fis = new FileInputStream(
                    new File("Productos.xlsx"));
              
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
                                System.out.print(
                                        "\t" + (int) cell.getNumericCellValue() + "\t\t");
                                break;
                            case STRING:
                                if (cell.getStringCellValue().length() < 8) {
                                    System.out.print(
                                            "\t" + cell.getStringCellValue() + "\t\t");
                                } else {
                                    System.out.print(
                                            "\t" + cell.getStringCellValue() + "\t");
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

     public static ArrayList<String> recogerDatosExcel() {
        ArrayList<String> returnStrings = new ArrayList<String>();
        XSSFRow row;
        FileInputStream fis;
        try {
            fis = new FileInputStream(
                    new File("Productos.xlsx"));
              
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
                            
                            int a = ((int)cell.getNumericCellValue());
                            Integer s = ((Integer)a);
                            String devolver = s.toString();
                               returnStrings.add(devolver);
                                break;
                            case STRING:
                               returnStrings.add(cell.getStringCellValue());

                                break;
                            default:
                                System.out.println("Error, tipo de dato no soportado: " + cell.getColumnIndex());
                        }
                    }

                }
                
            }
            workbook.close();
            return returnStrings;
        } catch (Exception e) {
            System.out.println("Error");
            return returnStrings;
            
        }
    }
}