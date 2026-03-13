package com.alarm.rememberia;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.alarm.rememberia.databinding.FragmentRingingBinding;

public class RingingFragment extends Fragment {

    private FragmentRingingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRingingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBellPattern();
        animateCentralBell();

        binding.buttonDone.setOnClickListener(v -> navigateToExecute());
        binding.buttonSnooze.setOnClickListener(v -> navigateToExecute());
        binding.buttonReschedule.setOnClickListener(v -> navigateToExecute());
    }

    private void animateCentralBell() {
        View bellIcon = binding.centralBellIcon;
        bellIcon.setPivotX(dpToPx(13));
        bellIcon.setPivotY(dpToPx(2));

        ObjectAnimator swing = ObjectAnimator.ofFloat(bellIcon, "rotation", -15f, 15f);
        swing.setDuration(300);
        swing.setRepeatCount(ValueAnimator.INFINITE);
        swing.setRepeatMode(ValueAnimator.REVERSE);
        swing.setInterpolator(new LinearInterpolator());
        swing.start();
    }

    private void setupBellPattern() {
        FrameLayout container = binding.bellPatternContainer;
        container.post(() -> {
            if (binding == null) return;

            int cx = container.getWidth() / 2;
            int cy = dpToPx(52);

            int bellColor = ContextCompat.getColor(requireContext(), R.color.primary_300);

            int[][] rings = {
                {6,  44,  14, 55},
                {10, 68,  12, 45},
                {14, 92,  11, 38},
                {18, 116, 10, 32},
                {22, 140, 9,  28},
                {26, 164, 8,  24},
                {30, 188, 8,  20},
            };

            for (int ringIndex = 0; ringIndex < rings.length; ringIndex++) {
                int count = rings[ringIndex][0];
                int radiusPx = dpToPx(rings[ringIndex][1]);
                int sizePx = dpToPx(rings[ringIndex][2]);
                float opacity = rings[ringIndex][3] / 100f;
                double ringOffset = (ringIndex * 2 * Math.PI) / 11;

                for (int i = 0; i < count; i++) {
                    double angle = (2 * Math.PI * i) / count - Math.PI / 2 + ringOffset;
                    float dx = (float) (radiusPx * Math.cos(angle));
                    float dy = (float) (radiusPx * Math.sin(angle));

                    ImageView bell = new ImageView(requireContext());
                    bell.setImageResource(R.drawable.ic_bell);
                    bell.setImageTintList(ColorStateList.valueOf(bellColor));
                    bell.setAlpha(opacity);

                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(sizePx, sizePx);
                    bell.setLayoutParams(params);
                    bell.setX(cx - sizePx / 2f + dx);
                    bell.setY(cy - sizePx / 2f + dy);

                    container.addView(bell);
                }
            }
        });
    }

    private int dpToPx(float dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    private void navigateToExecute() {
        ((MainActivity) requireActivity()).navigateToExecute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
