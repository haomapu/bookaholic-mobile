package com.example.bookaholic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.bookaholic.Tools.showToast;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookaholic.details.Detail;
import com.example.bookaholic.SignUpActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity{
    private Button btnLogin;
    private TextView btnSignUp;
    private TextInputEditText emailLogin, passLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

//        btnLogin = findViewById(R.id.btnLogin);
//        emailLogin = findViewById(R.id.editEmailLogin);
//        passLogin = findViewById(R.id.editPasswordLogin);
//        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = Objects.requireNonNull(emailLogin.getText()).toString();
                password = Objects.requireNonNull(passLogin.getText()).toString();

                boolean validInputs = !email.equals("") && !password.equals("");

                if (!validInputs)
                    showToast(SignInActivity.this, R.string.empty_text);
//                valid với firebase ở đây
                else if (email.equals("giahai") && password.equals("123")) {
                    Intent intent = new Intent(SignInActivity.this, Detail.class);
                    startActivity(intent);
                }
                else
                    showToast(SignInActivity.this, R.string.fail_login);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
