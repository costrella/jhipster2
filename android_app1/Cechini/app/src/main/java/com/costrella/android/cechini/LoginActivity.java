package com.costrella.android.cechini;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.costrella.android.cechini.login.LoginService;
import com.costrella.android.cechini.services.CechiniService;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText login;
    private EditText pass;
    private Button btn;
    private CechiniService cechiniService;
    private LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cechiniService = CechiniService.getInstance();

        login = (EditText) findViewById(R.id.login);
        pass = (EditText) findViewById(R.id.pass);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (login.getText().toString().length() < 1 || pass.getText().toString().length() < 1) {
            // out of range
            Toast.makeText(this, "wypelnil login i haslo !", Toast.LENGTH_LONG).show();
        } else {
            loginService = LoginService.getInstance();
            loginService.doLogin(getApplicationContext(), login.getText().toString(), pass.getText().toString());
        }
    }
}
