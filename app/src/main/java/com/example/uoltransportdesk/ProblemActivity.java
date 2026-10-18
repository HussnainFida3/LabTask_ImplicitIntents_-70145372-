package com.example.uoltransportdesk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProblemActivity extends AppCompatActivity {

    private TextView tvGreeting;
    private EditText etPhone, etEmail;
    private Button btnCallDriver, btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        tvGreeting = findViewById(R.id.tvGreeting);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        btnCallDriver = findViewById(R.id.btnCallDriver);
        btnSendEmail = findViewById(R.id.btnSendEmail);

        // Read the name sent from MainActivity
        String name = getIntent().getStringExtra(MainActivity.EXTRA_NAME);
        if (name == null || name.trim().isEmpty()) {
            name = "Student";
        }
        tvGreeting.setText("Hello, " + name + "! Tell us what went wrong.");

        // Call Driver: 11 digits starting with 03
        btnCallDriver.setOnClickListener(v -> {
            String number = etPhone.getText().toString().trim();
            if (!number.matches("03\\d{9}")) {
                etPhone.setError("Enter 11 digits starting with 03");
                return;
            }
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
            MainActivity.openIntent(this, intent);
        });

        // Send Email: must match Patterns.EMAIL_ADDRESS
        btnSendEmail.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Enter a valid email address");
                return;
            }
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
            MainActivity.openIntent(this, intent);
        });
    }
}