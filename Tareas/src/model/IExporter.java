package model;
import java.util.ArrayList;

//COMPLETO

public interface IExporter {

    public boolean exportarTareas(ArrayList<Task> tareas) throws ExporterException;
    public ArrayList<Task> importarTareas() throws ExporterException;

}
