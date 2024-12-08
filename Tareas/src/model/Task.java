package model;

//CREO QUE COMPLETO


import java.io.Serializable;
import java.time.*;
import java.util.Locale;
import java.util.UUID;

public class Task implements Serializable {
    private UUID identifier;
    private String title;
    private LocalDate date;
    private String content;
    private int priority;
    private int estimatedDuration;
    private boolean completed;

    //constructor con atributos
    public Task(UUID identifier, String title, LocalDate date, String content, int priority, int estimatedDuration,
            boolean completed) {
        this.identifier = UUID.randomUUID();
        this.title = title;
        this.date = date;
        this.content = content;
        this.priority = priority;
        this.estimatedDuration = estimatedDuration;
        this.completed = completed;
    }

    //constructor copia
    public Task(Task otraTarea){
        this.identifier = otraTarea.identifier;
        this.title = otraTarea.title;
        this.date = otraTarea.date;
        this.content = otraTarea.content;
        this.priority = otraTarea.priority;
        this.estimatedDuration = otraTarea.estimatedDuration;
        this.completed = otraTarea.completed;
    }

    public Task(){}


    public static Task getTaskFromString(String tareaString, String delimitador){
        String[] datos = tareaString.split(delimitador);
        if(datos.length !=7){
            return null;
        }
        
        try {
            UUID identifier = UUID.randomUUID();
            String identifierString = identifier.toString();
            identifierString = datos[0];
            String title = datos[1];
            LocalDate date = LocalDate.parse(datos[2]);
            String content = datos[3];
            int priority = Integer.parseInt(datos[4]);
            int estimatedDuration = Integer.parseInt(datos[5]);
            boolean completed = Boolean.parseBoolean(datos[7]);
            Task t = new Task(identifier, title, date, content, priority, estimatedDuration, completed);
            return t;
        } catch (NumberFormatException|ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public String getInstanceAsDelimitedString(String delim){
        identifier = getIdentifier();
        String identifierString = identifier.toString();
        return String.format(Locale.ENGLISH, "%s" + delim + "%s" + delim + "%s" + delim + "%s" + delim + "%s" + delim + "%s" + delim + "%s", identifierString, title, date, content, priority, estimatedDuration, tareaCompletada());
    }

    public String listarTarea(){
        return String.format("|%10d|%10s|%10s|%10s|%10d|%10d|%10s|", this.identifier, this.title, this.date, this.content, this.priority, this.estimatedDuration, tareaCompletada() );

    }






    public String tareaCompletada(){
        if(this.isCompleted()==true){
            return "SI";
        }else{return "NO";}
    }

    public void changeCompleted(){
        if(this.completed==true){
            setCompleted(false);
        }else{setCompleted(true);}
    }

    //getters y setters
    public UUID getIdentifier() {
        return identifier;
    }


    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public int getPriority() {
        return priority;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }


    public int getEstimatedDuration() {
        return estimatedDuration;
    }


    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }


    public boolean isCompleted() {
        return completed;
    }


    public void setCompleted(boolean completed) {
        this.completed = completed;
    }




    



    
    

}
