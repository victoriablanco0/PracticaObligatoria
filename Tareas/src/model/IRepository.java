package model;

import java.util.ArrayList;
import java.util.UUID;

//COMPLETO



public interface IRepository {

    public boolean addTask(Task t) throws RepositoryException;

    public boolean removeTask(UUID identifier) throws RepositoryException;

    public Task modifyTask(UUID identifier) throws RepositoryException;

    public ArrayList<Task> getAllTasks() throws RepositoryException;

    public boolean completarTarea(UUID identifier);

}
