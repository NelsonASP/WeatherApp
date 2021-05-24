package com.nelson.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nelson.weatherapp.Utils.ConsultasML;
import com.nelson.weatherapp.Utils.DataSearch;
import com.nelson.weatherapp.Utils.DataSearch2;
import com.nelson.weatherapp.Utils.ICallbackListeners;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ICallbackListeners {

    final String TAG = "ConsultasWather";
    ConsultasML consultasML;
    EditText search;
    DataSearch dataSearch;
    DataSearch2 dataSearch2;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        consultasML = new ConsultasML(this, this);
        init();
    }

    public void init(){
        search = findViewById(R.id.editbuscar);
        search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // si presiona enter
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String itemSearch = search.getText().toString();
                    Log.e("Button","" +  itemSearch);
                    consultaED(itemSearch);

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void listenerListItemsxName(DataSearch dataSearch) {
        this.dataSearch = dataSearch;

        Log.e(TAG,"Consulta Items Name" +  dataSearch);

        if (dataSearch == null){
            showDialogWarning("ERROR","CIUDAD NO ENCONTRADA",1);

        }else {
            showDialogWarning("BUSCANDO","BUSCANDO CIUDAD...",2);
            consultaCT(dataSearch.getIdciudad());
        }
    }

    @Override
    public void listenerListItemsxIds(DataSearch2 dataSearch2) {
        Log.e(TAG,"Consulta ciudad" +  dataSearch2);
        this.dataSearch2 = dataSearch2;

        Intent intent = new Intent(MainActivity.this, ActivityWeather.class);
        intent.putExtra("DataSearch2", dataSearch2);
        intent.putExtra("DataSearch", dataSearch);
        startActivity(intent);
    }

    public void consultaED(String busqueda){
        consultasML.consultaItemsXNombre(busqueda);
    }

    public void consultaCT(String ciudad){
        consultasML.consultaItemsxIds(ciudad);
    }

    private void showDialogWarning(String title, String mesage, Integer integer) {
        try {
            alertDialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(mesage)
                    .setIcon(R.drawable.warning)
                    .setCancelable(false)
                    .show();

            if (alertDialog != null) {
                TextView sms = Objects.requireNonNull(alertDialog).findViewById(android.R.id.message);
                sms.setGravity(Gravity.CENTER);
                sms.setTextSize(18);
                //sms.setAllCaps(true);

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerTask,2000);

                switch (integer) {
                    case 1:
                        alertDialog.setIcon(R.drawable.warning);
                        break;
                    case 2:
                        alertDialog.setIcon(R.drawable.aproved);
                        break;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            salir();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void salir(){

        alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.lbTitle))
                .setIcon(R.drawable.warning)
                .setMessage(getString(R.string.lbExit))
                .setCancelable(false)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Finalizar la actividad
                        MainActivity.this.finishAffinity();
                    }
                })
                .show();

        if (alertDialog != null) {
            TextView sms = Objects.requireNonNull(alertDialog).findViewById(android.R.id.message);
            sms.setGravity(Gravity.CENTER);
            sms.setTextSize(18);
            //sms.setAllCaps(true);
        }
    }
}