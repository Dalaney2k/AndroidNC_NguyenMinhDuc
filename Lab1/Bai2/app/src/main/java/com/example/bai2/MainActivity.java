package com.example.bai2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView textView1, textView2, textView3;
    private Button button1, button2, button3;
    private Thread thread1, thread2, thread3;
    private boolean isThread1Running = false, isThread2Running = false, isThread3Running = false;
    private Handler handler;
    private static final int THREAD_1 = 1;
    private static final int THREAD_2 = 2;
    private static final int THREAD_3 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case THREAD_1:
                        textView1.setText(String.valueOf(msg.arg1));
                        break;
                    case THREAD_2:
                        textView2.setText(String.valueOf(msg.arg1));
                        break;
                    case THREAD_3:
                        textView3.setText(String.valueOf(msg.arg1));
                        break;
                }
            }
        };

        startThreads();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isThread1Running = !isThread1Running;
                updateButtonText(button1, isThread1Running);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isThread2Running = !isThread2Running;
                updateButtonText(button2, isThread2Running);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isThread3Running = !isThread3Running;
                updateButtonText(button3, isThread3Running);
            }
        });
    }
    private void startThreads() {
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                try {
                    while (true) {
                        if (isThread1Running) {
                            int randomNumber = random.nextInt(51) + 50; //sinh random từ 0-50
                            Message msg = handler.obtainMessage(THREAD_1, randomNumber, 0);
                            handler.sendMessage(msg);
                        }
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int oddNumber = 1;
                try {
                    while (true) {
                        if (isThread2Running) {
                            Message msg = handler.obtainMessage(THREAD_2, oddNumber, 0);
                            handler.sendMessage(msg);
                            oddNumber += 2;
                        }
                        Thread.sleep(2500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int number = 0;
                try {
                    while (true) {
                        if (isThread3Running) {
                            Message msg = handler.obtainMessage(THREAD_3, number, 0);
                            handler.sendMessage(msg);
                            number++;
                        }
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
    }
    private void updateButtonText(Button button, boolean isRunning) {
        if (isRunning) {
            button.setText("Stop");
        } else {
            button.setText("Start");
        }
    }
}
