package com.londonappbrewery.magiceightball;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;

    private float acelVal;  // CURRENT ACCELERATION VALUE AND GRAVITY
    private float acelLast; // LAST ACCELERATION VALUE AND GRAVITY
    private float shake;    // ACCELERATION VALUE DIFFER FROM GRAVITY

    private ImageView ballImage;

    private final int[] images = {
            R.drawable.ball1,
            R.drawable.ball2,
            R.drawable.ball3,
            R.drawable.ball4,
            R.drawable.ball5
    };

    private Random randomNumGen = new Random();
    private Random rnd = new Random();
    private LinearLayout a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ballImage = (ImageView)findViewById(R.id.imageView);
        a = (LinearLayout)findViewById(R.id.activity_main);

        ballImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shakeIt();
            }
        });

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            if (shake > 12) {
                // CODE GO HERE
                shakeIt();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void shakeIt() {
        int number = randomNumGen.nextInt(images.length);
        ballImage.setImageResource(images[number]);
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        a.setBackgroundColor(color);
    }
}


