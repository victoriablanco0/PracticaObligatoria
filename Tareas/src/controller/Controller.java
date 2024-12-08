package controller;

//CREO QUE COMPLETO

import java.util.List;
import java.util.UUID;

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
        //menú principal
        view.mostrarMenu();
    }

    public void end(){
        view.end();
    }

   public boolean agregarTarea(Task tarea) throws RepositoryException{
        return model.agregarTarea(tarea);
   }

   public boolean eliminarTarea(UUID identifier){
        return model.eliminarTarea(identifier);
   }

   public Task modificarTarea(UUID identifier) throws RepositoryException{
        return model.modificarTarea(identifier);
   }

   public boolean importarTareasCSV(){
        return model.importarTareasCSV();
    }

    public boolean exportarTareasCSV(){
        return model.exportarTareasCSV();
    }

    public List<Task> obtenerTareasDelModelo() throws RepositoryException{
        return model.obtenerTareas();
    }

    public boolean completarTarea(UUID identifierCompletar) {
        return model.completarTarea(identifierCompletar);
    }

    public boolean exportarTareasJSON() {
        return model.exportarTareasJSON();

    }

    public boolean importarTareasJSON() {
        return model.importarTareasJSON();

    }


}
