package model;

//COMPLETO

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Model {
    private ArrayList<Task> tareas;
    private IRepository repository;
    private IExporter exporter;


    //constructor
    public Model(IRepository repository, IExporter exporter){
        this.repository = repository;
        this.exporter = exporter;
        tareas = new ArrayList<>();
    }

    

    public boolean agregarTarea(Task tarea) throws RepositoryException{
        return repository.agregarTarea(tarea);

    }

    public boolean eliminarTarea(UUID identifier){
        return repository.eliminarTarea(identifier);
    }

    public Task modificarTarea(UUID identifier) throws RepositoryException{
        return repository.modificarTarea(identifier);
    }

    public boolean tareasCompletadas(UUID identifier){
        return repository.tareasCompletadas(identifier);
    }

    public boolean exportarTareasCSV(){
        return repository.exportarTareasCSV(tareas);
    }



    public boolean importarTareasCSV(){
        return repository.importarTareasCSV();
    }


    public List<Task> obtenerTareas() throws RepositoryException{
       return repository.obtenerTareas();
       
       
        /* List<Task> listaCopia = new ArrayList<>(tareas.size());
        for(Task tarea : tareas){
            listaCopia.add(new Task(tarea));
        }

        return listaCopia;*/
    }



    public boolean completarTarea(UUID identifierCompletar) {
        return repository.completarTarea(identifierCompletar);
    }


}
