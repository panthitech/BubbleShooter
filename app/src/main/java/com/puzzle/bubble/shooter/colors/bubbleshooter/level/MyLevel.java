package com.puzzle.bubble.shooter.colors.bubbleshooter.level;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.level.Level;

public class MyLevel extends Level {
    public static String mBubble;
    public LevelTutorial mLevelTutorial;
    public LevelType mLevelType;
    public int mMove;
    public  String mPlayer;
    public int mScore;
    public int mStar;
    public int mTarget;

    public MyLevel(int level) {
        super(level);
    }

    public void setLevelType(String type) {
        char c;
        switch (type.hashCode()) {
            case 111185:
                if (type.equals("pop")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 949444906:
                if (type.equals("collect")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mLevelType = LevelType.POP_BUBBLE;
                return;
            case 1:
                this.mLevelType = LevelType.COLLECT_ITEM;
                return;
            default:
                return;
        }
    }

    public void setLevelTutorial(String tutorial) {
        char c;
        switch (tutorial.hashCode()) {
            case -1383205240:
                if (tutorial.equals("bounce")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1112890261:
                if (tutorial.equals("collect_items")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -915222468:
                if (tutorial.equals("obstacle_bubble")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -889473228:
                if (tutorial.equals("switch")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -690457931:
                if (tutorial.equals("fire_bubble")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 109413407:
                if (tutorial.equals("shoot")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 112189281:
                if (tutorial.equals("locked_bubble")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 537173193:
                if (tutorial.equals("bomb_bubble")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1930689768:
                if (tutorial.equals("color_bubble")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mLevelTutorial = LevelTutorial.SHOOT_BUBBLE;
                return;
            case 1:
                this.mLevelTutorial = LevelTutorial.BOUNCE_BUBBLE;
                return;
            case 2:
                this.mLevelTutorial = LevelTutorial.SWITCH_BUBBLE;
                return;
            case 3:
                this.mLevelTutorial = LevelTutorial.COLLECT_ITEMS;
                return;
            case 4:
                this.mLevelTutorial = LevelTutorial.COLOR_BUBBLE;
                return;
            case 5:
                this.mLevelTutorial = LevelTutorial.FIRE_BUBBLE;
                return;
            case 6:
                this.mLevelTutorial = LevelTutorial.BOMB_BUBBLE;
                return;
            case 7:
                this.mLevelTutorial = LevelTutorial.LOCKED_BUBBLE;
                return;
            case '\b':
                this.mLevelTutorial = LevelTutorial.OBSTACLE_BUBBLE;
                return;
            default:
                return;
        }
    }
}
