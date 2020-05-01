package com.navoda.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OcrTextActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_text);
        text = findViewById(R.id.ocr_text);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("TEXT");
        text.setText(message);

    }
}
