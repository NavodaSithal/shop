package com.navoda.shop;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.nfc.Tag;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.net.URL;

public class TextRec extends AppCompatActivity{

    ImageView img;
    ContentValues values;
    Uri imageUri;
    int PICTURE_RESULT = 100;
    Bitmap thumbnail;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_rec);
        img = findViewById(R.id.cam_view);

        cam();

        Button b = findViewById(R.id.btn_check);
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if (!textRecognizer.isOperational()){
//                    Toast.makeText(this,"no text",Toast.LENGTH_SHORT).show();
                }else {
                    Frame frame = new Frame.Builder().setBitmap(thumbnail).build();
                    SparseArray<TextBlock> item = textRecognizer.detect(frame);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0 ; i < item.size() ; i++){
                        TextBlock textBlock = item.valueAt(i);
                        stringBuilder.append(textBlock.getValue());
                        stringBuilder.append("\n");

                    }
                }
            }
        }) ;
    }


    public void cam(){
        if (ContextCompat.checkSelfPermission(TextRec.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TextRec.this, new String[] {Manifest.permission.CAMERA},100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 100){
//            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
//            img.setImageBitmap(imageBitmap);
//        }

//        case PICTURE_RESULT:
        if (requestCode == PICTURE_RESULT)
            if (resultCode == Activity.RESULT_OK) {
                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), imageUri);
                    img.setImageBitmap(thumbnail);
                    Object imageurl = getRealPathFromURI(imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

//    }

    public void onTapCamera(View view) {
//        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(i,100);

        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, PICTURE_RESULT);
      }

//    public void onTapCheck() {
//        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
//        if (!textRecognizer.isOperational()){
//            Toast.makeText(this,"no text",Toast.LENGTH_SHORT).show();
//        }else {
//            Frame frame = new Frame.Builder().setBitmap(thumbnail).build();
//            SparseArray<TextBlock> item = textRecognizer.detect(frame);
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = 0 ; i < item.size() ; i++){
//                TextBlock textBlock = item.valueAt(i);
//                stringBuilder.append(textBlock.getValue());
//                stringBuilder.append("\n");
//
//            }
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        onTapCheck();
//    }
}
