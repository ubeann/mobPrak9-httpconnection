package com.example.modul9_httpconnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        btnLogin.setOnClickListener(view -> {
            url="http://10.0.2.2:80/mobile/login.php";
            url +="?user="+etUsername.getText().toString()+"&password="+etPassword.getText().toString();
            getRequest();
        });
    }
    private void getRequest(){
        Log.d("getRequest",url);
        new Thread(() -> {
            try{
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(url);
                HttpResponse response = client.execute(request);
                result = request(response);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            handler.sendEmptyMessage(0);
        }).start();
    }

    final Handler handler = new Handler(){
        public void handleMessage (Message msg){
            super.handleMessage(msg);
            etStatus.setText(result);
        }
    };

    public static String request(HttpResponse response) {
        String result;
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                str.append(line).append("\n");
            }
            in.close();
            result = str.toString();
        }catch(Exception ex){
            result = "Error";
        }
        return result;
    }

}