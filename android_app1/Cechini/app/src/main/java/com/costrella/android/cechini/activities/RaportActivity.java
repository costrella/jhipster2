package com.costrella.android.cechini.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.costrella.android.cechini.Constants;
import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Raport;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.PersonService;
import com.costrella.android.cechini.services.StoreService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaportActivity extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;
    EditText editText;
    private int idImgView = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport);
        Button btnImg1 = (Button) findViewById(R.id.btnImg1);
        Button btnImg2 = (Button) findViewById(R.id.btnImg2);
        Button btnImg3 = (Button) findViewById(R.id.btnImg3);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        editText = (EditText) findViewById(R.id.editText2);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRaport();
            }
        });

        btnImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImgSelected(1);
            }
        });
        btnImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImgSelected(2);
            }
        });
        btnImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImgSelected(3);
            }
        });

    }

    private void startImgSelected(int id) {
        idImgView = id;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                switch (idImgView) {
                    case 1:
                        bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
                        imageView1.setImageBitmap(bitmap1);
                        break;
                    case 2:
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
                        imageView2.setImageBitmap(bitmap2);
                        break;
                    case 3:
                        bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
                        imageView3.setImageBitmap(bitmap3);
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createRaport() {
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 20, baos1);
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 20, baos2);
        bitmap3.compress(Bitmap.CompressFormat.JPEG, 20, baos3);
        byte[] byteArray1 = baos1.toByteArray();
        byte[] byteArray2 = baos2.toByteArray();
        byte[] byteArray3 = baos3.toByteArray();
        Raport raport = new Raport();
        if (editText.getText() != null) {
            raport.setDescription(editText.getText().toString());
        } else {
            raport.setDescription("empty");
        }
        raport.setPerson(PersonService.PERSON);
        raport.setStore(StoreService.STORE);
        raport.setFoto1(byteArray1);
        raport.setFoto2(byteArray2);
        raport.setFoto3(byteArray3);
        Call<Raport> callRaport = CechiniService.getInstance().getCechiniAPI().createRaport(raport);
        callRaport.enqueue(new Callback<Raport>() {
            @Override
            public void onResponse(Call<Raport> call, Response<Raport> response) {
                int code = response.code();
                if(code == 201){
                    Toast.makeText(getApplicationContext(), Constants.RAPORT_SUCCESS, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), Constants.SOMETHING_WRONG + code, Toast.LENGTH_LONG).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<Raport> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Constants.SOMETHING_WRONG, Toast.LENGTH_LONG).show();

            }
        });
    }
}
