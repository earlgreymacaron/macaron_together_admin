package com.cs496.macaron_together_admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by q on 2017-01-03.
 */

public class EventView extends Activity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    EventData event;
    TextView shop_name;
    TextView shop_addr;
    TextView price;
    ImageView thumnail;
    TextView start_date;
    TextView end_date;
    Button status;
    Button save;

    String encoded;
    String startDate;
    String endDate;
    String state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
        Drawable alpha = findViewById(R.id.eventview).getBackground();
        alpha.setAlpha(90);

        //Get album to View
        Intent intent = getIntent();
        event = (EventData) intent.getSerializableExtra("EventData");

        //Setup page with data
        shop_name = (TextView) findViewById(R.id.c_name);
        shop_name.setText(event.getShopName());
        shop_addr = (TextView) findViewById(R.id.c_addr);
        shop_addr.setText(event.getShopAddr());
        price = (TextView) findViewById(R.id.c_price);
        price.setText(event.getPrice()+"원/개");
        thumnail = (ImageView) findViewById(R.id.c_thumnail);
        byte[] decodedString = Base64.decode(event.getPhotos(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        thumnail.setImageBitmap(decodedByte);
        thumnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 0); // RequestCode 0 = pick from gallery
            }
        });
        start_date = (TextView) findViewById(R.id.start_msg);
        start_date.setText(event.getStartDate());
        end_date = (TextView) findViewById(R.id.end_msg);
        end_date.setText(event.getEndDate());
        status = (Button) findViewById(R.id.c_status);
        status.setText(event.getStatus());
        status.setOnClickListener(statusOnClickListener);
        GregorianCalendar calendar = new GregorianCalendar();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        Button start = (Button) findViewById(R.id.start_date);
        Button end = (Button) findViewById(R.id.end_date);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(EventView.this,
                        listener1, year, month, day);
                dialog.show();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(EventView.this,
                        listener2, year, month, day);
                dialog.show();
            }
        });

        save = (Button) findViewById(R.id.complete);
        save.setOnClickListener(saveOnClickListener);

    }

    //클릭하면 바뀌는 진행상황 버튼
    Button.OnClickListener statusOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            state = status.getText().toString();
            if (state.contains("진행중")) {
                state = "배송중";
                status.setText(state);
            } else if (state.contains("배송중")) {
                state = "녹는중";
                status.setText(state);
            } else if (state.contains("녹는중")) {
                state = "공구완료";
                status.setText(state);
            } else if (state.contains("공구완료")) {
                state = "진행중";
                status.setText(state);
            }
        }
    };

    //"저장하기" 버튼을 눌렀을 때 - 각각 변경사항을 확인하고 변경되었으면 DB에 업데이트
    Button.OnClickListener saveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatabaseReference branch = databaseReference.child("events").child(event.getShopName());
            if (encoded != null)
                branch.child("photos").setValue(encoded);
            if (state != null)
                branch.child("status").setValue(state);
            if (startDate != null)
                branch.child("startDate").setValue(startDate);
            if (endDate != null)
                branch.child("endDate").setValue(endDate);
            Toast.makeText(getApplicationContext(), "수정되었습니다", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                try {
                    Uri currImageURI = data.getData();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(currImageURI), null, options);
                    thumnail.setImageBitmap(bitmap);
                    ByteArrayOutputStream ByteStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, ByteStream);
                    byte[] b = ByteStream.toByteArray();
                    encoded = Base64.encodeToString(b, Base64.NO_WRAP);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private DatePickerDialog.OnDateSetListener listener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String msg = year + "/" + monthOfYear + 1 + "/" + dayOfMonth;
            if (dayOfMonth < 10)
                msg = year + "/" + monthOfYear + 1 + "/0" + dayOfMonth;
            startDate = msg;
            start_date.setText(msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String msg = year + "/" + monthOfYear + 1 + "/" + dayOfMonth;
            if (dayOfMonth < 10)
                msg = year + "/" + monthOfYear + 1 + "/0" + dayOfMonth;
            endDate = msg;
            end_date.setText(msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };

}
