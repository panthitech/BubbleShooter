package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public synchronized void onGlobalLayout() {
                ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
                if (viewTreeObserver.isAlive()) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                    GameFragment.this.onLayoutCreated();
                }
            }
        });
    }

    
    public void onLayoutCreated() {
    }

    public boolean onBackPressed() {
        return false;
    }

    public GameActivity getGameActivity() {
        return (GameActivity) getActivity();
    }

    public void showDialog(GameDialog newDialog) {
        getGameActivity().showDialog(newDialog);
    }
}
