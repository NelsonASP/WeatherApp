package com.nelson.weatherapp.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ConsultasML {

    final String TAG = "ConsultasML";

    Context myContext;

    DataSearch dataSearch;
    DataSearch2 dataSearch2;

    final String  urlServidor = "https://www.metaweather.com/" ;

    /**
     * Urls de las consultas
     */
    final String urlConsultaItemsNombre = "api/location/search/";
    final String urlConsultaItemsIds    = "api/location/";

    /**
     * Listado de las consultas que tenemos
     */
    final int CONSULTA_ITEMS_X_NOMBRE = 1;
    final int CONSULTA_ITEMS_X_IDS    = 2;

    /**
     * Identifica que consulta estamos haciendo
     */
    int tipoConsulta = CONSULTA_ITEMS_X_NOMBRE;


    public ICallbackListeners callback;

    public ConsultasML(Context myContext) {
        this.myContext = myContext;
    }

    public ConsultasML(Context myContext, ICallbackListeners listener) {
        this.myContext = myContext;
        this.callback = listener;
    }

    /**
     * Método que establece el listener para las respuestas.
     *
     * @param listener
     */
    public void setListener(ICallbackListeners listener){
        callback = listener;
    }

    /**
     * Método que hace la búsqueda de ciudades x parte del nombre.
     *
     * Ejemplo : https://www.metaweather.com/api/location/search/?query=london
     *
     * @param texto a buscar
     */
    public void consultaItemsXNombre(String texto){

        tipoConsulta = CONSULTA_ITEMS_X_NOMBRE;

        String[] data = {"?query",texto};

        new ConsultasAsincronas(urlConsultaItemsNombre, data).execute();
    }


    /**
     * Método que hace la búsqueda de items x Id
     * Se pueden consultar varios ítems separando sus ids con comas.
     *
     * Ejemplo : https://www.metaweather.com/api/location/368148/
     *
     * @param ids de los artículos
     */
    public void consultaItemsxIds(String... ids ){

        tipoConsulta = CONSULTA_ITEMS_X_IDS;

        new ConsultasML.ConsultasAsincronas(urlConsultaItemsIds, ids).execute();

    }

    /**
     * Clase asíncrona que realiza las consultas al servidor
     */
    private class ConsultasAsincronas extends AsyncTask<String, Void, String> {

        private String url;
        private JSONArray objectResponse;
        private JSONObject  objectResponse2;

        private String complemento;

        public ConsultasAsincronas(String modulo, String... datos) {

            Log.d(TAG, "Consultas()");

            complemento = String.join("=", datos);

            url = urlServidor.concat(modulo).concat(complemento);
        }


        protected String doInBackground(String... strings) {

            InputStream inputStream = null;

            Log.d(TAG, "Url "+ url);

            try {
                URL url2 = new URL(url); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url2.openConnection();
                //httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("GET"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                //httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }

                if(inputStream != null)
                    return convertInputStreamToString(inputStream);
                else
                    return "";

            } catch (MalformedURLException e) {
                //e.printStackTrace();
                Log.d(TAG, "Excepcion 1 = "+e.getMessage());
                return "";
            } catch (IOException e) {
                //e.printStackTrace();
                Log.d(TAG, "Excepcion 2 = "+e.getMessage());
                return "";
            }

        }

        @Override
        protected void onPostExecute(String result) {

            Log.d(TAG, "Recibido del servidor = "+result);

            String result2 = "";

            result = result.trim();

            // Validar el tamaño
            if (result.length()>5){
                result2 = result;
            }

            // Extraer datos de la respuesta
            try {

                if(tipoConsulta==CONSULTA_ITEMS_X_NOMBRE){
                    objectResponse = new JSONArray(result2);
                }else if (tipoConsulta==CONSULTA_ITEMS_X_IDS){
                    objectResponse2 = new JSONObject(result2);
                }

            } catch (JSONException e) {
                Log.d(TAG, "JSONException 1 " +e.getMessage());
            }

            if (callback!=null){
                if(tipoConsulta==CONSULTA_ITEMS_X_NOMBRE){
                    dataSearch = new DataSearch();
                    dataSearch2 = new DataSearch2();
                    try {
                        JSONObject arreglo = (JSONObject) objectResponse.get(0);

                        String idciudad = arreglo.getString("woeid");
                        String nombreciudad = arreglo.getString("title");
                        String locacion = arreglo.getString("location_type");

                        dataSearch.setIdciudad(idciudad);
                        dataSearch.setNombreciudad(nombreciudad);
                        dataSearch.setLocacion(locacion);

                        Log.e(TAG,"" + idciudad + nombreciudad + locacion );

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        dataSearch = null;
                    }

                    callback.listenerListItemsxName(dataSearch);
                }else if (tipoConsulta==CONSULTA_ITEMS_X_IDS){

                    try {
                        JSONArray datatos = objectResponse2.getJSONArray("consolidated_weather");
                        JSONObject arreglo  = (JSONObject) datatos.get(0);

                        String climaactual = arreglo.getString("weather_state_name");
                        String temperatura = arreglo.getString("the_temp");
                        String fechaactual = arreglo.getString("applicable_date");
                        String temperaturaminima = arreglo.getString("min_temp");
                        String temperaturamaxima = arreglo.getString("max_temp");
                        String humedad = arreglo.getString("humidity");
                        String visibilidad = arreglo.getString("visibility");
                        String previsibilidad = arreglo.getString("predictability");

                        dataSearch2.setClimaactual(climaactual);
                        dataSearch2.setFechaactual(fechaactual);
                        dataSearch2.setTemperatura(temperatura);
                        dataSearch2.setTemperaturaminima(temperaturaminima);
                        dataSearch2.setTemperaturamaxima(temperaturamaxima);
                        dataSearch2.setHumedad(humedad);
                        dataSearch2.setVisibilidad(visibilidad);
                        dataSearch2.setPrevisibilidad(previsibilidad);

                        Log.e(TAG,"" + temperatura );

                    }catch (Exception e){
                    }

                    callback.listenerListItemsxIds(dataSearch2);
                }
            }

        }

    }

    /**
     * Convertir el input de la petición en un String
     *
     * @param inputStream
     * @return String
     * @throws IOException
     */
    public String convertInputStreamToString(InputStream inputStream) throws IOException {

        Log.d(TAG,"convertInputStreamToString()");

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}

