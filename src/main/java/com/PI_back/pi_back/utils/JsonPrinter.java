package com.PI_back.pi_back.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class JsonPrinter {
    public static String toString(Object t) {
        GsonBuilder gsonBuilder = new GsonBuilder(); // Crear un objeto GsonBuilder para configurar la serialización JSON
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()); // Registrar un adaptador de tipo para LocalDate utilizando LocalDateAdapter
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()); // Registrar un adaptador de tipo para LocalDateTime utilizando LocalDateTimeAdapter
        Gson gson = gsonBuilder.setPrettyPrinting().create(); // Crear un objeto Gson con la configuración proporcionada, incluyendo formato legible y pretty-printing

        return gson.toJson(t).trim().replace("\n", "").replace("\t", ""); // Convertir el objeto en formato JSON utilizando el objeto Gson creado y eliminar espacios en blanco, saltos de línea y tabulaciones innecesarias
    }
    }
