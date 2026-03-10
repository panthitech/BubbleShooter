package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameFragment;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.TransitionEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.ExitDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.SettingDialog;

public class MenuFragment extends GameFragment implements View.OnClickListener, TransitionEffect.OnTransitionListener {
    public TransitionEffect mTransitionEffect;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mTransitionEffect = new TransitionEffect(getGameActivity());
        this.mTransitionEffect.setListener(this);
        init();
    }

    public void init() {
        ImageView imageLogoBg = (ImageView) getView().findViewById(R.id.image_logo_bg);
        ImageView imageLogo = (ImageView) getView().findViewById(R.id.image_logo);
        imageLogo.setScaleX(0.0f);
        imageLogo.setScaleY(0.0f);
        imageLogo.animate().setStartDelay(300).setDuration(1000).scaleX(1.0f).scaleY(1.0f).setInterpolator(new OvershootInterpolator());
        ImageButton btnSetting = (ImageButton) getView().findViewById(R.id.btn_setting);
        ImageButton btnStart = (ImageButton) getView().findViewById(R.id.btn_start);
        btnSetting.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        UIEffect.createButtonEffect(btnSetting);
        UIEffect.createButtonEffect(btnStart);
        imageLogoBg.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.light_rotate));
        imageLogo.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.logo_pulse));
        btnStart.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.button_pulse));
        getView().findViewById(R.id.image_fox).startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.player_pulse));
        UIEffect.createPopUpEffect(imageLogoBg, 1);
        UIEffect.createPopUpEffect(btnStart, 2);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_setting) {
            getGameActivity().getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            showDialog(new SettingDialog(getGameActivity()));
        } else if (id == R.id.btn_start) {
            getGameActivity().getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            this.mTransitionEffect.show();
        }
    }

    public void onTransition() {
        getGameActivity().navigateToFragment(new MapFragment());
    }

    public boolean onBackPressed() {
        showDialog(new ExitDialog(getGameActivity()) {
            public void exit() {
                MenuFragment.this.getGameActivity().finish();
            }
        });
        return true;
    }
}
