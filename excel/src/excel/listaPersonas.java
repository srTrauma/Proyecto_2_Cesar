package excel;
import java.util.ArrayList;

public class listaPersonas {
    public ArrayList<persona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(ArrayList<persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    private ArrayList<persona> listaPersonas = new ArrayList<persona>();
}
