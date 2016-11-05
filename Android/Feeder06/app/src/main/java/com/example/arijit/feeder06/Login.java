package com.example.arijit.feeder06;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.arijit.loginapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity implements View.OnClickListener {


    private EditText username1;
    private EditText password1;
    private static TextView attempt;
    private static Button login_button;
    private static final String targetURL = "http://192.168.0.58:8000/Feeder06/slogin/";
    int attempt_counter=5;
    private String username;
    private String password;
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";
    //private OkHttpClient.Builder client;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username1 = (EditText)findViewById(R.id.editText_username);
        password1 = (EditText)findViewById(R.id.editText_password);
        attempt = (TextView)findViewById(R.id.textView_attempt);
        login_button = (Button)findViewById(R.id.button_login);

        login_button.setOnClickListener(this);
        //new Work().execute();

    }
    /* private class Work extends AsyncTask<Void,Void,String>
     {
         Work(String email, String Password) {
             uname = email;
             pwd = Password;
         }

         protected String doInBackground(Void... urls){
             HttpURLConnection httpConnection = null;
             StringBuilder response  = new StringBuilder();
             ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
             postParameters.add(new BasicNameValuePair("username", uname));
             postParameters.add(new BasicNameValuePair("password", pwd));
             String response = null;
             String get_response = null;


             client = new OkHttpClient.Builder();
             client.authenticator(new Authenticator() {
                 @Override
                 public Request authenticate(Route route, Response response) throws IOException {
                     //if (responseCount(response) >= 3) {
                         //return null; // If we've failed 3 times, give up. - in real life, never give up!!
                     //}
                     String credential = Credentials.basic("name", "password");
                     return response.request().newBuilder().header("Authorization", credential).build();
                 }
             });
             client.connectTimeout(10, TimeUnit.SECONDS);
             client.writeTimeout(10, TimeUnit.SECONDS);
             client.readTimeout(30, TimeUnit.SECONDS);
             try {

                 //URL tagetUrl = new URL(targetURL);
                 //httpConnection = (HttpURLConnection) tagetUrl.openConnection();
                 //httpConnection.setDoOutput(true);
                 //httpConnection.setRequestMethod("POST");
                 //httpConnection.setRequestProperty("Content-Type", "application/json");
                 //httpConnection.connect();

                 if (httpConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
                 {
                     BufferedReader input = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()),40);
                     String strLine = null;
                     while ((strLine = input.readLine()) != null)
                     {
                         response.append(strLine);
                     }
                     input.close();
                 System.out.println(response.toString());
                 }



             } catch (MalformedURLException e) {
                 e.printStackTrace();
                 return "MalformedURLException";

             } catch (IOException e) {
                 e.printStackTrace();
                 return "" + httpConnection.getErrorStream();
             }
             return null;
         }

         protected void onPostExecute(String result) {
             if (result==null) {
                 System.out.println("working");
                 LoginButton();
             }
         }
     }*/
    private void userLogin() {
        username = username1.getText().toString().trim();
        password = password1.getText().toString().trim();
        //String uri = String.format("http://somesite.com/some_endpoint.php?param1=%1$s&param2=%2$s",
                //num1,
                //num2);

        //StringRequest myReq = new StringRequest(Request.Method.GET,
         /*       targetURL, Listener<String> listener, ErrorListener errorlistener){}
                //createMyReqSuccessListener(),
                //createMyReqErrorListener());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(myReq);*/

        StringRequest stringRequest = new StringRequest(Request.Method.GET, targetURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            openProfile();
                        }else{
                            Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,username);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile(){
        Intent intent = new Intent(this, User.class);
        intent.putExtra(KEY_USERNAME, username);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        userLogin();
    }
    public void LoginButton(){


        attempt.setText(Integer.toString(attempt_counter));
        final Intent intent = new Intent(this,User.class);
        login_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //finally {

                        //if(httpConnection != null) {
                        //httpConnection.disconnect();
                        //}


                        //if (username.getText().toString().equals("user") & password.getText().toString().equals("pass"))
                        //{
                        Toast.makeText(Login.this, "Username and password is correct",
                                Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(this,User.class);
                        startActivity(intent);

                        //}
                        if (username1.getText().toString().equals("user") & password1.getText().toString().equals("pass")) {
                            Toast.makeText(Login.this, "Username and password is NOT correct",
                                    Toast.LENGTH_SHORT).show();
                            attempt_counter--;
                            attempt.setText(Integer.toString(attempt_counter));
                            if (attempt_counter == 0)
                                login_button.setEnabled(false);
                            //}
                        }
                    }
                }
        );

    }
}


