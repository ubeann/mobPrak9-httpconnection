package com.example.modul9_httpconnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Database extends AppCompatActivity {

    EditText username, password;
    Button btnAdd;
    private String url = "http://10.0.2.2:80/mobile/insert.php";
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            System.out.println(username.getText().toString().equals(""));
            if(username.getText().toString().equals("") | password.getText().toString().equals("")){
                System.out.println("kosong");
                Toast.makeText(getApplicationContext(), "Please fill in required fields", Toast.LENGTH_SHORT).show();
            }
            else{
                try{
                    String usernameTxt = URLEncoder.encode(username.getText().toString(), "utf-8");
                    String passwordTxt = URLEncoder.encode(password.getText().toString(), "utf-8");
                    url = "http://10.0.2.2:80/mobile/insert.php";
                    url += "?username=" + usernameTxt +"&password=" + passwordTxt;
                    getRequest();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.d("wow",e.toString());
                }
            }

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
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    };

    public static String request(HttpResponse response){
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