package com.nelson.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nelson.weatherapp.Utils.DataSearch;
import com.nelson.weatherapp.Utils.DataSearch2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ActivityWeather extends AppCompatActivity {

    final String TAG = "ConsultasWeAp";
    TextView temperatura;
    TextView nombreciudad;
    TextView fechaactual;
    TextView temeraturaMinima;
    TextView TemperaturaMaxima;
    TextView climaActual;
    TextView humedad;
    TextView viibilidad;
    TextView previsibilidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        init();
    }

    public void init(){

        DataSearch2 dataSearch2 = (DataSearch2) getIntent().getSerializableExtra("DataSearch2");
        DataSearch dataSearch = (DataSearch) getIntent().getSerializableExtra("DataSearch");

        temperatura = findViewById(R.id.tvTemperatura);
        nombreciudad = findViewById(R.id.tvnamecity);
        fechaactual = findViewById(R.id.tvdate);
        temeraturaMinima = findViewById(R.id.tvtempmin);
        TemperaturaMaxima = findViewById(R.id.tvtempmax);
        climaActual = findViewById(R.id.tvweather);
        humedad = findViewById(R.id.tvhumidity);
        viibilidad = findViewById(R.id.tvvisibility);
        previsibilidad = findViewById(R.id.tvpredictability);

        temperatura.setText(dataSearch2.getTemperatura());
        nombreciudad.setText(dataSearch.getNombreciudad());
        fechaactual.setText(dataSearch2.getFechaactual());
        temeraturaMinima.setText(dataSearch2.getTemperaturaminima());
        TemperaturaMaxima.setText(dataSearch2.getTemperaturamaxima());
        climaActual.setText(dataSearch2.getClimaactual());
        humedad.setText(dataSearch2.getHumedad());
        viibilidad.setText(dataSearch2.getVisibilidad());
        previsibilidad.setText(dataSearch2.getPrevisibilidad());

        Log.e(TAG,"Info" + dataSearch +dataSearch2  );

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(ActivityWeather.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}