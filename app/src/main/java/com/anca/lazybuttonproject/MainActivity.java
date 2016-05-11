package com.anca.lazybuttonproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anca.lazybutton.LazyButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LazyButton lazyButton = (LazyButton) findViewById(R.id.my_btn);
    }
}
