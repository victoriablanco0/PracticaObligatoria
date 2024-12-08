package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//COMPLETO



public interface IRepository {

    public Task addTask(Task t) throws RepositoryException;

    public void removeTask(Task t) throws RepositoryException;

    public void modifyTask(Task t) throws RepositoryException;

    public ArrayList<Task> getAllTasks() throws RepositoryException;

}
