package com.ipl.professorallocation.data.repositorio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.ipl.professorallocation.data.service.AlocacaoService;
import com.ipl.professorallocation.data.service.CursoService;
import com.ipl.professorallocation.data.service.DepartamentoService;
import com.ipl.professorallocation.data.service.ProfessorService;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String URL_BASE = "https://professor-allocation.herokuapp.com";
    // Loga as mensagens enviadas para o backend através do retrofit
    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .client(client)
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create(gsonConverters()))
                    .build();
        }
        return retrofit;
    }

    public static ProfessorService getProfessorService() {
        if (retrofit == null) {
            getInstance();
        }
        return retrofit.create(ProfessorService.class);
    }

    public static CursoService getCursoService() {
        if (retrofit == null) {
            getInstance();
        }
        return retrofit.create(CursoService.class);
    }

    public static DepartamentoService getDepartamentoService() {
        if (retrofit == null) {
            getInstance();
        }
        return retrofit.create(DepartamentoService.class);
    }

    public static AlocacaoService getAlocacaoService() {
        if (retrofit == null) {
            getInstance();
        }
        return retrofit.create(AlocacaoService.class);
    }

    /*
       Este código converte tipos especificos do Java para que possam ser enviados para o backend
       e posteriormente convertem o que recebem do backend para tipos especificos no Java.
    */
    public static Gson gsonConverters() {
        final GsonBuilder builder = new GsonBuilder();

        // Converter a string de hora, minuto e timezone (14:31+0000) enviada pelo backend para o
        // tipo LocalTime do java. (14:31)
        builder.registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (je, type, jdc) ->
                LocalTime.parse(je.getAsString(), DateTimeFormatter.ofPattern("HH:mmZ"))
        );

        // Converter LocalTime de hora, minuto (14:31) utilizado na plaicação para o
        // tipo  do backend adicionando time zone (14:31+0000)
        builder.registerTypeAdapter(LocalTime.class, (JsonSerializer) (src, typeOfSrc, context) ->
                new JsonPrimitive(src.toString()+"+0000")
        );

        return builder.create();
    }
}
