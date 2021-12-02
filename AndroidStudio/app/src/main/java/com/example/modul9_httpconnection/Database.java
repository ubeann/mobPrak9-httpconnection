package com.example.modul9_httpconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Database extends AppCompatActivity {

    EditText username, password;
    Button btnAdd;
    private String url = "http://10.0.2.2:8080/mobile/insert.php";
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(username.getText().toString().equals(""));
                if(username.getText().toString().equals("") | password.getText().toString().equals("")){
                    System.out.println("kosong");
                    Toast.makeText(getApplicationContext(), "Please fill in required fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        String usernameTxt = URLEncoder.encode(username.getText().toString(), "utf-8");
                        String passwordTxt = URLEncoder.encode(password.getText().toString(), "utf-8");
                        url = "http://10.0.2.2:8080/mobile/insert.php";
                        url += "?username=" + usernameTxt +"&password=" + passwordTxt;
                        getRequest();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Log.d("wow",e.toString());
                    }
                }

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
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
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