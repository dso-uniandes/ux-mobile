package com.alarm.rememberia;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.alarm.rememberia.databinding.FragmentHomeBinding;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.HashSet;
import java.util.Set;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Set<String> enabledAlarms = new HashSet<>();
    private String searchQuery = "";

    private static class Alarm {
        String id, name, time, frequency, category, categoryKey;
        int iconResId, iconBgResId, iconColor, categoryIconResId, categoryIconColor;

        Alarm(String id, String name, String time, String frequency, String category,
              int iconResId, int iconBgResId, int iconColor,
              int categoryIconResId, int categoryIconColor) {
            this.id = id;
            this.name = name;
            this.time = time;
            this.frequency = frequency;
            this.category = category;
            this.iconResId = iconResId;
            this.iconBgResId = iconBgResId;
            this.iconColor = iconColor;
            this.categoryIconResId = categoryIconResId;
            this.categoryIconColor = categoryIconColor;
        }
    }

    private Alarm[] alarms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAlarms();
        setupSearch();
        populateAlarmsList();
    }

    private void setupSearch() {
        binding.searchInputLayout.setVisibility(View.GONE);

        binding.btnSearch.setOnClickListener(v -> {
            if (binding.searchInputLayout.getVisibility() == View.GONE) {
                binding.searchInputLayout.setVisibility(View.VISIBLE);
                binding.searchInput.requestFocus();
            } else {
                binding.searchInputLayout.setVisibility(View.GONE);
                binding.searchInput.setText("");
                searchQuery = "";
                populateAlarmsList();
            }
        });

        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = s.toString().toLowerCase().trim();
                populateAlarmsList();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void initializeAlarms() {
        alarms = new Alarm[]{
            new Alarm("1", "Purificar agua", "7:30 pm", "Diario", "Salud",
                R.drawable.ic_droplet, R.drawable.bg_circle_context_water, R.color.context_water,
                R.drawable.ic_heart, R.color.primary),
            new Alarm("2", "Tomar medicamento", "9:00 pm", "L-V", "Salud",
                R.drawable.ic_pill, R.drawable.bg_circle_context_medicine, R.color.context_medicine,
                R.drawable.ic_heart, R.color.primary),
            new Alarm("3", "Regar plantas", "6:00 am", "L-V", "Hogar",
                R.drawable.ic_leaf, R.drawable.bg_circle_context_plant, R.color.context_plant,
                R.drawable.ic_home, R.color.context_home),
            new Alarm("4", "Leer 20 min", "8:00 pm", "Mar/Jue", "Estudio",
                R.drawable.ic_book_open, R.drawable.bg_circle_context_study, R.color.context_study,
                R.drawable.ic_book_open, R.color.context_study),
            new Alarm("5", "Sacar basura", "7:00 pm", "Sab", "Hogar",
                R.drawable.ic_trash, R.drawable.bg_circle_context_trash, R.color.context_trash,
                R.drawable.ic_home, R.color.context_home)
        };
        enabledAlarms.add("1");
        enabledAlarms.add("2");
        enabledAlarms.add("4");
    }

    private void populateAlarmsList() {
        binding.alarmsContainer.removeAllViews();
        binding.inactiveContainer.removeAllViews();

        for (Alarm alarm : alarms) {
            if (!searchQuery.isEmpty() &&
                !alarm.name.toLowerCase().contains(searchQuery) &&
                !alarm.category.toLowerCase().contains(searchQuery)) {
                continue;
            }
            View alarmCardView = createAlarmCard(alarm);
            if (enabledAlarms.contains(alarm.id)) {
                binding.alarmsContainer.addView(alarmCardView);
            } else {
                binding.inactiveContainer.addView(alarmCardView);
            }
        }
    }

    private View createAlarmCard(Alarm alarm) {
        boolean isEnabled = enabledAlarms.contains(alarm.id);

        MaterialCardView card = new MaterialCardView(requireContext());
        card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card));
        card.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.border));
        card.setStrokeWidth(1);
        card.setRadius(dpToPx(12));
        card.setCardElevation(0);

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, dpToPx(12));
        card.setLayoutParams(cardParams);

        LinearLayout mainLayout = new LinearLayout(requireContext());
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.setPadding(dpToPx(12), dpToPx(12), dpToPx(16), dpToPx(12));
        mainLayout.setGravity(android.view.Gravity.CENTER_VERTICAL);
        mainLayout.setAlpha(isEnabled ? 1f : 0.6f);
        card.addView(mainLayout);

        FrameLayout iconContainer = createIconContainer(alarm);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dpToPx(44), dpToPx(44));
        iconParams.setMargins(0, 0, dpToPx(12), 0);
        mainLayout.addView(iconContainer, iconParams);

        LinearLayout contentLayout = new LinearLayout(requireContext());
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setLayoutParams(new LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        ));

        TextView nameView = createTextView(alarm.name, R.style.TextAppearance_App_Body);
        nameView.setTextColor(ContextCompat.getColor(requireContext(), R.color.foreground));
        nameView.setTextSize(14);
        nameView.setTypeface(nameView.getTypeface(), android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        nameParams.setMargins(0, 0, 0, dpToPx(2));
        contentLayout.addView(nameView, nameParams);

        LinearLayout detailsRow = new LinearLayout(requireContext());
        detailsRow.setOrientation(LinearLayout.HORIZONTAL);
        detailsRow.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        detailsRow.setGravity(android.view.Gravity.CENTER_VERTICAL);

        addDetailItem(detailsRow, R.drawable.ic_clock, R.color.muted_foreground, alarm.time);
        addSeparator(detailsRow);
        addDetailItem(detailsRow, R.drawable.ic_refresh_cw, R.color.muted_foreground, alarm.frequency);
        addSeparator(detailsRow);
        addDetailItem(detailsRow, alarm.categoryIconResId, alarm.categoryIconColor, alarm.category);

        contentLayout.addView(detailsRow);
        mainLayout.addView(contentLayout);

        MaterialSwitch toggleSwitch = new MaterialSwitch(requireContext());
        toggleSwitch.setThumbTintList(ContextCompat.getColorStateList(requireContext(), R.color.switch_thumb_tint));
        toggleSwitch.setTrackTintList(ContextCompat.getColorStateList(requireContext(), R.color.switch_track_tint));
        toggleSwitch.setChecked(isEnabled);
        LinearLayout.LayoutParams switchParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        mainLayout.addView(toggleSwitch, switchParams);

        toggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                enabledAlarms.add(alarm.id);
            } else {
                enabledAlarms.remove(alarm.id);
            }
            populateAlarmsList();
        });

        card.setOnClickListener(v -> {
            if (enabledAlarms.contains(alarm.id)) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new RingingFragment())
                    .addToBackStack(null)
                    .commit();
            }
        });

        return card;
    }

    private void addDetailItem(LinearLayout row, int iconResId, int iconColor, String text) {
        ImageView icon = new ImageView(requireContext());
        icon.setImageDrawable(ContextCompat.getDrawable(requireContext(), iconResId));
        icon.setImageTintList(ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), iconColor)
        ));
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dpToPx(12), dpToPx(12));
        iconParams.setMargins(0, 0, dpToPx(3), 0);
        row.addView(icon, iconParams);

        TextView textView = new TextView(requireContext());
        textView.setText(text);
        textView.setTextSize(11);
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.muted_foreground));
        row.addView(textView);
    }

    private void addSeparator(LinearLayout row) {
        TextView sep = new TextView(requireContext());
        sep.setText("|");
        sep.setTextSize(11);
        sep.setTextColor(ContextCompat.getColor(requireContext(), R.color.muted));
        LinearLayout.LayoutParams sepParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        sepParams.setMargins(dpToPx(4), 0, dpToPx(4), 0);
        row.addView(sep, sepParams);
    }

    private FrameLayout createIconContainer(Alarm alarm) {
        FrameLayout iconFrame = new FrameLayout(requireContext());
        iconFrame.setLayoutParams(new FrameLayout.LayoutParams(dpToPx(44), dpToPx(44)));
        iconFrame.setBackground(ContextCompat.getDrawable(requireContext(), alarm.iconBgResId));

        ImageView icon = new ImageView(requireContext());
        icon.setImageDrawable(ContextCompat.getDrawable(requireContext(), alarm.iconResId));
        icon.setImageTintList(ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), alarm.iconColor)
        ));
        int iconSize = dpToPx(20);
        FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(iconSize, iconSize, android.view.Gravity.CENTER);
        icon.setLayoutParams(iconParams);
        iconFrame.addView(icon);

        return iconFrame;
    }

    private TextView createTextView(String text, int styleResId) {
        TextView view = new TextView(requireContext());
        view.setText(text);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            view.setTextAppearance(styleResId);
        }
        return view;
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
