package com.alarm.rememberia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.alarm.rememberia.databinding.FragmentLocationBinding;
import com.google.android.material.chip.Chip;

public class LocationFragment extends Fragment {

    private FragmentLocationBinding binding;
    private boolean locationEnabled = true;
    private String selectedPlace = "casa";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLocationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.chipLocationEnabled.setOnClickListener(v -> {
            locationEnabled = !locationEnabled;
            updateEnableChip();
        });
        updateEnableChip();

        binding.chipPlaceCasa.setOnClickListener(v -> setPlace("casa"));
        binding.chipPlaceTrabajo.setOnClickListener(v -> setPlace("trabajo"));
        updatePlaceSelection();

        binding.buttonLocationSave.setOnClickListener(v -> ((MainActivity) requireActivity()).navigateToHome());
    }

    private void updateEnableChip() {
        Chip chip = binding.chipLocationEnabled;
        if (locationEnabled) {
            chip.setText(R.string.location_enable_on);
            chip.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.primary)));
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_foreground));
            chip.setChipStrokeWidth(0f);
        } else {
            chip.setText(R.string.location_enable_off);
            chip.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.card)));
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.muted_foreground));
            chip.setChipStrokeWidth((int) (getResources().getDisplayMetrics().density));
            chip.setChipStrokeColor(android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.border)));
        }
    }

    private void setPlace(String place) {
        if (selectedPlace.equals(place)) return;
        selectedPlace = place;
        updatePlaceSelection();
    }

    private void updatePlaceSelection() {
        int primary = ContextCompat.getColor(requireContext(), R.color.primary);
        int primaryForeground = ContextCompat.getColor(requireContext(), R.color.primary_foreground);
        int card = ContextCompat.getColor(requireContext(), R.color.card);
        int foreground = ContextCompat.getColor(requireContext(), R.color.foreground);
        int strokePx = (int) (getResources().getDisplayMetrics().density);

        setChipPlaceStyle(binding.chipPlaceCasa, "casa".equals(selectedPlace), primary, primaryForeground, card, foreground, strokePx);
        setChipPlaceStyle(binding.chipPlaceTrabajo, "trabajo".equals(selectedPlace), primary, primaryForeground, card, foreground, strokePx);
    }

    private void setChipPlaceStyle(Chip chip, boolean selected, int primary, int primaryFg, int cardBg, int fgColor, int strokeWidthPx) {
        if (selected) {
            chip.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(primary));
            chip.setTextColor(primaryFg);
            chip.setChipIconTint(android.content.res.ColorStateList.valueOf(primaryFg));
            chip.setChipStrokeWidth(0f);
        } else {
            chip.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(cardBg));
            chip.setTextColor(fgColor);
            chip.setChipIconTint(android.content.res.ColorStateList.valueOf(primary));
            chip.setChipStrokeWidth(strokeWidthPx);
            chip.setChipStrokeColor(android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.border)));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
