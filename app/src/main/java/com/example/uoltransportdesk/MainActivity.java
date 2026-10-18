package com.example.uoltransportdesk;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Key constant for passing the name to ProblemActivity
    public static final String EXTRA_NAME = "com.example.uoltransportdesk.EXTRA_NAME";

    private EditText etName;
    private Button btnPage, btnCallOffice, btnBusStop, btnReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        btnPage = findViewById(R.id.btnPage);
        btnCallOffice = findViewById(R.id.btnCallOffice);
        btnBusStop = findViewById(R.id.btnBusStop);
        btnReport = findViewById(R.id.btnReport);

        // 1) Open web page (implicit intent)
        btnPage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.uol.edu.pk"));
            openIntent(this, intent);
        });

        // 2) Open dialer with sample number (no call is placed)
        btnCallOffice.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:04298765432"));
            openIntent(this, intent);
        });

        // 3) Open maps app
        btnBusStop.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=Thokar+Niaz+Baig,+Lahore"));
            openIntent(this, intent);
        });

        // 4) Validate name, then open ProblemActivity (explicit intent)
        btnReport.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                etName.setError("Please enter your name");
                return;
            }
            Intent intent = new Intent(MainActivity.this, ProblemActivity.class);
            intent.putExtra(EXTRA_NAME, name);
            startActivity(intent);
        });
    }

    // Safe helper used by every implicit intent
    public static void openIntent(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No app found for this action", Toast.LENGTH_SHORT).show();
        }
    }
}