package model;
import java.util.ArrayList;
import java.util.List;

//COMPLETO

public interface IExporter {

    public boolean exportarTareas(List<Task> tareas) throws ExporterException;
    public List<Task> importarTareas() throws ExporterException;

}
