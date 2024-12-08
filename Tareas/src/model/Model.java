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
    

    public boolean addTask(Task tarea) throws RepositoryException{
        return repository.addTask(tarea);

    }

    public boolean removeTask(UUID identifier) throws RepositoryException{
        return repository.removeTask(identifier);
    }

    public Task modifyTask(UUID identifier) throws RepositoryException{
        return repository.modifyTask(identifier);
    }

    public boolean exportarTareas() throws ExporterException{
        return exporter.exportarTareas(tareas);
    }



    public boolean importarTareas() throws ExporterException, RepositoryException{
        

       List<Task> tareasImportadas=exporter.importarTareas();
        for(Task tarea:tareasImportadas){
            repository.addTask(tarea);
        }
        boolean exito=true;
        if(tareasImportadas==null){
            exito=false;
        }
        return exito;
    }


    public List<Task> getAllTasks() throws RepositoryException{
       return repository.getAllTasks();
       
       
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
