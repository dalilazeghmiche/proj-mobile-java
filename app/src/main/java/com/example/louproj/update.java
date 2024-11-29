package com.example.louproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class update extends AppCompatActivity {

    private EditText oldPasswordEditText, newPasswordEditText,usernameedit;
    private String username,password;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        username = getIntent().getStringExtra("USERNAME");
        password = getIntent().getStringExtra("PASSWORD");

        dbHelper = new DBHelper(this);
        usernameedit = findViewById(R.id.username);
        oldPasswordEditText = findViewById(R.id.editText_old_password);
        newPasswordEditText = findViewById(R.id.editText_new_password);
        oldPasswordEditText.setText(password);
        usernameedit.setText(username);




    }
    public void gg (View c) {
        String oldPassword = oldPasswordEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();

        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(update.this, "Please enter old and new password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the old password matches the password stored in the database
        if (dbHelper.isValidPassword(username, oldPassword)) {
            // Update the password in the database for the specified username
            dbHelper.updatePassword(username, newPassword);
            Toast.makeText(update.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(update.this, "Incorrect old password", Toast.LENGTH_SHORT).show();
        }
    }



    public void back (View v){
        Intent ab = new Intent(this, MainActivity.class);
        startActivity(ab);

    }
}