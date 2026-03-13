package com.alarm.rememberia;

import android.content.res.ColorStateList;
import android.os.Bundle;
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

import com.alarm.rememberia.databinding.FragmentPlantillasBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import java.util.HashSet;
import java.util.Set;

public class PlantillasFragment extends Fragment {

    private FragmentPlantillasBinding binding;
    private Set<String> addedIds = new HashSet<>();
    private Set<String> expandedIds = new HashSet<>();

    private static class Template {
        String id, name, category, description, time, frequency;
        int iconResId, iconBg, iconColor;

        Template(String id, String name, String category, String description,
                 String time, String frequency, int iconResId, int iconBg, int iconColor) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.description = description;
            this.time = time;
            this.frequency = frequency;
            this.iconResId = iconResId;
            this.iconBg = iconBg;
            this.iconColor = iconColor;
        }
    }

    private Template[] templates;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlantillasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeTemplates();
        populateTemplatesList();
    }

    private void initializeTemplates() {
        templates = new Template[]{
            new Template("1", "Beber Agua", "SALUD", "Recuerda hidratarte cada 2 horas durante el día",
                "Cada 2h", "DIARIO", R.drawable.ic_droplet, R.drawable.bg_circle_context_water, R.color.context_water),
            new Template("2", "Ejercicio Matutino", "DEPORTE", "30 minutos de actividad física para empezar bien",
                "6:30 am", "L-V", R.drawable.ic_dumbbell, R.drawable.bg_circle_primary_100, R.color.primary),
            new Template("3", "Meditación", "SALUD", "10 minutos de meditación guiada para la calma",
                "7:00 am", "DIARIO", R.drawable.ic_brain, R.drawable.bg_circle_context_study, R.color.context_study),
            new Template("4", "Despertar Temprano", "SALUD", "Levántate con energía positiva cada mañana",
                "6:00 am", "DIARIO", R.drawable.ic_sun, R.drawable.bg_circle_context_sun, R.color.context_sun),
            new Template("5", "Limpiar casa", "HOGAR", "Limpieza general semanal",
                "10:00 am", "SAB", R.drawable.ic_trash, R.drawable.bg_circle_context_trash, R.color.context_trash),
            new Template("6", "Leer 20 Minutos", "ESTUDIO", "Dedicar tiempo a la lectura antes de dormir",
                "9:00 pm", "DIARIO", R.drawable.ic_book_open, R.drawable.bg_circle_context_study, R.color.context_study)
        };
    }

    private void populateTemplatesList() {
        binding.templatesContainer.removeAllViews();

        for (Template template : templates) {
            View templateCardView = createTemplateCard(template);
            binding.templatesContainer.addView(templateCardView);
        }
    }

    private View createTemplateCard(Template template) {
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
        mainLayout.setGravity(android.view.Gravity.TOP);
        card.addView(mainLayout);

        FrameLayout iconContainer = createIconContainer(template);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dpToPx(44), dpToPx(44));
        iconParams.setMargins(0, 0, dpToPx(12), 0);
        mainLayout.addView(iconContainer, iconParams);

        LinearLayout contentLayout = new LinearLayout(requireContext());
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setLayoutParams(new LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        ));

        LinearLayout nameRow = new LinearLayout(requireContext());
        nameRow.setOrientation(LinearLayout.HORIZONTAL);
        nameRow.setGravity(android.view.Gravity.CENTER_VERTICAL);
        nameRow.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView nameView = new TextView(requireContext());
        nameView.setText(template.name);
        nameView.setTextColor(ContextCompat.getColor(requireContext(), R.color.foreground));
        nameView.setTextSize(14);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        nameParams.setMargins(0, 0, dpToPx(8), 0);
        nameRow.addView(nameView, nameParams);

        Chip categoryChip = new Chip(requireContext());
        categoryChip.setText(template.category);
        categoryChip.setChipStrokeWidth(dpToPx(1));
        categoryChip.setChipStrokeColor(ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), R.color.border)
        ));
        categoryChip.setChipBackgroundColor(ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), R.color.card)
        ));
        categoryChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.muted_foreground));
        categoryChip.setTextSize(8);
        categoryChip.setChipMinHeight(dpToPx(20));
        categoryChip.setChipCornerRadius(dpToPx(6));
        categoryChip.setClickable(false);
        nameRow.addView(categoryChip);

        LinearLayout.LayoutParams nameRowParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        nameRowParams.setMargins(0, 0, 0, dpToPx(2));
        contentLayout.addView(nameRow, nameRowParams);

        final TextView descView = new TextView(requireContext());
        descView.setText(template.description);
        descView.setTextColor(ContextCompat.getColor(requireContext(), R.color.muted_foreground));
        descView.setTextSize(11);
        if (!expandedIds.contains(template.id)) {
            descView.setMaxLines(1);
            descView.setEllipsize(android.text.TextUtils.TruncateAt.END);
        }
        LinearLayout.LayoutParams descParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        descParams.setMargins(0, 0, 0, dpToPx(4));
        contentLayout.addView(descView, descParams);

        LinearLayout timeRow = createTimeFrequencyRow(template);
        contentLayout.addView(timeRow);

        mainLayout.addView(contentLayout);

        MaterialButton addButton = createAddButton(template.id);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.setMargins(dpToPx(8), 0, 0, 0);
        mainLayout.addView(addButton, buttonParams);

        card.setOnClickListener(v -> {
            if (expandedIds.contains(template.id)) {
                expandedIds.remove(template.id);
                descView.setMaxLines(1);
                descView.setEllipsize(android.text.TextUtils.TruncateAt.END);
            } else {
                expandedIds.add(template.id);
                descView.setMaxLines(Integer.MAX_VALUE);
                descView.setEllipsize(null);
            }
        });

        return card;
    }

    private FrameLayout createIconContainer(Template template) {
        FrameLayout iconFrame = new FrameLayout(requireContext());
        iconFrame.setLayoutParams(new FrameLayout.LayoutParams(dpToPx(44), dpToPx(44)));
        iconFrame.setBackground(ContextCompat.getDrawable(requireContext(), template.iconBg));

        ImageView icon = new ImageView(requireContext());
        icon.setImageDrawable(ContextCompat.getDrawable(requireContext(), template.iconResId));
        icon.setImageTintList(ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), template.iconColor)
        ));
        int iconSize = dpToPx(20);
        FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(iconSize, iconSize, android.view.Gravity.CENTER);
        icon.setLayoutParams(iconParams);
        iconFrame.addView(icon);

        return iconFrame;
    }

    private LinearLayout createTimeFrequencyRow(Template template) {
        LinearLayout row = new LinearLayout(requireContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        row.setGravity(android.view.Gravity.CENTER_VERTICAL);

        ImageView clockIcon = new ImageView(requireContext());
        clockIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_clock));
        clockIcon.setImageTintList(ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), R.color.muted_foreground)
        ));
        LinearLayout.LayoutParams clockParams = new LinearLayout.LayoutParams(dpToPx(12), dpToPx(12));
        clockParams.setMargins(0, 0, dpToPx(3), 0);
        row.addView(clockIcon, clockParams);

        TextView timeView = new TextView(requireContext());
        timeView.setText(template.time);
        timeView.setTextSize(11);
        timeView.setTextColor(ContextCompat.getColor(requireContext(), R.color.muted_foreground));
        row.addView(timeView);

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

        TextView frequencyView = new TextView(requireContext());
        frequencyView.setText(template.frequency);
        frequencyView.setTextSize(11);
        frequencyView.setTextColor(ContextCompat.getColor(requireContext(), R.color.muted_foreground));
        row.addView(frequencyView);

        return row;
    }

    private MaterialButton createAddButton(String templateId) {
        boolean isAdded = addedIds.contains(templateId);

        MaterialButton button;
        if (isAdded) {
            button = new MaterialButton(requireContext(), null, com.google.android.material.R.attr.materialButtonOutlinedStyle);
            button.setText("Listo");
            button.setStrokeColor(ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.secondary_300)
            ));
            button.setStrokeWidth(dpToPx(1));
            button.setBackgroundTintList(ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.card)
            ));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_700));
        } else {
            button = new MaterialButton(requireContext());
            button.setText("Añadir");
            button.setBackgroundTintList(ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.primary)
            ));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_foreground));
            button.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_plus));
            button.setIconSize(dpToPx(14));
            button.setIconTint(ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.primary_foreground)
            ));
        }

        button.setTextSize(11);
        button.setCornerRadius(dpToPx(12));
        button.setInsetTop(0);
        button.setInsetBottom(0);
        button.setMinHeight(dpToPx(32));
        button.setMinimumHeight(dpToPx(32));
        button.setPaddingRelative(dpToPx(12), 0, dpToPx(12), 0);
        button.setAllCaps(false);

        button.setOnClickListener(v -> {
            if (addedIds.contains(templateId)) {
                addedIds.remove(templateId);
            } else {
                addedIds.add(templateId);
            }
            populateTemplatesList();
        });

        return button;
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
