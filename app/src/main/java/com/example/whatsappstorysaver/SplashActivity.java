package com.example.whatsappstorysaver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    /*  @BindView(R.id.txtWelcome)
      TextView textView;
  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //  ButterKnife.bind(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 1200);

        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory() + "/NewVijay");
                if (!file.exists()) {
                    file.mkdir();
                    Toast.makeText(getApplicationContext(), Environment.getExternalStorageDirectory()+"/Success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), Environment.getExternalStorageDirectory()+"Already exist", Toast.LENGTH_LONG).show();
                }

            }
        });*/
    }
}