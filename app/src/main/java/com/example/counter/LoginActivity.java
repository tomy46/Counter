package com.example.counter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_actity);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.setLanguageCode("es");
        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);

        if(firebaseAuth.getCurrentUser() != null)

          startActivity(homeIntent);

        Button button = findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                login(v);
            }
        });

        Button registerButton = findViewById(R.id.go_to_register);

        registerButton.setOnClickListener(v -> {
         startActivity(registerIntent);
        });

    }

    public void login(View view){
        EditText email = findViewById(R.id.email_login);
        EditText password = findViewById(R.id.password_login);

       String _email = email.getText().toString();
       String _password = password.getText().toString();
       if(_email.equals(""))
           showMessage(getString(R.string.email_error));
       if(_password.equals(""))
           showMessage(getString(R.string.password_error));

        firebaseAuth.signInWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                          //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                        showMessage(task.getException().getMessage());
                         //   updateUI(null);
                        }
                    }
                });



    }


    void showMessage(String message){
        Toast.makeText(LoginActivity.this, message,
                Toast.LENGTH_SHORT).show();
    }


}