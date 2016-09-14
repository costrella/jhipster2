package com.costrella.android.cechini;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 2016-09-14.
 */
public class Main extends AppCompatActivity implements View.OnClickListener {

    private EditText value;
    private Button btn;
    private ProgressBar pb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        value=(EditText)findViewById(R.id.editText1);
        btn=(Button)findViewById(R.id.button1);
        pb=(ProgressBar)findViewById(R.id.progressBar1);
        pb.setVisibility(View.GONE);
        btn.setOnClickListener(this);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(value.getText().toString().length()<1){

            // out of range
            Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
        }else{
            pb.setVisibility(View.VISIBLE);
//            execute();
            new MyAsyncTask().execute(value.getText().toString());
        }


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

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData(params[0]);
            return null;
        }

        protected void onPostExecute(Double result){
            pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }
        protected void onProgressUpdate(Integer... progress){
            pb.setProgress(progress[0]);
        }

        public void postData(String valueIWantToSend) {
            // Create a new HttpClient and Post Header


            try {
                // Add your data


//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                nameValuePairs.add(new BasicNameValuePair("name", valueIWantToSend));
//                nameValuePairs.add(new BasicNameValuePair("surname", valueIWantToSend));
//                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                // Execute HTTP Post Request


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
                // TODO Auto-generated catch block
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }

    }
}