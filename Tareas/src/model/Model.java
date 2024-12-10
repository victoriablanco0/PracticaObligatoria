package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Model {
    private ArrayList<Task> tareas;
    private IRepository repository;
    private IExporter exporter;
    File ficheroEstadoSerializado;



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

    public boolean exportarTareas() throws ExporterException, RepositoryException{
        return exporter.exportarTareas(getAllTasks());
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


    public boolean cargarEstadoAplicación() {

        if (ficheroEstadoSerializado.exists() && ficheroEstadoSerializado.isFile()) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(ficheroEstadoSerializado));
                this.tareas = (ArrayList<Task>) ois.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                // Dejamos el error para la depuración, por el canal err.
                System.err.println("Error durante la deserialización: " + ex.getMessage());
                return false;
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException ex) {
                        // Dejamos el error para la depuración, por el canal err.
                        System.err.println("Error durante la deserialización: " + ex.getMessage());
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }

    }


    public boolean guardarEstadoAplicación() {

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(ficheroEstadoSerializado));
            oos.writeObject(tareas);
            return true;
        } catch (IOException ex) {
            // Dejamos el error para la depuración, por el canal err.
            System.err.println("Error durante la serialización: " + ex.getMessage());
            return false;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    // Dejamos el error para la depuración, por el canal err.
                    System.err.println("Error al cerrar el flujo: " + ex.getMessage());
                    return false;
                }
            }
        }
    }
}
