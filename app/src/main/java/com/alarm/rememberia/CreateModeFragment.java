package com.alarm.rememberia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.alarm.rememberia.databinding.FragmentCreateModeBinding;

public class CreateModeFragment extends Fragment {

    private FragmentCreateModeBinding binding;
    private String selectedMode = "quick";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateModeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCardClicks();
        binding.buttonCreateModeContinue.setOnClickListener(v -> onContinue());
        updateSelection();
    }

    private void setupCardClicks() {
        binding.cardQuick.setOnClickListener(v -> setSelectedMode("quick"));
        binding.cardSeries.setOnClickListener(v -> setSelectedMode("series"));
        binding.cardVoice.setOnClickListener(v -> setSelectedMode("voice"));
        binding.cardLocation.setOnClickListener(v -> setSelectedMode("location"));
    }

    private void setSelectedMode(String mode) {
        if (selectedMode.equals(mode)) return;
        selectedMode = mode;
        updateSelection();
    }

    private void updateSelection() {
        int cardBgUnselected = ContextCompat.getColor(requireContext(), R.color.card);
        int borderColor = ContextCompat.getColor(requireContext(), R.color.border);
        int secondary100 = ContextCompat.getColor(requireContext(), R.color.secondary_100);
        int secondary700 = ContextCompat.getColor(requireContext(), R.color.secondary_700);
        int primary100 = ContextCompat.getColor(requireContext(), R.color.primary_100);
        int primary300 = ContextCompat.getColor(requireContext(), R.color.primary_300);
        int primary = ContextCompat.getColor(requireContext(), R.color.primary);
        int foreground = ContextCompat.getColor(requireContext(), R.color.foreground);
        int mutedForeground = ContextCompat.getColor(requireContext(), R.color.muted_foreground);
        int contextMedicine = ContextCompat.getColor(requireContext(), R.color.context_medicine);
        int contextMedicineBg = ContextCompat.getColor(requireContext(), R.color.context_medicine_bg);

        boolean quickSel = "quick".equals(selectedMode);
        binding.cardQuick.setCardBackgroundColor(quickSel ? secondary100 : cardBgUnselected);
        binding.cardQuick.setStrokeWidth(quickSel ? (int) (1.5f * getResources().getDisplayMetrics().density) : (int) (1f * getResources().getDisplayMetrics().density));
        binding.cardQuick.setStrokeColor(quickSel ? secondary700 : borderColor);
        binding.iconContainerQuick.setBackgroundResource(quickSel ? R.drawable.bg_circle_secondary_100_selected : R.drawable.bg_circle_secondary_100_unselected);
        binding.titleQuick.setTextColor(quickSel ? secondary700 : foreground);
        binding.descQuick.setTextColor(quickSel ? secondary700 : mutedForeground);

        boolean seriesSel = "series".equals(selectedMode);
        binding.cardSeries.setCardBackgroundColor(seriesSel ? primary100 : cardBgUnselected);
        binding.cardSeries.setStrokeWidth(seriesSel ? (int) (1.5f * getResources().getDisplayMetrics().density) : (int) (1f * getResources().getDisplayMetrics().density));
        binding.cardSeries.setStrokeColor(seriesSel ? primary300 : borderColor);
        binding.iconContainerSeries.setBackgroundResource(seriesSel ? R.drawable.bg_circle_primary_100_selected : R.drawable.bg_circle_primary_100_unselected);
        binding.titleSeries.setTextColor(seriesSel ? primary : foreground);
        binding.descSeries.setTextColor(seriesSel ? primary : mutedForeground);

        boolean voiceSel = "voice".equals(selectedMode);
        binding.cardVoice.setCardBackgroundColor(voiceSel ? primary300 : cardBgUnselected);
        binding.cardVoice.setStrokeWidth(voiceSel ? (int) (1.5f * getResources().getDisplayMetrics().density) : (int) (1f * getResources().getDisplayMetrics().density));
        binding.cardVoice.setStrokeColor(voiceSel ? primary : borderColor);
        binding.iconContainerVoice.setBackgroundResource(voiceSel ? R.drawable.bg_circle_primary_300_selected : R.drawable.bg_circle_primary_300_unselected);
        binding.titleVoice.setTextColor(voiceSel ? primary : foreground);
        binding.descVoice.setTextColor(voiceSel ? primary : mutedForeground);

        boolean locationSel = "location".equals(selectedMode);
        binding.cardLocation.setCardBackgroundColor(locationSel ? contextMedicineBg : cardBgUnselected);
        binding.cardLocation.setStrokeWidth(locationSel ? (int) (1.5f * getResources().getDisplayMetrics().density) : (int) (1f * getResources().getDisplayMetrics().density));
        binding.cardLocation.setStrokeColor(locationSel ? contextMedicine : borderColor);
        binding.iconContainerLocation.setBackgroundResource(locationSel ? R.drawable.bg_circle_context_medicine_selected : R.drawable.bg_circle_context_medicine_unselected);
        binding.titleLocation.setTextColor(locationSel ? contextMedicine : foreground);
        binding.descLocation.setTextColor(locationSel ? contextMedicine : mutedForeground);
    }

    private void onContinue() {
        if ("quick".equals(selectedMode)) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new CreateAlarmFragment())
                    .addToBackStack("create_mode")
                    .commit();
        } else {
            android.widget.Toast.makeText(requireContext(), "/create/" + selectedMode, android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
