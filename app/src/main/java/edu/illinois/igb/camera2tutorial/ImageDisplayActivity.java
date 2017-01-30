package edu.illinois.igb.camera2tutorial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(Camera2TutActivity.TAG,"Create ImageDisplayActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        Intent intent = getIntent();
        ImageView imageView = (ImageView) findViewById(R.id.imageview);
        imageView.setImageBitmap(BitmapFactory.decodeFile(intent.getStringExtra("path")));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        float leafArea = intent.getFloatExtra("leafarea",0f);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(leafArea+" sq in");
    }

    public void goback(View v){
        finish();
    }

}
