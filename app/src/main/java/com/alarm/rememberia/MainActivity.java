package com.alarm.rememberia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.alarm.rememberia.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home || id == R.id.nav_crear || id == R.id.nav_plantillas || id == R.id.nav_ajustes) {
                return true;
            }
            return false;
        });
    }
}
