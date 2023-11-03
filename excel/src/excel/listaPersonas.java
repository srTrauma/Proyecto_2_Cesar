package excel;
import java.util.ArrayList;

public class listaPersonas {
    public ArrayList<ArrayList<String>> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(ArrayList<ArrayList<String>> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    private ArrayList<ArrayList<String>> listaPersonas = new ArrayList<ArrayList<String>>();
}
