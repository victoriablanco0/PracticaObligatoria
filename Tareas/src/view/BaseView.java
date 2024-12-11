package view;

import controller.Controller;


//Clase Vista

public abstract class BaseView {
    protected Controller controller;

    public abstract void init() throws Exception;

    public abstract void showMenu() throws Exception;

    public abstract void showMessage(String mensaje);

    public abstract void showErrorMessage(String mensajeError);

    public abstract void end();

    public void setController(Controller controller){
        this.controller = controller;
    }

}
