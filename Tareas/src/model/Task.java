package model;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;


//Clase Tarea con atributos, constructores, getters y setters...

public class Task implements Serializable {
    private UUID identifier;
    private String title;
    private Date date;
    private String content;
    private int priority;
    private int estimatedDuration;
    private boolean completed;

    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    //constructor con atributos
    public Task(UUID identifier, String title, Date date, String content, int priority, int estimatedDuration,
            boolean completed) {
        this.identifier = UUID.randomUUID();
        this.title = title;
        this.date = date;
        this.content = content;
        this.priority = priority;
        this.estimatedDuration = estimatedDuration;
        this.completed = false; //para que por defecto la tarea no este completada
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


    public static Task getTaskFromString(String tareaString, String delimitador) throws ParseException{

            try { 
                String[] datos = tareaString.split(delimitador);
            if(datos.length ==7){
            UUID identifier = UUID.fromString(datos[0]) ;
            String title = datos[1];
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(datos[2]);
            String content = datos[3];
            int priority = Integer.parseInt(datos[4]);
            int estimatedDuration = Integer.parseInt(datos[5]);
            boolean completed = Boolean.parseBoolean(datos[6]);
            Task t = new Task(identifier, title, date, content, priority, estimatedDuration, completed);
            return t;
        }else{
            return null;
        }

        } catch (NumberFormatException|ArrayIndexOutOfBoundsException e) {
            return null;
        }
        }
        
        
    

    public String getInstanceAsDelimitedString(String delimitador){

        String fields = "%s"+delimitador+"%s"+delimitador+"%s"+delimitador+"%s"+delimitador+"%d"+delimitador+"%d"+delimitador+"%s"+delimitador;
                                                        
        return String.format(fields,getIdentifierAsString(),title,getDateAsString(),content,priority,estimatedDuration,tareaCompletada());

    }

    public String getIdentifierAsString(){
        return this.identifier.toString();
    }
    public String getDateAsString(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(getDate());
        return dateString;
    }

    public String listarTarea(){

        return String.format("|%10s|%10s|%10s|%10s|%10d|%10d|%10s|", this.identifier, this.title, this.date, this.content, this.priority, this.estimatedDuration, tareaCompletada() );

    }

    public String tareaCompletada(){
        if(this.isCompleted()==true){
            return "SI";
        }else{return "NO";}
    }

    public void completarTarea(){
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


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
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
