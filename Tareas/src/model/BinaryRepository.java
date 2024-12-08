package model;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class BinaryRepository implements IRepository{
    private final File file = new File(System.getProperty("user.home"), "tasks.bin");
    private ArrayList<Task> tareas = new ArrayList<>();
    
    
    @Override
    public Task addTask(Task t) throws RepositoryException {
        tareas.add(t);
        return t;
    }
    @Override
    public void removeTask(Task t) throws RepositoryException {
        tareas.remove(t);
    }
    
    @Override
    public void modifyTask(Task t) throws RepositoryException {
        
    }

    @Override
    public ArrayList<Task> getAllTasks() throws RepositoryException {
        return tareas;
    }





    
}
