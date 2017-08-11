package com.example.ayeshashahid.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    EditText email,pwd;
    Button signin;
    String emails,pswd,success,userid;
    HTTPURLConnection service;
    SessionManager session;

String url="http://192.168.1.24/course_service/Course_service.asmx/login";
JSONObject jsonobject;
    JSONArray jsonarray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        toolbar.setLogo(R.drawable.logo);
//        ImageView logo=(ImageView)findViewById(R.id.logo);
//        Picasso.with(this)
//                .load("http://ca1.risknucleus.com/lms_new/library/images/logo.png")
//                .into(logo);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        email=(EditText)findViewById(R.id.email);
        pwd=(EditText)findViewById(R.id.pwd);
        signin=(Button)findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


    }

    private void attemptLogin() {

        // Reset errors.
        email.setError(null);
        pwd.setError(null);



//        boolean cancel = false;
//        View focusView = null;

// Check for a valid password, if the user entered one.
//        if (TextUtils.isEmpty(pswd) && isPasswordValid(pswd)) {
//            pwd.setError(getString(R.string.error_invalid_password));
//            focusView = pwd;
//            cancel = true;
//        }


        email.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(email, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
  pwd.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validation.isPasswordValid(pwd, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
if (checkValidation() ){
    // Store values at the time of the login attempt.
    pswd = pwd.getText().toString();
    emails=email.getText().toString();
    // perform the user login attempt.
    service = new HTTPURLConnection();
    new UserLogin().execute();
}
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        }
 else {
    Toast.makeText(this, "Form contains error", Toast.LENGTH_LONG).show();

        }
    }
    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.isEmailAddress(email, true)) ret = false;
        if (!Validation.isPasswordValid(pwd, true)) ret = false;

        return ret;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
                searchViewAndroidActionBar.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return true;

    }
    // Download JSON file AsyncTask
 private  class UserLogin extends AsyncTask<Void, Void, Void> {
        HashMap<String, String> postDataParams;
        String response = "";

        @Override
        protected Void doInBackground(Void... params) {

            // JSON file URL address
            //   jsonobject = JSONfunctions.getJSONfromURL(subcategory_url);
            postDataParams = new HashMap<String, String>();
            postDataParams.put("email", emails);
            postDataParams.put("pwd", pswd);
            //Call ServerData() method to call webservice and store result in response
            response = service.ServerData(url, postDataParams);
            Log.d("response", response);
           try {
                jsonobject = new JSONObject(response);
         success=jsonobject.getString("success");
               if (success.equals("True")) {
                   JSONArray jsonArray=jsonobject.getJSONArray("Userdetail");
                   for (int i = 0; i < jsonArray.length(); i++) {
                       JSONObject json = jsonArray.getJSONObject(i);
                       userid=json.getString("userID");

                   }
                   Log.d("userid",userid);

               }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (success.equals("True")){
                Toast.makeText(getApplicationContext(), "Login successfully..!", Toast.LENGTH_LONG).show();
                session.createLoginSession(userid);
                Intent i = new Intent(getApplicationContext(), test.class);
                startActivity(i);
                finish();

            }
else
                Toast.makeText(getApplicationContext(), "Opps...! It seems something went wrong. Kindly re-enter the relevent data and please proceed again.", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.login) {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }
        if (id == R.id.signin) {
            Intent i=new Intent(this,registeration.class);
            startActivity(i);
        }
        if (id == R.id.browse_course) {
            Toast.makeText(this,"Browse course",Toast.LENGTH_LONG).show();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void signup(View view){
        Intent i=new Intent(this,registeration.class);
        startActivity(i);
    }
}
