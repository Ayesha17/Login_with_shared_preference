package com.example.ayeshashahid.myapplication;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
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

import org.json.JSONObject;

import java.util.HashMap;

public class registeration extends AppCompatActivity {
    SearchView searchView;
    EditText firstname,lastname,email,password,confirm_password;
    String frstname,lstname,emails,pwd,c_pwd,success;
HTTPURLConnection service;
    JSONObject jsonobject;
    String url="http://192.168.1.24/course_service/Course_service.asmx/signup";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       // toolbar.setTitle("");
          toolbar.setLogo(R.drawable.logo);
//        ImageView logo=(ImageView)findViewById(R.id.logo);
//        Picasso.with(this)
//                .load("http://ca1.risknucleus.com/lms_new/library/images/logo.png")
//                .into(logo);

        firstname=(EditText)findViewById(R.id.first_name);
        lastname=(EditText)findViewById(R.id.last_name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pwd);
        confirm_password=(EditText)findViewById(R.id.confirm_pwd);


Button signup=(Button)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptsignup();
            }
        });
    }

    private void attemptsignup() {

        // Reset errors.
        firstname.setError(null);
        lastname.setError(null);
        email.setError(null);
        password.setError(null);
        confirm_password.setError(null);

        firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Validation.hasTexts(firstname, true);
            }
        });

        lastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Validation.hasTexts(lastname, true);
            }
        });

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

        password.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validation.isPasswordValid(password, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        confirm_password.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validation.isPasswordValid(confirm_password, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        if (checkValidation() ){
            // Store values at the time of the login attempt.
            frstname = firstname.getText().toString();
            lstname = lastname.getText().toString();
            emails = email.getText().toString();
            pwd = password.getText().toString();
    if(pwd.equals(confirm_password.getText().toString()))

    {    // perform the user login attempt.
        service = new HTTPURLConnection();
        new UserSignup().execute();
    }
    else
        confirm_password.setError("Passwords don't match. Try again?");
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
        if (!Validation.isPasswordValid(password, true)) ret = false;
        if (!Validation.isPasswordValid(confirm_password, true)) ret = false;
        if (!Validation.hasTexts(firstname, true)) ret = false;
        if (!Validation.hasTexts(lastname, true)) ret = false;

        return ret;
    }




    public void signin(View view){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
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
             //   Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return true;

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

    private class UserSignup extends AsyncTask<Void,Void,Void>{
        HashMap<String, String> postDataParams;
        String response = "";

        protected void onPreExecute(Void args) {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(registeration.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
// JSON file URL address
            //   jsonobject = JSONfunctions.getJSONfromURL(subcategory_url);
            postDataParams = new HashMap<String, String>();
            postDataParams.put("firstname", frstname);
            postDataParams.put("lastname", lstname);
            postDataParams.put("email", emails);
            postDataParams.put("password", pwd);
            //Call ServerData() method to call webservice and store result in response
            response = service.ServerData(url, postDataParams);
            Log.d("response", response);
            try {
                jsonobject = new JSONObject(response);
                success=jsonobject.getString("success");

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            pDialog.dismiss();
            if (success.equals("True")){
                Toast.makeText(getApplicationContext(), "Register successfully..!", Toast.LENGTH_LONG).show();

            }
            else
                Toast.makeText(getApplicationContext(), "Opps...! It seems something went wrong. Kindly re-enter the relevent data and please proceed again.", Toast.LENGTH_LONG).show();
        }
    }

}
