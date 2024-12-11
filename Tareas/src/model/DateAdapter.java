package model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


//Método adaptador para que el Gson pueda manejar el tipo de dato Date

public class DateAdapter extends TypeAdapter<Date>  {
private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value != null) {
            out.value(formatter.format(value)); // Serializa el Date a String en el formato definido
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        try {
            return formatter.parse(in.nextString()); // Deserializa el String a Date usando el formato
        } catch (ParseException e) {
            throw new IOException("Error al convertir el String a Date: " + e.getMessage(), e);
        }
    }

}
