package com.example.sensor_proximidad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //Objeto SensorManager
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Para obtener acceso a cualquier sensor de hardware,
         necesitas un objeto SensorManager. Para crearlo,
         utiliza el método getSystemService() de tu clase
         Activity, y pásale la constante SENSOR_SERVICE.

            SENSOR_SERVICE=Úselo con getSystemService (java.lang.String)
            para recuperar un SensorManager para acceder a los sensores.
         */
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        /*Ahora, puedes crear un objeto Sensor para el
        sensor de proximidad, invocando el método
        getDefaultSensor() y pasándole la constante TYPE_PROXIMITY.



        TYPE_PROXIMITY=Una constante que describe un tipo de sensor
        de proximidad. Este es un sensor de activación.


        */
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        /*
        Antes de proceder, asegúrate siempre de que
        el objeto Sensor no sea null. Si lo es, significa
        que el sensor de proximidad no está disponible.

        Si el sensor cuenta con el sensor
         */

        if(sensor == null) {
            finish();
        }

        /*
        Para poder leer los datos crudos generados por un sensor, debes asociarle un
        SensorEventListener invocando el método registerListener() del objeto SensorManager.
         Al hacerlo, también debes especificar la frecuencia con la cual deberían leerse datos del sensor.
         */
        sensorEventListener = new SensorEventListener() {
            @Override
            //Este metodo funciona cuando los valores han cambiado del sensor
            public void onSensorChanged(SensorEvent sensorEvent) {

                if(sensorEvent.values[0] < sensor.getMaximumRange()) {
                    //si estamos dentro del rango del sensor se cumplirar la condicion
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        start();
    }
    public void start(){
        //Tiempo de ejecucion entre dos eventos
        sensorManager.registerListener(sensorEventListener,sensor,2000*1000);
    }
    public void stop(){
        //el sensor se detiene
        sensorManager.unregisterListener(sensorEventListener);
    }
    @Override
    //no se encuentra en primer plano
    protected void onPause(){
        stop();
        super.onPause();
    }
    @Override
    //Se vuelve a poner la aplicacion en primer plano
    protected void onResume(){
        start();
        super.onResume();
    }
}
