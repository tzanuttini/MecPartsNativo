package com.tonga.mecpartblank;

public class Maquina {
    private String id;
    private String nombre;

    public Maquina(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }
}
