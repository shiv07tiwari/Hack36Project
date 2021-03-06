package com.example.shivansh.krishi_care;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    public static String MY_PREFS_NAME = "Preference List File";
    private static final int PICK_IMAGE = 1;
    private  Boolean valid = true;
    private ImageView upload_image;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Boolean islogedin = prefs.getBoolean("is_logedin",false);
        if(islogedin){
            Intent myIntent = new Intent(SignUpActivity.this, HomeActivity.class);
            SignUpActivity.this.startActivity(myIntent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("log","Welcome");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//
//        RetrofitInterface apiService =
//                APIClient.getClient().create(RetrofitInterface.class);
//        Call<List<OTPMessageResponse>> call = apiService.check();
//
//        call.enqueue(new Callback<List<OTPMessageResponse>>() {
//
//            @Override
//            public void onResponse(Call<List<OTPMessageResponse>> call, Response<List<OTPMessageResponse>> response) {
//                Log.e("log", call.request().url().toString());
//                try {
//                    Log.e("log","Check");
//                    //Toast.makeText(SignUpActivity.this,String.valueOf(response.message()),Toast.LENGTH_LONG).show();
//                   // Log.e("Log", String.valueOf(response.body()));
//                    List<OTPMessageResponse> list = response.body();
//                    for (OTPMessageResponse m: list) {
//                        Log.e("log",m.getName());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<OTPMessageResponse>> call, Throwable t) {
//                Log.e("log", call.request().url().toString());
//                Toast.makeText(SignUpActivity.this,String.valueOf(t),Toast.LENGTH_LONG).show();
//                Log.e("ERROR2", t.toString());
//            }
//
//        });

        upload_image = findViewById(R.id.upload_image);
        final EditText user_name = findViewById(R.id.user_name);
        final EditText user_phone = findViewById(R.id.user_contact);
        final EditText user_email = findViewById(R.id.user_email);
        final EditText user_password = findViewById(R.id.user_password);
        Button signup = findViewById(R.id.button_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("log","Click");
                if(user_name.getText().toString().length()==0 && valid) {
                    //valid=false;
                    Toast.makeText(SignUpActivity.this, "Enter Name", Toast.LENGTH_LONG).show();
                }
                if(user_password.getText().toString().length()==0 && valid) {
                    //valid=false;
                    Toast.makeText(SignUpActivity.this,"Enter Password",Toast.LENGTH_LONG).show();
                }
                if(user_phone.getText().toString().length()!=10 && valid) {
                    //valid=false;
                    Toast.makeText(SignUpActivity.this,"Phone Number must be 10 digit",Toast.LENGTH_LONG).show();
                }
                if(user_email.getText().toString().length()!=0 && valid) {
                    if(!user_email.getText().toString().endsWith(".com")){
                        //valid=false;
                        Toast.makeText(SignUpActivity.this,"Invalid Email Address",Toast.LENGTH_LONG).show();
                    }
                }
                if(valid) {
                    Intent myIntent = new Intent(SignUpActivity.this, HomeActivity.class);
                    SignUpActivity.this.startActivity(myIntent);
                    //Utilities.requestOTP(user_phone);
                    editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("is_logedin",true);
                    editor.putString("user_name",user_name.getText().toString());
                    editor.putString("user_phone",user_phone.getText().toString());
                    editor.putString("user_email",user_email.getText().toString());
                    editor.putString("user_password",user_password.getText().toString());
                    editor.apply();
                    finish();
                }
            }
        });
        upload_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

    }

    public static boolean validateAadharNumber(String aadharNumber){
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if(isValidAadhar){
            isValidAadhar = Utilities.validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            Log.e("log","Editor : "+editor);
            Log.e("log", String.valueOf(selectedImageUri));
            editor.putString("user_image_uri", String.valueOf(selectedImageUri));
            editor.apply();
            upload_image.setImageURI(selectedImageUri);
        }
    }
}
