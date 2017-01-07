package com.cs496.macaron_together_admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by q on 2017-01-05.
 */

public class AddEventActivity extends Activity {
    String startDate;
    String endDate;
    String photo;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //Set typeface to all the text in the layout

        //"시작 날짜 선택", "마감 날짜 선택" DatePicker Setup
        Button start = (Button) findViewById(R.id.start_date);
        Button end = (Button) findViewById(R.id.end_date);
        GregorianCalendar calendar = new GregorianCalendar();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(AddEventActivity.this,
                        listener1, year, month, day);
                dialog.show();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(AddEventActivity.this,
                        listener2, year, month, day);
                dialog.show();
            }
        });

        //"사진 선택"
        Button select_photo = (Button) findViewById(R.id.select_photos);
        select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 0); // RequestCode 0 = pick from gallery
            }
        });


        //"시작하기" Button
        Button complete = (Button) findViewById(R.id.complete);
        complete.setOnClickListener(completeOnClickListener);

    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                try {
                    Uri currImageURI = data.getData();
                    TextView photo_msg = (TextView) findViewById(R.id.photo_msg);
                    photo_msg.setText(currImageURI.toString());
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(currImageURI), null, options);
                    ByteArrayOutputStream ByteStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, ByteStream);
                    byte[] b = ByteStream.toByteArray();
                    photo = Base64.encodeToString(b, Base64.NO_WRAP);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    //"시작하기" 버튼 눌렀을 때
    Button.OnClickListener completeOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            EditText name = (EditText) findViewById(R.id.b_name);
            String shop_name = name.getText().toString();
            EditText addr = (EditText) findViewById(R.id.b_addr);
            String shop_addr = addr.getText().toString();
            EditText p = (EditText) findViewById(R.id.b_price);
            String price = p.getText().toString();
            EditText f = (EditText) findViewById(R.id.b_flavor);
            String flavor = f.getText().toString();
            List<String> flavors = Arrays.asList(flavor.split(","));
            String status = "ONGOING";
            EventData event = new EventData
                    (shop_name,shop_addr,startDate,endDate,price,flavors, photo, status);
            databaseReference.child("events").push().setValue(event);
            Toast.makeText(getApplicationContext(), "추가되었습니다", Toast.LENGTH_SHORT).show();


        }
    };




    private DatePickerDialog.OnDateSetListener listener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String msg = year + "년 " + monthOfYear + 1 + "월 " + dayOfMonth +"일";
            if (dayOfMonth < 10)
                msg = year + "년 " + monthOfYear + 1 + "월 0" + dayOfMonth + "일";
            startDate = msg;
            TextView start_msg = (TextView) findViewById(R.id.start_msg);
            start_msg.setText(msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String msg = year + "년 " + monthOfYear + 1 + "월 " + dayOfMonth +"일";
            if (dayOfMonth < 10)
                msg = year + "년 " + monthOfYear + 1 + "월 0" + dayOfMonth + "일";
            endDate = msg;
            TextView end_msg = (TextView) findViewById(R.id.end_msg);
            end_msg.setText(msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };



}
