package com.example.getsavego.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.contextaware.ContextAware;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.getsavego.adapters.TransactionsAdapter;
import com.example.getsavego.models.Transaction;
import com.example.getsavego.utils.Constants;
import com.example.getsavego.utils.Helper;
import com.example.getsavego.viewmodel.MainViewModel;
import com.example.getsavego.views.fragments.AddTransactionFragment;
import com.example.getsavego.R;
import com.example.getsavego.databinding.ActivityMainBinding;
import com.example.getsavego.views.fragments.StatsFragment;
import com.example.getsavego.views.fragments.TransactionsFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;


public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
Calendar calendar;
public MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Realm.init(this);

//        SharedPreferences sharedPreferences = getSharedPreferences("UserLoginPrefs", MODE_PRIVATE);
//        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
//        if (!isLoggedIn) {
//            // If not logged in, redirect to LoginActivity
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish(); // Close MainActivity so user can't go back to it
//        }


        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel= new ViewModelProvider(this).get(MainViewModel.class);
        setSupportActionBar(binding.toolBarMain);
        getSupportActionBar().setTitle("GetSaveGo");
        Constants.setCategories();
        calendar=Calendar.getInstance();

        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new TransactionsFragment());
        transaction.commit();
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                if(menuItem.getItemId()==R.id.trans) {
                    transaction.replace(R.id.content,new TransactionsFragment());
                    //getSupportFragmentManager().popBackStack();

                }
                else if(menuItem.getItemId()==R.id.stats) {
                    transaction.replace(R.id.content,new StatsFragment());
                   // transaction.addToBackStack(null);
                }
                transaction.commit();
                return true;
            }
        });
     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void getTransactions(){
        viewModel.getTransactions(calendar);
    }

}