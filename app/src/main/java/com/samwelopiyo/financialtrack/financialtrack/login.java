package com.samwelopiyo.financialtrack.financialtrack;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 5/6/16.
 */
public class login extends AppCompatActivity {
    private DataBaseHelper db;
    private EditText username;
    private EditText password;
    private Button login;
    private Button signup;
    private CheckBox remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        db = new DataBaseHelper(this);
        username=(EditText) findViewById(R.id.usernameLogin);
        password=(EditText) findViewById(R.id.passwordLogin);
        login=(Button) findViewById(R.id.btn_login);
        signup=(Button) findViewById(R.id.link_signup);
        remember=(CheckBox) findViewById(R.id.remember);
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (remember.isChecked()) {
                    db.numberOfRows();
                    if (db.noUsers > 0) {
                        db.open();
                        Cursor c = db.getAllUser();
                        username.setText(c.getString(c.getColumnIndex(c.getColumnName(1))));
                        password.setText(c.getString(c.getColumnIndex(c.getColumnName(3))));
                    }
                }
            }
        });
        /*if (remember.isChecked()){
            db.numberOfRows();
            if (db.noUsers > 0) {
                db.open();
                Cursor c = db.getAllUser();
                username.setText(c.getString(c.getColumnIndex(c.getColumnName(1))));
                password.setText(c.getString(c.getColumnIndex(c.getColumnName(3))));
            }
        }*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0){
                    Toast.makeText(login.this, "One or more of the Important Fields has been left blank.", Toast.LENGTH_SHORT).show();
                }else {
                    db.numberOfRows();
                    if (db.noUsers > 0) {
                        db.open();
                        Cursor c=db.getAllUser();
                        if (username.getText().toString().equals(c.getString(c.getColumnIndex(c.getColumnName(1)))) && password.getText().toString().equals(c.getString(c.getColumnIndex(c.getColumnName(3))))){
                            //if (username.getText().toString().equals("Admin") && password.getText().toString().equals("admin")){
                            Intent homepage=new Intent(login.this, Homepage.class);
                            startActivity(homepage);
                            username.setText("");
                            password.setText("");
                            db.close();
                        } else {
                            Toast.makeText(login.this, "Wrong credentials entered.", Toast.LENGTH_SHORT).show();
                            username.setText("");
                            password.setText("");
                        }
                        //String user = username.getText().toString();
                        //String pass = password.getText().toString();
                    }else{
                        Toast.makeText(login.this, "There is no user registered!", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        Intent signup=new Intent(login.this, signup.class);
                        startActivity(signup);
                    }

                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(login.this, com.samwelopiyo.financialtrack.financialtrack.signup.class);
                startActivity(signup);
            }
        });


    }

}
