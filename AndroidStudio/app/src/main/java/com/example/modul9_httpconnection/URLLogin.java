package com.example.modul9_httpconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class URLLogin extends AppCompatActivity {
    EditText etUsername, etPassword, etStatus;
    Button btnLogin;
    String url="http://10.0.2.2:80/mobile/login.php";
    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urllogin);
        etUsername = findViewById(R.id.etuserName);
        etPassword = findViewById(R.id.etPassword);
        etStatus = findViewById(R.id.etStatus);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url="http://10.0.2.2:80/mobile/login.php";
                url +="?user="+etUsername.getText().toString()+"&password="+etPassword.getText().toString();
                getRequest();
            }
        });
    }
    private void getRequest(){
        Log.d("getRequest",url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet(url);
                    HttpResponse response = client.execute(request);
                    result = request(response);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
            }).start();
    }

    Handler handler = new Handler(){
        public void handleMessage (Message msg){
            super.handleMessage(msg);
            etStatus.setText(result);
        }
    };

    /**
     * Method untuk Menerima data dari server
     * @param response
     * @return
     */
    public static String request(HttpResponse response){
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        }catch(Exception ex){
            result = "Error";
        }
        return result;
    }

}