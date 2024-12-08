package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//COMPLETO



public interface IRepository {
    public boolean agregarTarea(Task t) throws RepositoryException;

    public boolean eliminarTarea(UUID identifier);

    public Task modificarTarea(UUID identifier) throws RepositoryException;

    public ArrayList<Task> obtenerTareas() throws RepositoryException;

}
