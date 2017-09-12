package com.costrella.android.cechini.activities.raport;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.costrella.android.cechini.R;
import com.costrella.android.cechini.activities.realm.RealmInit;
import com.costrella.android.cechini.model.Warehouse;
import com.costrella.android.cechini.services.CechiniService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
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

    private int idImgView = 0;
    private View mProgressView;
    private View scroolView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    Warehouse selectedWarehouse;
    ImageButton picSBtn1, picSBtn2, picSBtn3, rotateLeft1, rotateRight1, rotateLeft2, rotateRight2, rotateLeft3, rotateRight3;
    RelativeLayout relativeLayout;
    Realm realm;
    private boolean internetAccess = true;
    Spinner warehousesSpinner = null;

    float f_rotateLeft1 = 0;
    float f_rotateRight1 = 0;
    float f_rotateLeft2 = 0;
    float f_rotateRight2 = 0;
    float f_rotateLeft3 = 0;
    float f_rotateRight3 = 0;

    private Bitmap rotate(float angle, Bitmap bitmap, ImageView imageView) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        bitmap = rotated;

        imageView.setImageBitmap(rotated);

        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport);
        RealmInit.init(getApplicationContext());
        realm = RealmInit.realm;

        final EditText editText, z_a, z_b, z_c, z_d, z_e, z_f, z_g, z_h;

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutRaport);
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
                RaportController.getInstance(
                        getApplicationContext()).createRaport(selectedWarehouse, bitmap1, bitmap2, bitmap3,
                        editText,
                        z_a, z_b, z_c, z_d, z_e, z_f, z_g, z_h,
                        scroolView, mProgressView, relativeLayout);
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

        picSBtn1 = (ImageButton) findViewById(R.id.btnImg1a);
        setBtnListenerOrDisable(
                picSBtn1,
                mTakePicSOnClickListener1,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        picSBtn2 = (ImageButton) findViewById(R.id.btnImg2a);
        setBtnListenerOrDisable(
                picSBtn2,
                mTakePicSOnClickListener2,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        picSBtn3 = (ImageButton) findViewById(R.id.btnImg3a);
        setBtnListenerOrDisable(
                picSBtn3,
                mTakePicSOnClickListener3,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        rotateLeft1 = (ImageButton) findViewById(R.id.btnRotateLeft1);
        rotateRight1 = (ImageButton) findViewById(R.id.btnRotateRight1);
        rotateLeft2 = (ImageButton) findViewById(R.id.btnRotateLeft2);
        rotateRight2 = (ImageButton) findViewById(R.id.btnRotateRight2);
        rotateLeft3 = (ImageButton) findViewById(R.id.btnRotateLeft3);
        rotateRight3 = (ImageButton) findViewById(R.id.btnRotateRight3);
        rotateLeft1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f_rotateLeft1 -= 90;
                bitmap1 = rotate(f_rotateLeft1, bitmap1, imageView1);
            }
        });
        rotateRight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f_rotateRight1 += 90;
                bitmap1 = rotate(f_rotateRight1, bitmap1, imageView1);
            }
        });
        rotateLeft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f_rotateLeft2 -= 90;
                bitmap2 = rotate(-f_rotateLeft2, bitmap2, imageView2);
            }
        });
        rotateRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f_rotateRight2 += 90;
                bitmap2 = rotate(f_rotateRight2, bitmap2, imageView2);
            }
        });
        rotateLeft3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f_rotateLeft3 -= 90;
                bitmap3 = rotate(-f_rotateLeft3, bitmap3, imageView3);
            }
        });
        rotateRight3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f_rotateRight2 += 90;
                bitmap3 = rotate(f_rotateRight2, bitmap3, imageView3);
            }
        });


        warehousesSpinner = (Spinner) findViewById(R.id.spinner);
        Call<List<Warehouse>> warehouseCall = CechiniService.getInstance().getCechiniAPI().getWarehousesMobi();

        warehouseCall.enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                int code = response.code();
                if (code == 200) {
                    List<Warehouse> warehouses = response.body();
                    addToListActivity(warehouses);
                    realm.beginTransaction();
                    realm.delete(Warehouse.class);
                    realm.commitTransaction();
                    realm.beginTransaction();
                    realm.copyToRealm(warehouses);
                    realm.commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Log.e("costrella:", " Nie ma internetu");
                realm.beginTransaction();
                List<Warehouse> warehouseList = realm.where(Warehouse.class).findAll();
                addToListActivity(warehouseList);
                realm.cancelTransaction();

            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            picSBtn1.setEnabled(false);
            picSBtn2.setEnabled(false);
            picSBtn3.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

    }

    Uri imageUri;
    ContentValues values;


    private void addToListActivity(List<Warehouse> warehouses) {
        final List<Warehouse> list = new ArrayList<Warehouse>();
        Warehouse empty = new Warehouse();
        empty.setName("");
        empty.setId(99999L);
        list.add(empty);
        list.addAll(warehouses);
        ArrayAdapter<Warehouse> dataAdapter = new ArrayAdapter<Warehouse>(RaportActivity.this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        warehousesSpinner.setAdapter(dataAdapter);
        warehousesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedWarehouse = list.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                picSBtn1.setEnabled(true);
                picSBtn2.setEnabled(true);
                picSBtn3.setEnabled(true);
            }
        }
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

    private Bitmap scale(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / scale, bitmap.getHeight() / scale, true);
    }

}
