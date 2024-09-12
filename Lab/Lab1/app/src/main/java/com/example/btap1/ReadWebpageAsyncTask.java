package com.example.btap1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReadWebpageAsyncTask extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.TextView01);
        button = (Button) findViewById(R.id.readwebpage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadWebPageTask task = new DownloadWebPageTask(textView);
                task.execute("https://vi.wikipedia.org/wiki/Wikipedia_ti%E1%BA%BFng_Vi%E1%BB%87t");
            }
        });
    }
}
