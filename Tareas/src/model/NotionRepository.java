package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

import notion.api.v1.NotionClient;
import notion.api.v1.http.OkHttp5Client;
import notion.api.v1.logging.Slf4jLogger;
import notion.api.v1.model.databases.QueryResults;
import notion.api.v1.model.pages.Page;
import notion.api.v1.model.pages.PageParent;
import notion.api.v1.model.pages.PageProperty;
import notion.api.v1.model.pages.PageProperty.RichText;
import notion.api.v1.model.pages.PageProperty.RichText.Text;
import notion.api.v1.request.databases.QueryDatabaseRequest;
import notion.api.v1.request.pages.CreatePageRequest;
import notion.api.v1.request.pages.UpdatePageRequest;

public class NotionRepository implements IRepository{
    private final NotionClient client;
    private final String databaseId;
    private final String titleColumnName = "Identifier";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;


    public NotionRepository(String apiToken, String databaseId){
        this.client=new NotionClient(apiToken);
        client.setHttpClient(new OkHttp5Client(60000,60000,60000));
        client.setLogger(new Slf4jLogger());
        System.setProperty("notion.api.v1.logging.StdoutLogger", "debug");
        this.databaseId = databaseId;

    }


    // Buscar el ID (interno de Notion) de una página por Identifier (atributo Title de la Database de Notion)
    private String findPageIdByIdentifier(String identifier, String titleColumnName) {
        try {
            QueryDatabaseRequest queryRequest = new QueryDatabaseRequest(databaseId);
            QueryResults queryResults = client.queryDatabase(queryRequest);

            for (Page page : queryResults.getResults()) {
                Map<String, PageProperty> properties = page.getProperties();
                if (properties.containsKey(titleColumnName) &&
                        properties.get(titleColumnName).getTitle().get(0).getText().getContent().equals(identifier)) {
                    return page.getId();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Métodos auxiliares para crear propiedades de página
    private PageProperty createTitleProperty(String title) {
        RichText idText = new RichText();
        idText.setText(new Text(title));
        PageProperty idProperty = new PageProperty();
        idProperty.setTitle(Collections.singletonList(idText));
        return idProperty;
    }

    // Metodos auxiliares para crear propiedades de página
    private PageProperty createRichTextProperty(String text) {
        RichText richText = new RichText();
        richText.setText(new Text(text));
        PageProperty property = new PageProperty();
        property.setRichText(Collections.singletonList(richText));
        return property;
    }

    private PageProperty createNumberProperty(Integer number) {
        PageProperty property = new PageProperty();
        property.setNumber(number);
        return property;
    }

    private PageProperty createDateProperty(String date) {
        PageProperty property = new PageProperty();
        PageProperty.Date dateProperty = new PageProperty.Date();
        dateProperty.setStart(date);
        property.setDate(dateProperty);
        return property;
    }

    private PageProperty createCheckboxProperty(boolean checked) {
        PageProperty property = new PageProperty();
        property.setCheckbox(checked);
        return property;
    }

    private Task mapPageToTask(String pageId, Map<String, PageProperty> properties) {
        try {
            Task tarea = new Task(); 
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            //DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;


            tarea.setIdentifier(UUID.fromString(properties.get("Identifier").getTitle().get(0).getText().getContent()));
            tarea.setTitle(properties.get("Titulo").getRichText().get(0).getText().getContent());
            tarea.setDate(formatter.parse(properties.get("Fecha").getDate().getStart()));
            tarea.setContent(properties.get("Contenido").getRichText().get(0).getText().getContent());
            tarea.setPriority(properties.get("Prioridad").getNumber().intValue());
            tarea.setEstimatedDuration(properties.get("Duracion Estimada").getNumber().intValue());
            tarea.setCompleted(properties.get("Completada").getCheckbox());
            return tarea;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addTask(Task tarea) throws RepositoryException {
        System.out.println("Creando una nueva página...");
        // Crear las propiedades de la página
        // Las propiedades son las que se definen en la Dabase de Notion como columnas
        // Se ejemplifican varios tipos de propiedades como texto, número, fecha y casilla de verificación

        String p = findPageIdByIdentifier(tarea.getIdentifierAsString(), titleColumnName);
        System.out.println(tarea.listarTarea());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, PageProperty> properties = Map.of(
            
                "Identifier", createTitleProperty((tarea.getIdentifier()).toString()),
                "Titulo", createRichTextProperty(tarea.getTitle()),
                "Fecha", createDateProperty(formatter.format(tarea.getDate())),
                "Contenido", createRichTextProperty(tarea.getContent()),
                "Prioridad", createNumberProperty(tarea.getPriority()),
                "Duracion Estimada", createNumberProperty(tarea.getEstimatedDuration()),
                "Completada", createCheckboxProperty(tarea.isCompleted())
        );

         PageParent parent = PageParent.database(databaseId);

        // Crear la solicitud a la API de Notion
        CreatePageRequest request = new CreatePageRequest(parent, properties);

        // Ejecutar la solicitud (necesita de conexión a internet)
        Page response = client.createPage(request);

        // Este identificador es el interno de Notion no el campo Identifier de tipo Title
        // que se utilizará como clave primaria unica
        // Sin embargo es necesario para actualizar o eliminar registros
        System.out.println("Página creada con ID (interno Notion)" + response.getId());
        return true;
        } 
    
    

    @Override
    public boolean removeTask(UUID identifier) throws RepositoryException {
        try {
            String pageId = findPageIdByIdentifier(identifier.toString(),titleColumnName);
            if (pageId == null) {
                System.out.println("No se encontró un registro con el Identifier: " + identifier);
                return false;
            }
            // Archivar la página
            UpdatePageRequest updateRequest = new UpdatePageRequest(pageId, Collections.emptyMap(), true);
            client.updatePage(updateRequest);
            System.out.println("Página archivada con ID (interno Notion)" + pageId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Task modifyTask(UUID identifier) throws RepositoryException {
        Task tareaCambiar = new Task();
        try {
            String pageId = findPageIdByIdentifier(identifier.toString(), titleColumnName);
            if (pageId == null) {
                System.out.println("No se encontró un registro con el Identifier: " + identifier);
    
            }
            QueryDatabaseRequest queryRequest = new QueryDatabaseRequest(databaseId);

            // Ejecutar la consulta
            QueryResults queryResults = client.queryDatabase(queryRequest);

            // Procesar los resultados
            for (Page page : queryResults.getResults()) {
                Map<String, PageProperty> properties = page.getProperties();
                Task tarea = mapPageToTask(page.getId(), properties);
                if (tarea != null && tarea.getIdentifier().equals(identifier)) {
                    tareaCambiar = tarea;
                    removeTask(tarea.getIdentifier());
                }
            }
            

            System.out.println("Página actualizada con ID (interno Notion)" + pageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
                return tareaCambiar;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tareas = new ArrayList<>();
        try {
            // Crear la solicitud para consultar la base de datos
            QueryDatabaseRequest queryRequest = new QueryDatabaseRequest(databaseId);

            // Ejecutar la consulta
            QueryResults queryResults = client.queryDatabase(queryRequest);

            // Procesar los resultados
            for (Page page : queryResults.getResults()) {
                Map<String, PageProperty> properties = page.getProperties();
                Task tarea = mapPageToTask(page.getId(), properties);
                if (tarea != null) {
                    tareas.add(tarea);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tareas;

    }

    @Override
    public boolean completarTarea(UUID identifier)  {
        List<Task> tareas = getAllTasks();
                              
        // Crear la solicitud para consultar la base de datos
        QueryDatabaseRequest queryRequest = new QueryDatabaseRequest(databaseId);

        // Ejecutar la consulta
        QueryResults queryResults = client.queryDatabase(queryRequest);

        // Procesar los resultados
        for (Page page : queryResults.getResults()) {
            Map<String, PageProperty> properties = page.getProperties();
            Task tarea = mapPageToTask(page.getId(), properties);
            if (tarea != null &&tarea.getIdentifier().equals(identifier)) {
                completarTarea(tarea.getIdentifier());
                return true;
            }else{return false;}
        }
        return true;
    }

}
