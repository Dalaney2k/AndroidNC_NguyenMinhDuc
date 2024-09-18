package com.example.btvn1_baothuc;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button setAlarmButton, stopButton;
    private Vibrator vibrator;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        stopButton = findViewById(R.id.stopButton);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        timePicker.setIs24HourView(true);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopVibration();
            }
        });
    }

    private void setAlarm() {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long currentTimeMillis = System.currentTimeMillis();
        long alarmTimeMillis = calendar.getTimeInMillis();

        if (alarmTimeMillis <= currentTimeMillis) {
            alarmTimeMillis += 24 * 60 * 60 * 1000;
        }

        long timeDifference = alarmTimeMillis - currentTimeMillis;
        startCountDown(timeDifference);
    }

    private void startCountDown(long timeInMillis) {
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                startVibration();
            }
        };
        countDownTimer.start();
        Toast.makeText(this, "Báo thức đã được cài!", Toast.LENGTH_SHORT).show();
    }

    private void startVibration() {
        Toast.makeText(this, "Đã đến giờ", Toast.LENGTH_LONG).show();
        long[] pattern = {0, 1000, 1000};
        vibrator.vibrate(pattern, 0);
        stopButton.setVisibility(View.VISIBLE);
    }

    private void stopVibration() {
        if (vibrator != null) {
            vibrator.cancel();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        stopButton.setVisibility(View.GONE);
        Toast.makeText(this, "Đã dừng báo thức!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vibrator != null) {
            vibrator.cancel();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}