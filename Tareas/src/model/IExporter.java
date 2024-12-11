package model;
import java.util.List;


//Interfaz para exportar/importar tareas; o bien en CSV o bien en JSON

public interface IExporter {

    public boolean exportarTareas(List<Task> tareas) throws ExporterException;
    public List<Task> importarTareas() throws ExporterException;

}
