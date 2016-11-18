package com.costrella.android.cechini.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.costrella.android.cechini.Constants;
import com.costrella.android.cechini.R;
import com.costrella.android.cechini.model.Raport;
import com.costrella.android.cechini.model.Warehouse;
import com.costrella.android.cechini.services.CechiniService;
import com.costrella.android.cechini.services.DayService;
import com.costrella.android.cechini.services.PersonService;
import com.costrella.android.cechini.services.StoreService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaportActivity extends AppCompatActivity {
    private static final int ACTION_TAKE_PHOTO_6 = 6;
    private static final int ACTION_TAKE_PHOTO_S = 2;
    private int PICK_IMAGE_REQUEST = 1;
    private int scale = 4;
    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;
    EditText editText, z_a, z_b, z_c, z_d, z_e, z_f, z_g, z_h;
    private int idImgView = 0;
    private View mProgressView;
    private View scroolView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    Warehouse selectedWarehouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);

        mProgressView = findViewById(R.id.raport_progress);
        scroolView = findViewById(R.id.scrollView);
        ImageButton btnImg1 = (ImageButton) findViewById(R.id.btnImg1);
        ImageButton btnImg2 = (ImageButton) findViewById(R.id.btnImg2);
        ImageButton btnImg3 = (ImageButton) findViewById(R.id.btnImg3);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        editText = (EditText) findViewById(R.id.editText2);
        z_a = (EditText) findViewById(R.id.z_a);
        z_b = (EditText) findViewById(R.id.z_b);
        z_c = (EditText) findViewById(R.id.z_c);
        z_d = (EditText) findViewById(R.id.z_d);
        z_e = (EditText) findViewById(R.id.z_e);
        z_f = (EditText) findViewById(R.id.z_f);
        z_g = (EditText) findViewById(R.id.z_g);
        z_h = (EditText) findViewById(R.id.z_h);

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

        ImageButton picSBtn1 = (ImageButton) findViewById(R.id.btnImg1a);
        setBtnListenerOrDisable(
                picSBtn1,
                mTakePicSOnClickListener1,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        ImageButton picSBtn2 = (ImageButton) findViewById(R.id.btnImg2a);
        setBtnListenerOrDisable(
                picSBtn2,
                mTakePicSOnClickListener2,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        ImageButton picSBtn3 = (ImageButton) findViewById(R.id.btnImg3a);
        setBtnListenerOrDisable(
                picSBtn3,
                mTakePicSOnClickListener3,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        final Spinner warehouses = (Spinner) findViewById(R.id.spinner);
        Call<List<Warehouse>> warehouseCall = CechiniService.getInstance().getCechiniAPI().getWarehousesMobi();

        warehouseCall.enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                int code = response.code();
                if(code == 200){
                    final List<Warehouse> list = response.body();
                    ArrayAdapter<Warehouse> dataAdapter = new ArrayAdapter<Warehouse>(RaportActivity.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    warehouses.setAdapter(dataAdapter);
                    warehouses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            System.out.print("test");
                            selectedWarehouse = list.get(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {

            }
        });

    }

    Uri imageUri;
    ContentValues values;

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void goToMediaStore(int value) {

        idImgView = value;
        values = new ContentValues();
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, ACTION_TAKE_PHOTO_6);
    }

    Button.OnClickListener mTakePicSOnClickListener1 =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToMediaStore(1);
                }
            };
    Button.OnClickListener mTakePicSOnClickListener2 =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToMediaStore(2);
                }
            };
    Button.OnClickListener mTakePicSOnClickListener3 =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToMediaStore(3);
                }
            };

    private void handleSmallCameraPhoto(Bitmap bitmap, ImageView imageView) {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    getContentResolver(), imageUri);
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / scale, bitmap.getHeight() / scale, true);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBtnListenerOrDisable(
            ImageButton btn,
            ImageButton.OnClickListener onClickListener,
            String intentName
    ) {
        if (isIntentAvailable(this, intentName)) {
            btn.setOnClickListener(onClickListener);
        } else {
//            btn.setText(
//                    "cannot" + " " + btn.getText());
            btn.setClickable(false);
        }
    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
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

        if (requestCode == ACTION_TAKE_PHOTO_6) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    switch (idImgView) {
                        case 1:
                            try {
                                bitmap1 = MediaStore.Images.Media.getBitmap(
                                        getContentResolver(), imageUri);
                                bitmap1 = scale(bitmap1);
                                imageView1.setImageBitmap(bitmap1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            bitmap2 = MediaStore.Images.Media.getBitmap(
                                    getContentResolver(), imageUri);
                            bitmap2 = scale(bitmap2);
                            imageView2.setImageBitmap(bitmap2);
                            break;
                        case 3:
                            bitmap3 = MediaStore.Images.Media.getBitmap(
                                    getContentResolver(), imageUri);
                            bitmap3 = scale(bitmap3);
                            imageView3.setImageBitmap(bitmap3);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                switch (idImgView) {
                    case 1:
                        bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        bitmap1 = scale(bitmap1);
                        imageView1.setImageBitmap(bitmap1);
                        break;
                    case 2:
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        bitmap2 = scale(bitmap2);
                        imageView2.setImageBitmap(bitmap2);
                        break;
                    case 3:
                        bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        bitmap3 = scale(bitmap3);
                        imageView3.setImageBitmap(bitmap3);
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap scale (Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / scale, bitmap.getHeight() / scale, true);
    }

    private byte[] getImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] byteArray = baos.toByteArray();
        return byteArray;
    }

    private int getInt(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            return 0;
        } else {
            return ((Integer.parseInt(editText.getText().toString())));
        }
    }

    private void createRaport() {
        showProgress(true);
        Raport raport = new Raport();
        if(selectedWarehouse != null){
            raport.setWarehouse(selectedWarehouse);
        }
        if (DayService.selectedDay != null)
            raport.setDay(DayService.selectedDay);
        if (bitmap1 != null) {
            raport.setFoto1(getImage(bitmap1));
        }
        if (bitmap2 != null) {
            raport.setFoto2(getImage(bitmap2));
        }
        if (bitmap3 != null) {
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
        raport.setZ_f(getInt(z_f));
        raport.setZ_g(getInt(z_g));
        raport.setZ_h(getInt(z_h));

        Call<Raport> callRaport = CechiniService.getInstance().getCechiniAPI().createRaport(raport);
        callRaport.enqueue(new Callback<Raport>() {
            @Override
            public void onResponse(Call<Raport> call, Response<Raport> response) {
                int code = response.code();
                showProgress(false);
                if (code == 201) {
                    Toast.makeText(getApplicationContext(), Constants.RAPORT_SUCCESS, Toast.LENGTH_LONG).show();
                } else {
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
