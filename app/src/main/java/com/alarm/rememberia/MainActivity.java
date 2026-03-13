package com.alarm.rememberia;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.alarm.rememberia.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String KEY_CURRENT_TAB = "current_tab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int selectedId = (savedInstanceState != null)
                ? savedInstanceState.getInt(KEY_CURRENT_TAB, R.id.nav_home)
                : R.id.nav_home;

        binding.bottomNav.setSelectedItemId(selectedId);
        showFragmentForNavId(selectedId);

        binding.bottomNav.setOnItemSelectedListener(item -> {
            showFragmentForNavId(item.getItemId());
            return true;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_TAB, binding.bottomNav.getSelectedItemId());
    }

    private void showFragmentForNavId(int navId) {
        Fragment fragment;
        if (navId == R.id.nav_ajustes) {
            fragment = new SettingsFragment();
        } else if (navId == R.id.nav_crear) {
            fragment = new CreateModeFragment();
        } else if (navId == R.id.nav_plantillas) {
            fragment = new PlantillasFragment();
        } else {
            fragment = new HomeFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    public void navigateToHome() {
        androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.beginTransaction()
                .replace(R.id.content_frame, new HomeFragment())
                .commit();
        binding.bottomNav.setSelectedItemId(R.id.nav_home);
    }

    public void navigateToRinging() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new RingingFragment())
                .addToBackStack(null)
                .commit();
    }

    public void navigateToExecute() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ExecuteFragment())
                .addToBackStack(null)
                .commit();
    }
}
