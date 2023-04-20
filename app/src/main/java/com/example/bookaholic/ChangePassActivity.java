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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ChangePassActivity extends AppCompatActivity {
    private Button resetPassword;
    private TextInputEditText newPass, oldPass;
    private String oldPassword, newPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        resetPassword = findViewById(R.id.btn_change_pass);
        newPass = findViewById(R.id.editNewPassChange);
        oldPass = findViewById(R.id.editOldPassChange);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPassword = Objects.requireNonNull(oldPass.getText()).toString();
                newPassword = Objects.requireNonNull(newPass.getText()).toString();

                if (!Tools.checkValidPassword(oldPassword) || !Tools.checkValidPassword(newPassword)) {
                    showToast(ChangePassActivity.this, R.string.invalid_password);
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    final String email = user.getEmail();
                    AuthCredential credential = EmailAuthProvider.getCredential(email,oldPassword);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    showToast(ChangePassActivity.this, R.string.update_pass_success);
                                                    Log.d(TAG, "Password updated");
                                                } else {
                                                    Log.d(TAG, "Error password not updated");
                                                }
                                            }
                                        });
                                    } else {
                                        Log.d(TAG, "Error auth failed");
                                    }
                                }
                            });
                }
            }
        });
    }
}
