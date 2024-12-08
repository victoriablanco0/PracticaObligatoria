package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BinaryRepository implements IRepository{

    private ArrayList<Task> tareas = new ArrayList<>();
    
    
    @Override
    public boolean addTask(Task t) throws RepositoryException {
        boolean existe = true;
        for(Task tareaYaIntroducida : tareas){
            if(tareaYaIntroducida.getIdentifier() != t.getIdentifier()){
                tareas.add(t);
            }else{existe = false;
            }
        }
        return existe; 
        
    }


    @Override
    public ArrayList<Task> getAllTasks() throws RepositoryException {
        return tareas;
    }
    
    @Override
    public boolean removeTask(UUID identifier) throws RepositoryException {
        boolean existe = true;
            for(Task tareaYaIntroducida : tareas){
                if(tareaYaIntroducida.getIdentifier()== identifier){
                    tareas.remove(tareaYaIntroducida);
                    existe = true;
                
                }
            }
            return existe; 
        }
        
    


    @Override
    public Task modifyTask(UUID identifier) throws RepositoryException {
        Task tareaCambiar = null;
       for(Task tareaYaIntroducida : tareas){
        if(tareaYaIntroducida.getIdentifier().equals(identifier)){
            tareaCambiar = tareaYaIntroducida;
        }     
        
       } 
       return tareaCambiar;
    }


    @Override
    public boolean completarTarea(UUID identifier) {
        boolean existe = true;
        for(Task tareaYaIntroducida : tareas){
            if(tareaYaIntroducida.getIdentifier().equals(identifier)){
                tareaYaIntroducida.completarTarea();
                existe=true;
        
            }
        }
        return existe; 
    }



    public List<Task> getOtraTarea(){
        List<Task> otraTarea = new ArrayList<>(tareas.size());
        for (Task task : tareas) {
            otraTarea.add(new Task(task));
        }
        return otraTarea;
    }

}







    

