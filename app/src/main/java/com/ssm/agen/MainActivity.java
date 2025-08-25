package com.ssm.agen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvPassword;
    private Button btnGenerate, btnCopy, btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable Edge-to-Edge content
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_main);

        tvPassword = findViewById(R.id.tvPassword);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnCopy = findViewById(R.id.btnCopy);
        btnShare = findViewById(R.id.btnShare);

        btnGenerate.setOnClickListener(v -> generatePassword());
        btnCopy.setOnClickListener(v -> copyPassword());
        btnShare.setOnClickListener(v -> sharePassword());
    }

    private void generatePassword() {
        int length = new Random().nextInt(9) + 8; // 8 to 16 characters
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        tvPassword.setText(password.toString());
    }

    private void copyPassword() {
        String password = tvPassword.getText().toString();
        if (!password.isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("password", password);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    private void sharePassword() {
        String password = tvPassword.getText().toString();
        if (!password.isEmpty()) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "My random password: " + password);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
    }
}
