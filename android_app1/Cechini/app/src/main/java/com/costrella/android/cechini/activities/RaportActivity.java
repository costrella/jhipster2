package com.costrella.android.cechini.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
import com.costrella.android.cechini.services.DayService;
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
    EditText editText, z_a, z_b, z_c, z_d, z_e;
    private int idImgView = 0;
    private View mProgressView;
    private View scroolView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport);
        mProgressView = findViewById(R.id.raport_progress);
        scroolView = findViewById(R.id.scrollView);
        Button btnImg1 = (Button) findViewById(R.id.btnImg1);
        Button btnImg2 = (Button) findViewById(R.id.btnImg2);
        Button btnImg3 = (Button) findViewById(R.id.btnImg3);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        editText = (EditText) findViewById(R.id.editText2);
        z_a = (EditText) findViewById(R.id.z_a);
        z_b = (EditText) findViewById(R.id.z_b);
        z_c = (EditText) findViewById(R.id.z_c);
        z_d = (EditText) findViewById(R.id.z_d);
        z_e = (EditText) findViewById(R.id.z_e);
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

    private byte[] getImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] byteArray = baos.toByteArray();
        return byteArray;
    }

    private int getInt(EditText editText){
        if(editText.getText().toString().isEmpty()){
            return 0;
        }else{
            return ((Integer.parseInt(editText.getText().toString())));
        }
    }

    private void createRaport() {
        showProgress(true);
        Raport raport = new Raport();
        raport.setDay(DayService.selectedDay);
        if(bitmap1 != null){
            raport.setFoto1(getImage(bitmap1));
        }
        if(bitmap2 != null){
            raport.setFoto2(getImage(bitmap2));
        }
        if(bitmap3 != null){
            raport.setFoto3(getImage(bitmap3));
        }

        raport.setDescription(editText.getText().toString());

        raport.setPerson(PersonService.PERSON);
        raport.setStore(StoreService.STORE);
        raport.setZ_a(getInt(z_a));
        raport.setZ_b(getInt(z_b));
        raport.setZ_c(getInt(z_c));
        raport.setZ_d(getInt(z_d));
        raport.setZ_e(getInt(z_e));

        Call<Raport> callRaport = CechiniService.getInstance().getCechiniAPI().createRaport(raport);
        callRaport.enqueue(new Callback<Raport>() {
            @Override
            public void onResponse(Call<Raport> call, Response<Raport> response) {
                int code = response.code();
                showProgress(false);
                if(code == 201){
                    Toast.makeText(getApplicationContext(), Constants.RAPORT_SUCCESS, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), Constants.SOMETHING_WRONG + code, Toast.LENGTH_LONG).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<Raport> call, Throwable t) {
                showProgress(false);
                Toast.makeText(getApplicationContext(), Constants.SOMETHING_WRONG, Toast.LENGTH_LONG).show();

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            scroolView.setVisibility(show ? View.GONE : View.VISIBLE);
            scroolView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    scroolView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            scroolView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
