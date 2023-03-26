package com.example.bookaholic;

import static com.example.bookaholic.Tools.showToast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookaholic.details.Detail;
import com.example.bookaholic.SignInActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private Button btnRegister;
    private TextView btnSignIn;
    private TextInputEditText emailRegister, passRegister, confirmRegister;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        emailRegister = findViewById(R.id.editEmailRegister);
        passRegister = findViewById(R.id.editPasswordRegister);
        confirmRegister = findViewById(R.id.editConfirmRegister);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, confirm;
                email = Objects.requireNonNull(emailRegister.getText()).toString();
                password = Objects.requireNonNull(passRegister.getText()).toString();
                confirm = Objects.requireNonNull(confirmRegister.getText().toString());

                if (email.equals("") || password.equals(""))
                    showToast(SignUpActivity.this, R.string.empty_text);
//                valid với firebase ở đây
                else if (!password.equals(confirm))
                    showToast(SignUpActivity.this, R.string.confirm_password);
                else if (!email.equals("") && !password.equals("")) {
                    Intent intent = new Intent(SignUpActivity.this, Detail.class);
                    startActivity(intent);
                }
                else
                    showToast(SignUpActivity.this, R.string.confirm_password);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
