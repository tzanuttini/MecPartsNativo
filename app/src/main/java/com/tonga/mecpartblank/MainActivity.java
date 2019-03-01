package com.tonga.mecpartblank;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    private ListView lista;
    private ArrayList<Maquina> maquinas;
    private Servicio servicio;
    private ProgressDialog dialogo;
    private Adapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.lvMaquinas);
        maquinas = new ArrayList<Maquina>();
        dialogo = new ProgressDialog(this);
        obtenerMaquinas();
        dialogo.hide();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this ,"Cliquiaste la maquina con id "+view.getTag().toString(),Toast.LENGTH_SHORT);
            }
        });
    }
    private void obtenerMaquinas() {
        String ruta = "http://192.168.1.3:3000/api/maquinas";
        try{
            servicio = new Servicio();
            servicio.execute(ruta);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public class Servicio extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            dialogo.setTitle("Obteniendo datos del maquinas");
            dialogo.setMessage("Espere unos segundos..");
            dialogo.show();
        }

        @Override
        protected void onPostExecute(String s) {

            lista.setAdapter(adaptador);
        }

        @Override
        protected String doInBackground(String... strings) {
            String cadena = strings[0];
            URL url = null;
            String devuelve = "";
            maquinas.clear();
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
                    for(int i=0;i<respuestaJson.length();i++){
                        maquinas.add(new Maquina(respuestaJson.getJSONObject(i).getString("id"),respuestaJson.getJSONObject(i).getString("nombre")));
                    }

                    adaptador = new Adapter(getApplicationContext(),maquinas);
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
    }
}
