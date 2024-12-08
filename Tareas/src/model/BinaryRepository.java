package model;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class BinaryRepository implements IRepository{
    private final File file = new File(System.getProperty("user.home"), "tasks.bin");
    private ArrayList<Task> tareas = new ArrayList<>();




    @Override
    public boolean agregarTarea(Task t) throws RepositoryException {
        tareas.add(t);
        return t;
    }

    @Override
    public boolean eliminarTarea(UUID identifier) {
        return tareas.remove(identifier);
    
    }

    @Override
    public Task modificarTarea(UUID identifier) throws RepositoryException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificarTarea'");
    }

    @Override
    public ArrayList<Task> obtenerTareas() throws RepositoryException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerTareas'");
    }

    
}
