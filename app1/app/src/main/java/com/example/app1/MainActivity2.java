package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {

    private Button bt, bt2, btnLogout;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        // Khởi tạo Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Cấu hình Google Sign-In
        configureGoogleSignIn();

        bt = findViewById(R.id.btnReadNow);
        bt2 = findViewById(R.id.btnReadNow2);
        btnLogout = findViewById(R.id.btnLogout);

        bt.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity2.this, MainActivity3.class));
        });

        bt2.setOnClickListener(v -> finish());

        // Xử lý đăng xuất
        btnLogout.setOnClickListener(v -> signOut());
    }

    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signOut() {
        // Đăng xuất Firebase
        mAuth.signOut();

        // Đăng xuất Google
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            // Chuyển về màn hình đăng nhập
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}