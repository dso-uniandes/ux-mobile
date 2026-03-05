package com.alarm.rememberia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.alarm.rememberia.databinding.FragmentCreateAlarmBinding;
import com.google.android.material.chip.Chip;

public class CreateAlarmFragment extends Fragment {

    private FragmentCreateAlarmBinding binding;
    private String category = "salud";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateAlarmBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editName.setText("Purificar agua");
        binding.editTime.setText("7:30 pm");
        binding.toggleRepeat.check(R.id.btn_repeat_daily);
        binding.switchReminder.setChecked(true);

        setupCategoryChips();
        binding.buttonCreateAlarm.setOnClickListener(v -> onCreateAlarm());
        updateCategorySelection();
    }

    private void setupCategoryChips() {
        binding.chipSalud.setOnClickListener(v -> setCategory("salud"));
        binding.chipHogar.setOnClickListener(v -> setCategory("hogar"));
        binding.chipEstudio.setOnClickListener(v -> setCategory("estudio"));
    }

    private void setCategory(String cat) {
        if (category.equals(cat)) return;
        category = cat;
        updateCategorySelection();
    }

    private void updateCategorySelection() {
        int primary = ContextCompat.getColor(requireContext(), R.color.primary);
        int primaryForeground = ContextCompat.getColor(requireContext(), R.color.primary_foreground);
        int card = ContextCompat.getColor(requireContext(), R.color.card);
        int foreground = ContextCompat.getColor(requireContext(), R.color.foreground);
        int contextHome = ContextCompat.getColor(requireContext(), R.color.context_home);
        int contextStudy = ContextCompat.getColor(requireContext(), R.color.context_study);
        int strokeWidthPx = (int) (getResources().getDisplayMetrics().density);
        setChipMuiStyle(binding.chipSalud, "salud".equals(category), primary, primaryForeground, card, foreground, primary, strokeWidthPx);
        setChipMuiStyle(binding.chipHogar, "hogar".equals(category), primary, primaryForeground, card, foreground, contextHome, strokeWidthPx);
        setChipMuiStyle(binding.chipEstudio, "estudio".equals(category), primary, primaryForeground, card, foreground, contextStudy, strokeWidthPx);
    }

    private void setChipMuiStyle(Chip chip, boolean selected, int primary, int primaryFg, int cardBg, int fgColor, int iconColorUnselected, int strokeWidthPx) {
        if (selected) {
            chip.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(primary));
            chip.setTextColor(primaryFg);
            chip.setChipIconTint(android.content.res.ColorStateList.valueOf(primaryFg));
            chip.setChipStrokeWidth(0f);
        } else {
            chip.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(cardBg));
            chip.setTextColor(fgColor);
            chip.setChipIconTint(android.content.res.ColorStateList.valueOf(iconColorUnselected));
            chip.setChipStrokeWidth(strokeWidthPx);
            chip.setChipStrokeColor(android.content.res.ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.border)));
        }
    }

    private void onCreateAlarm() {
        ((MainActivity) requireActivity()).navigateToHome();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
