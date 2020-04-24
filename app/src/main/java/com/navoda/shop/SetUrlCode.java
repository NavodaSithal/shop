package com.navoda.shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.navoda.shop.model.cart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SetUrlCode extends AppCompatActivity {
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_url_code);
        text = findViewById(R.id.txt_url);

    }

    public void writeUrl(View view) {
        writeToFile();

    }

    private void writeToFile() {

        if (!text.getText().toString().isEmpty()){

            File file = new File(SetUrlCode.this.getFilesDir(), "text");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                String s =  text.getText().toString();
                File gpxfile = new File(file, "sample");
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(s);
                writer.flush();
                writer.close();
//                openFileOutput().setText(readFile());

                readFile();
                Toast.makeText(SetUrlCode.this, "Saved your text", Toast.LENGTH_LONG).show();
            } catch (Exception e) { }
        }


    }

    private String readFile() {
        File fileEvents = new File(SetUrlCode.this.getFilesDir()+"/text/sample");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
//                text.append('\n');
            }

            br.close();
        } catch (IOException e) { }
        String result = text.toString();
        cart.subUrl = result;

        return result;
    }
}
