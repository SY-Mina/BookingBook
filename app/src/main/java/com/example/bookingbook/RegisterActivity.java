package com.example.bookingbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText userName, email, password, mobile;
    RadioGroup radioGroup;
    Button register;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = userDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);
        radioGroup = findViewById(R.id.radiogp);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String txtUserName = userName.getText().toString();
                Log.d("userName1", txtUserName);
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                String txtMobile = mobile.getText().toString();

                if (TextUtils.isEmpty(txtUserName) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)
                        || TextUtils.isEmpty(txtMobile)) {
                    Toast.makeText(RegisterActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                } else {
                    int genderId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selected_Gender = radioGroup.findViewById(genderId);
                    if (selected_Gender == null) {
                        Toast.makeText(RegisterActivity.this, "Select gender Please", Toast.LENGTH_SHORT).show();
                    } else {
                        String selectGender = selected_Gender.getText().toString();
                        HashMap result = new HashMap<>();
                        result.put("name", txtUserName);
                        result.put("email", txtEmail);
                        result.put("password", txtPassword);
                        result.put("mobile", txtMobile);
                        result.put("gender", selectGender);
                        Log.d("userName2", txtUserName);
                        registerNewAccount(txtUserName, txtEmail, txtPassword, txtMobile, selectGender);
                    }
                }
            }
        });
    }

    private void registerNewAccount(final String username, final String email, final String password,
                                    final String mobile, final String gender) {

        final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
        mDialog.setMessage("??????????????????...");
        mDialog.show();
        User user = new User(username, email, password, mobile, gender);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    mDialog.dismiss();
                    Log.d("userName3", username);
                    userReference.child("users").child(username).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("userName4", username);
                            Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Register Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "??????!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
