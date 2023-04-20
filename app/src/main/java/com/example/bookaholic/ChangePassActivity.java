package com.example.bookaholic;

import static android.content.ContentValues.TAG;
import static com.example.bookaholic.Tools.showToast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ChangePassActivity extends AppCompatActivity {
    private Button resetPassword;
    private TextInputEditText pass, confirmPass;
    private String email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        pass = findViewById(R.id.btnForgot);

    }
}
