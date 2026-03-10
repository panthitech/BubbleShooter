package com.puzzle.bubble.shooter.colors.bubbleshooter.level;

import android.content.Context;
import android.util.Xml;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.level.Level;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.level.LevelManager;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class MyLevelManager extends LevelManager {
    public static final String FILE_NAME = "data.xml";
    public static final String FILE_TAG = "level";
    public final Context mContext;
    public MyLevel mLevel;
    public String mLevelTagName;

    public MyLevelManager(Context context) {
        this.mContext = context;
    }

    public Level getLevel(int level) {
        this.mLevelTagName = FILE_TAG + level;
        this.mLevel = new MyLevel(level);
        try {
            parse(this.mContext.getAssets().open(FILE_NAME));
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return this.mLevel;
    }

    public void parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
            parser.setInput(in, (String) null);
            parser.nextTag();
            read(parser);
        } finally {
            in.close();
        }
    }

    public void read(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(2, (String) null, FILE_TAG);
        while (parser.next() != 3) {
            if (parser.getEventType() == 2) {
                if (parser.getName().equals(this.mLevelTagName)) {
                    readLevel(parser);
                } else {
                    skip(parser);
                }
            }
        }
    }

    public void readLevel(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(2, (String) null, this.mLevelTagName);
        while (parser.next() != 3) {
            if (parser.getEventType() == 2) {
                setLevelInfo(parser.getName(), parser.nextText());
            }
        }
    }

    public void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() == 2) {
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case 2:
                        depth++;
                        break;
                    case 3:
                        depth--;
                        break;
                }
            }
            return;
        }
        throw new IllegalStateException();
    }

    public void setLevelInfo(String name, String text) {
        char c;
        switch (name.hashCode()) {
            case -1378241396:
                if (name.equals("bubble")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -985752863:
                if (name.equals("player")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -880905839:
                if (name.equals("target")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -851085895:
                if (name.equals("level_tutorial")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 207109909:
                if (name.equals("level_type")) {
                    c = 0;
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
                this.mLevel.setLevelType(text);
                return;
            case 1:
                this.mLevel.setLevelTutorial(text);
                return;
            case 2:
                this.mLevel.mTarget = Integer.parseInt(text);
                return;
            case 3:
                this.mLevel.mPlayer = text;
                this.mLevel.mMove = text.length();
                return;
            case 4:
                this.mLevel.mBubble = text;
                return;
            default:
                return;
        }
    }
}
