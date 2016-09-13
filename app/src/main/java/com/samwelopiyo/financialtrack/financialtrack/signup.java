package com.samwelopiyo.financialtrack.financialtrack;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 5/6/16.
 */
public class signup extends AppCompatActivity {
    private EditText name;
    private EditText password;
    private EditText email;
    private Button btn_signup;
    private Button link_login;
    private final DataBaseHelper db = new DataBaseHelper(this);
    private String usernameVar;
    private String emailVar;
    private String passwordVar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        name=(EditText) findViewById(R.id.input_name);
        password=(EditText) findViewById(R.id.input_password);
        email=(EditText) findViewById(R.id.input_email);
        btn_signup=(Button) findViewById(R.id.btn_signup);
        link_login=(Button) findViewById(R.id.link_login);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().length() == 0 || email.getText().toString().trim().length() == 0
                        || password.getText().toString().trim().length() == 0){
                    Toast.makeText(signup.this, "One or more of the Important Fields has been left blank.", Toast.LENGTH_SHORT).show();
                }else {
                    db.numberOfRows();
                    if (db.noUsers > 0) {
                        Toast.makeText(signup.this, "There is a user already registered.Login to access the account!", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        password.setText("");
                        email.setText("");
                    } else {
                        usernameVar = name.getText().toString();
                        emailVar = email.getText().toString();
                        passwordVar = password.getText().toString();
                        insertSignUp();
                        name.setText("");
                        email.setText("");
                        password.setText("");
                        Intent login = new Intent(signup.this, login.class);
                        startActivity(login);
                    }
                }


            }
        });
        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(signup.this, Homepage.class);
                startActivity(login);
            }
        });
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        assert fab1 != null;
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSignUp();
            }
        });
    }

    private void insertSignUp(){
        db.open();
        //long id;
        /*id =*/ db.insertTitleSignup(
                usernameVar,
                emailVar,
                passwordVar);
        System.out.println("bool211");
        db.close();
        Toast.makeText(this, "Sign UP Successfull!",
                Toast.LENGTH_LONG).show();
    }
    private void onBackPressedSignUp(){
        super.onBackPressed();
    }


}
