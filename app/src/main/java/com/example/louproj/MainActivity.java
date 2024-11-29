package com.example.louproj;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username;
    Button login, register;
    EditText password;
    public DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        password = findViewById(R.id.editTextTextPassword);
    }

    public void regist(View v) {
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        // Check if username already exists in the database
        if (dbHelper.isUserExists(user)) {
            // User already exists, display toast
            Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
        } else {
            // Add new user to the database
            dbHelper.addNewUser(user, pass);
            // Show success message
            Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Login button click listener
    public void login(View a) {
        String useer = username.getText().toString().trim();
        String paass = password.getText().toString().trim();

        // Check if user exists and password is correct
        if (dbHelper.isValidUser(useer, paass)) {
            // User exists and password is correct, show success message
            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            // User does not exist or password is incorrect, show error message
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    public void golist(View v) {
        Intent aa = new Intent(this, ListViewActivity.class);
        startActivity(aa);
    }

    public void delete (View v) {
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the user exists in the database
        if (dbHelper.isValidUser(user, pass)) {
            // Delete the user from the database
            dbHelper.deleteUser(user);
            Toast.makeText(MainActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
    public void update (View v){
        Intent ab = new Intent(this, update.class);
        String userame = username.getText().toString().trim();
        String passord = password.getText().toString().trim();

// Add username and password as extras to the Intent
        ab.putExtra("USERNAME", userame);
        ab.putExtra("PASSWORD", passord);

        startActivity(ab);

    }
    public void gobraodcast (View zz){
        Intent broad = new Intent(this, broadcast.class);
        startActivity(broad);

    }


}