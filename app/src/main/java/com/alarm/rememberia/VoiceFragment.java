package com.alarm.rememberia;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.alarm.rememberia.databinding.FragmentVoiceBinding;

public class VoiceFragment extends Fragment {

    private FragmentVoiceBinding binding;
    private static final String STATE_IDLE = "idle";
    private static final String STATE_LISTENING = "listening";
    private static final String STATE_TRANSCRIBING = "transcribing";
    private static final String STATE_DONE = "done";
    private static final String STATE_ERROR = "error";

    private String voiceState = STATE_IDLE;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable transcribingRunnable;
    private Runnable doneRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVoiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transcribingRunnable = () -> setVoiceState(STATE_TRANSCRIBING);
        doneRunnable = () -> setVoiceState(STATE_DONE);

        binding.voiceMicZone.setOnClickListener(v -> {
            if (STATE_IDLE.equals(voiceState)) {
                startListening();
            }
        });
        binding.voiceButtonCancel.setOnClickListener(v -> {
            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            }
        });
        binding.voiceButtonConfirm.setOnClickListener(v -> ((MainActivity) requireActivity()).navigateToHome());
        binding.voiceButtonRetry.setOnClickListener(v -> setVoiceState(STATE_IDLE));
    }

    private void startListening() {
        setVoiceState(STATE_LISTENING);
        handler.postDelayed(transcribingRunnable, 2000);
        handler.postDelayed(doneRunnable, 5000);
    }

    private void setVoiceState(String state) {
        if (STATE_IDLE.equals(state) || STATE_ERROR.equals(state)) {
            handler.removeCallbacks(transcribingRunnable);
            handler.removeCallbacks(doneRunnable);
        }
        voiceState = state;

        boolean showIdleInMicZone = STATE_IDLE.equals(state) || STATE_DONE.equals(state);
        binding.voiceStateIdle.setVisibility(showIdleInMicZone ? View.VISIBLE : View.GONE);
        binding.voiceStateListening.setVisibility(STATE_LISTENING.equals(state) ? View.VISIBLE : View.GONE);
        binding.voiceStateTranscribing.setVisibility(STATE_TRANSCRIBING.equals(state) ? View.VISIBLE : View.GONE);

        boolean showResult = STATE_TRANSCRIBING.equals(state) || STATE_DONE.equals(state);
        binding.voiceLabelTranscription.setVisibility(showResult ? View.VISIBLE : View.GONE);
        binding.voiceCardTranscription.setVisibility(showResult ? View.VISIBLE : View.GONE);

        boolean showDetected = STATE_DONE.equals(state);
        binding.voiceLabelDetected.setVisibility(showDetected ? View.VISIBLE : View.GONE);
        binding.voiceCardDetected.setVisibility(showDetected ? View.VISIBLE : View.GONE);
        binding.voiceButtonsDone.setVisibility(showDetected ? View.VISIBLE : View.GONE);

        binding.voiceCardError.setVisibility(STATE_ERROR.equals(state) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(transcribingRunnable);
        handler.removeCallbacks(doneRunnable);
        binding = null;
    }
}
