package com.tonga.mecpartblank.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.tonga.mecpartblank.Classes.Usuario;
import com.tonga.mecpartblank.Maquina;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ObtenerDatos extends AsyncTask<String, Void, String> {
    private ProgressDialog dialogo;
    private String ruta;
    private ArrayList<Maquina> maquinola;
    private ArrayList<Usuario> users;
    public ObtenerDatos(Context v) {
        dialogo = new ProgressDialog(v);
        ruta = "http://192.168.0.70:3000/api/";
    }

    @Override
    protected void onPreExecute() {
        dialogo.setTitle("Obteniendo datos del maquinas");
        dialogo.setMessage("Espere unos segundos..");
        dialogo.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... strings) {
        String cadena = strings[0]+strings[1];
        URL url = null;
        String devuelve = "";
        try {
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" + " (Linux; Android 1.5; es-ES) LectorScanner HTTP");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String linea;
                while((linea = reader.readLine())!= null){
                    result.append(linea);
                }
                JSONArray respuestaJson = new JSONArray(result.toString());
                switch (strings[1]) {
                    case "maquinas":
                    for (int i = 0; i < respuestaJson.length(); i++) {
                        maquinola.add(new Maquina(respuestaJson.getJSONObject(i).getString("id"), respuestaJson.getJSONObject(i).getString("nombre")));
                    }
                    break;
                    case "usuarios/":
                        for (int i = 0; i<respuestaJson.length();i++){
                            users.add(new Usuario(respuestaJson.getJSONObject(i).getString("usuario"),respuestaJson.getJSONObject(i).getString("clave"),respuestaJson.getJSONObject(i).getString("rol")));
                        }
                        break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return devuelve;
    }
    public ArrayList<Maquina> obtenerMaquinas(){
         maquinola = new ArrayList<Maquina>();
        this.execute(ruta,"maquinas");
        return maquinola;
    }
    public ArrayList<Usuario> obtenerUsuario(){
        users = new ArrayList<Usuario>();
        this.execute(ruta,"usuarios/");
        return users;

    }
}
