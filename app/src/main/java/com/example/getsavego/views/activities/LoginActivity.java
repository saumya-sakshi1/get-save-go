package com.example.getsavego.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.getsavego.R;
import com.example.getsavego.databinding.ActivityLoginBinding;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName= binding.userName.getText().toString();
                String password= binding.passWord.getText().toString();
                 if(userName.isEmpty()|| password.isEmpty()) {
                     Toast.makeText(LoginActivity.this, "Username and password are mandatory fields.", Toast.LENGTH_SHORT).show();
                 }
                 else{
               // loginUser(userName,password);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();}
            }
        });


    }
//    public void loginUser(String email, String password) {
//        App app = new App(new AppConfiguration.Builder(app_id).build());
//        Credentials credentials = Credentials.emailPassword(email, password);
//
//        app.loginAsync(credentials, new App.Callback<User>() {
//            @Override
//            public void onResult(App.Result<User> result) {
//                if (result.isSuccess()) {
//                    User user = result.get();
//                    setupSyncConfiguration(user);
//                } else {
//                    Log.e("EXAMPLE", "Failed to log in: " + result.getError().toString());
//                }
//            }
//        });
//    }
//
//
//    private void setupSyncConfiguration(User user) {
//        SyncConfiguration config = new SyncConfiguration.Builder(user, "partition")
//                .build();
//        Realm.setDefaultConfiguration(config);
//        SharedPreferences sharedPreferences = getSharedPreferences("UserLoginPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("isLoggedIn", true); // Save a flag indicating the user is logged in
//        editor.apply();
//    }
}