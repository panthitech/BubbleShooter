package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGame;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.GameView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameFragment;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class MyGameFragment extends GameFragment implements View.OnClickListener {
    public static final String LEVEL = "level";
    public Game mGame;
    public int mLevel;

    public static MyGameFragment newInstance(int level) {
        MyGameFragment fragment = new MyGameFragment();
        Bundle args = new Bundle();
        args.putInt(LEVEL, level);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mLevel = getArguments().getInt(LEVEL);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    
    public void onLayoutCreated() {
        ImageButton btnPause = (ImageButton) getGameActivity().findViewById(R.id.btn_pause);
        UIEffect.createButtonEffect(btnPause);
        btnPause.setOnClickListener(this);
        this.mGame = new MyGame(getGameActivity(), (GameView) getView().findViewById(R.id.game_view), this.mLevel);
        this.mGame.start();
        initAd();
    }

    public void initAd() {
        ((AdView) getView().findViewById(R.id.adView)).loadAd(new AdRequest.Builder().build());
    }

    public void onPause() {
        super.onPause();
        this.mGame.pause();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mGame.stop();
    }

    public boolean onBackPressed() {
        this.mGame.pause();
        return true;
    }

    public void onClick(View view) {
        getGameActivity().getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
        this.mGame.pause();
    }
}
