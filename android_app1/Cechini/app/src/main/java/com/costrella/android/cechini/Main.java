package com.costrella.android.cechini;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.costrella.android.cechini.model.Person;
import com.costrella.android.cechini.model.Raport;
import com.costrella.android.cechini.model.Store;
import com.costrella.android.cechini.services.CechiniService;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mike on 2016-09-14.
 */
public class Main extends AppCompatActivity implements View.OnClickListener{

    private EditText value;
    private Button btn;
    private Button galleryBtn;
    private ProgressBar pb;
    Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private CechiniService cechiniService;
    Store store = null;
    Person person = null;

    private void getStore(String id){
        store = null;
        Call<Store> call = cechiniService.getCechiniAPI().getStore(id);

        //asynchronous call
        call.enqueue(new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> response) {
                int code = response.code();
                if (code == 200) {
                    store = response.body();
                    Toast.makeText(getApplicationContext(), "Got the store: " + store.getName(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Did not work: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Store> call, Throwable t) {
                Log.e("s", "d");

            }
        });
    }

    private void getPerson(String id){
        person = null;
        Call<Person> call = cechiniService.getCechiniAPI().getPerson(id);

        //asynchronous call
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                int code = response.code();
                if (code == 200) {
                    person = response.body();
                    Toast.makeText(getApplicationContext(), "Got the person: " + person.getName(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Did not work: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e("s", "d");

            }
        });
    }

    private void createRaport(){
        Call<Store> callStore = cechiniService.getCechiniAPI().getStore("1004");

        //asynchronous call
        callStore.enqueue(new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> response) {
                int code = response.code();
                if (code == 200) {
                    store = response.body();
                    Call<Person> callPerson = cechiniService.getCechiniAPI().getPerson("1001");
                    callPerson.enqueue(new Callback<Person>() {
                        @Override
                        public void onResponse(Call<Person> call, Response<Person> response) {
                            int code = response.code();
                            if (code == 200) {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream .toByteArray();
                                Raport raport = new Raport();
                                raport.setDescription("lodz");
                                raport.setPerson(person);
                                raport.setStore(store);
                                raport.setFoto1(byteArray);
                                Call<Raport> callRaport = cechiniService.getCechiniAPI().createRaport(raport);
                                callRaport.enqueue(new Callback<Raport>() {
                                    @Override
                                    public void onResponse(Call<Raport> call, Response<Raport> response) {
                                        int code = response.code();
                                        Toast.makeText(getApplicationContext(), "creating raport: " + code, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Raport> call, Throwable t) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onFailure(Call<Person> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Store> call, Throwable t) {
                Log.e("s", "d");

            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cechiniService = CechiniService.getInstance();
        setContentView(R.layout.home_layout);
        value=(EditText)findViewById(R.id.editText1);
        btn=(Button)findViewById(R.id.button1);
        galleryBtn=(Button)findViewById(R.id.galleryBtn);
        pb=(ProgressBar)findViewById(R.id.progressBar1);
        pb.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galllery();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
//        if(value.getText().toString().length()<1){
//
//            // out of range
//            Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
//        }else{
//            pb.setVisibility(View.VISIBLE);
//            execute();
//            new MyAsyncTask().execute(value.getText().toString());
        createRaport();
//        }


    }

    public static void execute() {
        Map<String, String> comment = new HashMap<String, String>();
        comment.put("name", "Using the GSON library");
        comment.put("surname", "Using libraries is convenient.");
        String json = new GsonBuilder().create().toJson(comment, Map.class);
        makeRequest("http://192.168.0.10:8080/api/people", json);
    }

    public static HttpResponse makeRequest(String uri, String json) {
        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            return new DefaultHttpClient().execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void galllery(){
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
//            postRaport(params[0]);
//            postData(params[0]);
//            postEntityTest2();
            getStore();
            return null;
        }

        protected void onPostExecute(Double result){
            pb.setVisibility(View.GONE);
        }
        protected void onProgressUpdate(Integer... progress){
            pb.setProgress(progress[0]);
        }

        public void postData(String valueIWantToSend) {
            try {
                Map<String, String> comment = new HashMap<String, String>();
                comment.put("name", valueIWantToSend);
                comment.put("surname", valueIWantToSend);
                String json = new GsonBuilder().create().toJson(comment, Map.class);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.0.10:8080/api/people");
                httppost.setEntity(new StringEntity(json));
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                HttpResponse response = httpclient.execute(httppost);
                Log.e("a",response.toString());

            } catch (ClientProtocolException e) {
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
            }
        }
        public void postRaport(String valueIWantToSend) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
//                String picture = Base64.encodeToString(byteArray, Base64.DEFAULT);


//                Map<String, String> comment = new HashMap<String, String>();
                Map<String, Object> comment1 = new HashMap<String, Object>();
                comment1.put("data", "2016-09-13");
                comment1.put("description", "ele elegancko");
                comment1.put("foto1", byteArray);
                String json = new GsonBuilder().create().toJson(comment1, Map.class);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.0.10:8080/api/raports");
                httppost.setEntity(new StringEntity(json));
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                HttpResponse response = httpclient.execute(httppost);
//                if(response.getStatusLine().getStatusCode() != 201){
//                    Toast.makeText(getApplicationContext(), "ERROR code: " + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
//                }
                Log.e("a",response.toString());

            } catch (ClientProtocolException e) {
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
            }
        }
        public void postEntityTest2() {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String picture = Base64.encodeToString(byteArray, Base64.DEFAULT);


                Map<String, byte[]> comment1 = new HashMap<String, byte[]>();
                comment1.put("test1", byteArray);
                String json = new GsonBuilder().create().toJson(comment1, Map.class);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.0.10:8080/api/entitytest-2-s");
                httppost.setEntity(new StringEntity(json));
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                HttpResponse response = httpclient.execute(httppost);
                Log.e("a",response.toString());

            } catch (ClientProtocolException e) {
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
            }
        }

        public void getStore(){
            try {
                InputStream inputStream = null;
                HttpClient httpclient = new DefaultHttpClient();
                int id = 1003;
                HttpGet httpGet = new HttpGet("http://192.168.0.10:8080/api/stores/"+id);
                HttpResponse response = httpclient.execute(httpGet);
                inputStream = response.getEntity().getContent();
                JSONObject json = new JSONObject(convertInputStreamToString(inputStream));
                //mozna budowac model
                Log.e("json", json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

    }
}