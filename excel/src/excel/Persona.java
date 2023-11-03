package excel;
import java.util.ArrayList;

public class Persona {
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    private String nombre;
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    private String dni;
    private ArrayList<String> ListaCompra = new ArrayList<String>();
    public ArrayList<String> getListaCompra() {
        return ListaCompra;
    }
    public void setListaCompra(ArrayList<String> listaCompra) {
        ListaCompra = listaCompra;
    }
}
