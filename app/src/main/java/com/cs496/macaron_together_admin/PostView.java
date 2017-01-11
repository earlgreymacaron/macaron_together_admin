package com.cs496.macaron_together_admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by q on 2017-01-10.
 */

public class PostView extends Activity {
    PostData post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_view);

        Drawable alpha = findViewById(R.id.postview).getBackground();
        alpha.setAlpha(90);

        //Get post data to View
        Intent intent = getIntent();
        post = (PostData) intent.getSerializableExtra("post");

        TextView title = (TextView) findViewById(R.id.e_title);
        TextView author = (TextView) findViewById(R.id.e_author);
        TextView timestamp = (TextView) findViewById(R.id.e_timestamp);
        TextView body = (TextView) findViewById(R.id.e_body);
        title.setText(post.getTitle());
        java.util.Date time =new java.util.Date((long)Long.parseLong(post.getTimestamp()));
        String date[] = time.toString().split(" ");
        String stamp = date[1] + " " + date[2] + ", " + date[5] + " " + date[3];
        author.setText("작성자: " + post.getAuthor());
        timestamp.setText("작성시간: " + stamp);
        body.setText(post.getBody());

        //Popup Window Size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.5));
    }

}
