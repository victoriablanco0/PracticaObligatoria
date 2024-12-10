package controller;

//CREO QUE COMPLETO

import java.util.List;
import java.util.UUID;

import model.ExporterException;
import model.Model;
import model.RepositoryException;
import model.Task;
import view.BaseView;

public class Controller {
    Model model;
    BaseView view;

    //Método constructor
    public Controller(BaseView view, Model model){
        this.view = view;
        this.model = model;
        
        view.setController(this);
    }

    public void run() throws Exception{
        //Carga inicial del programa
        if(model.cargarEstadoAplicación()){
            view.showMessage("Cargado estado anterior con exito");
        }else{
            view.showMessage("No se encontró fichero para carga del programa");
        }

        //menú principal
        view.showMenu();


        // Guardado final del programa
        if(model.guardarEstadoAplicación()){
            view.end();
        }else{
            view.showMessage("No se pudo guardar el estado de la aplicación.\nSaliendo...");
        }

    }

    public void end(){
        view.end();
    }

   public boolean addTask(Task tarea) throws RepositoryException{
        return model.addTask(tarea);
   }

   public boolean removeTask(UUID identifier) throws RepositoryException{
        return model.removeTask(identifier);
   }

   public Task modifyTask(UUID identifier) throws RepositoryException{
        return model.modifyTask(identifier);
   }

   public boolean importarTareas() throws ExporterException, RepositoryException{
        return model.importarTareas();
    }

    public boolean exportarTareas() throws ExporterException, RepositoryException{
        return model.exportarTareas();
    }

    public List<Task> getAllTasks() throws RepositoryException{
        return model.getAllTasks();
    }

    public boolean completarTarea(UUID identifierCompletar) {
        return model.completarTarea(identifierCompletar);
    }

  

}
