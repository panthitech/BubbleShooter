package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble;

import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.RequestConfiguration;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type.DummyBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type.ItemBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type.LargeItemBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type.LargeObstacleBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type.LockedBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type.ObstacleBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.BasicBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.PlayerBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BubbleSystem {
    public static final int GRID_HEIGHT = 258;
    public static final int GRID_WIDTH = 300;
    public static final int ROW_ON_SCREEN = 11;
    public static final int TOTAL_COLUMN = 11;
    public float gridHeight;
    public float gridWidth;
    public final List<List<Bubble>> mBubbleList = new ArrayList();
    public final List<Bubble> mBubblePool = new ArrayList();
    public final Game mGame;
    public final List<Bubble> mRemovedList = new ArrayList();
    public int mRoot = 0;
    public int mTotalRow;

    public BubbleSystem(Game game) {
        this.mGame = game;
        this.gridWidth = game.getPixelFactor() * 300.0f;
        this.gridHeight = game.getPixelFactor() * 258.0f;
        String bubbles = ((MyLevel) game.getLevel()).mBubble.replaceAll("\\s+", RequestConfiguration.MAX_AD_CONTENT_RATING_UNSPECIFIED) + "00000000000";
        Log.e("NAMES", bubbles);
        this.mTotalRow = bubbles.length() / 11;
        initBubble(get2DArray(bubbles.toCharArray(), this.mTotalRow, 11));
        for (int i = 0; i < 11; i++) {
            this.mBubblePool.add(new Bubble(this.mGame, BubbleColor.BLANK));
        }
    }

    public char[][] get2DArray(char[] array, int row, int col) {
        int[] iArr = new int[2];
        iArr[1] = col;
        iArr[0] = row;
        char[][] temp = (char[][]) Array.newInstance(Character.TYPE, iArr);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                temp[i][j] = array[(i * col) + j];
            }
        }
        return temp;
    }

    public void initBubble(char[][] charArray) {
        for (int i = 0; i < this.mTotalRow; i++) {
            this.mBubbleList.add(new ArrayList(11));
        }
        for (int i2 = 0; i2 < this.mTotalRow; i2++) {
            for (int j = 0; j < 11; j++) {
                Bubble b = getBubbleType(charArray[i2][j]);
                b.setPosition((((float) j) * this.gridWidth) + (i2 % 2 != 0 ? this.gridWidth / 2.0f : 0.0f), ((float) i2) * this.gridHeight, i2, j);
                this.mBubbleList.get(i2).add(b);
            }
        }
        for (int i3 = 0; i3 < this.mTotalRow; i3++) {
            for (int j2 = 0; j2 < 11; j2++) {
                addEdges((Bubble) this.mBubbleList.get(i3).get(j2));
            }
        }
        for (int i4 = 0; i4 < this.mTotalRow; i4++) {
            for (int j3 = 0; j3 < 11; j3++) {
                ((Bubble) this.mBubbleList.get(i4).get(j3)).addToGame();
            }
        }
    }

    public Bubble getBubbleType(char type) {
        switch (type) {
            case '+':
                return new DummyBubble(this.mGame);
            case 66:
                return new LockedBubble(this.mGame, BubbleColor.BLUE);
            case 'G':
                return new LockedBubble(this.mGame, BubbleColor.GREEN);
            case 'O':
                return new LargeObstacleBubble(this.mGame);
            case 'R':
                return new LockedBubble(this.mGame, BubbleColor.RED);
            case 'X':
                return new LargeItemBubble(this.mGame);
            case 'Y':
                return new LockedBubble(this.mGame, BubbleColor.YELLOW);
            case 'o':
                return new ObstacleBubble(this.mGame);
            case 'x':
                return new ItemBubble(this.mGame);
            default:
                return new Bubble(this.mGame, getBubbleColor(type));
        }
    }

    public BubbleColor getBubbleColor(char color) {
        switch (color) {
            case 48:
                return BubbleColor.BLANK;
            case 'b':
                return BubbleColor.BLUE;
            case 'g':
                return BubbleColor.GREEN;
            case 'r':
                return BubbleColor.RED;
            case 'y':
                return BubbleColor.YELLOW;
            default:
                return BubbleColor.BLANK;
        }
    }

    public void addEdges(Bubble bubble) {
        int row = bubble.mRow;
        int col = bubble.mCol;
        if (row % 2 == 0) {
            if (row > 0 && col > 0) {
                bubble.mEdges[0] = (Bubble) this.mBubbleList.get(row - 1).get(col - 1);
            }
            if (row > 0) {
                bubble.mEdges[1] = (Bubble) this.mBubbleList.get(row - 1).get(col);
            }
            if (row < this.mTotalRow - 1 && col > 0) {
                bubble.mEdges[4] = (Bubble) this.mBubbleList.get(row + 1).get(col - 1);
            }
            if (row < this.mTotalRow - 1) {
                bubble.mEdges[5] = (Bubble) this.mBubbleList.get(row + 1).get(col);
            }
        } else {
            if (row > 0) {
                bubble.mEdges[0] = (Bubble) this.mBubbleList.get(row - 1).get(col);
            }
            if (row > 0 && col < 10) {
                bubble.mEdges[1] = (Bubble) this.mBubbleList.get(row - 1).get(col + 1);
            }
            if (row < this.mTotalRow - 1) {
                bubble.mEdges[4] = (Bubble) this.mBubbleList.get(row + 1).get(col);
            }
            if (row < this.mTotalRow - 1 && col < 10) {
                bubble.mEdges[5] = (Bubble) this.mBubbleList.get(row + 1).get(col + 1);
            }
        }
        if (col > 0) {
            bubble.mEdges[2] = (Bubble) this.mBubbleList.get(row).get(col - 1);
        }
        if (col < 10) {
            bubble.mEdges[3] = (Bubble) this.mBubbleList.get(row).get(col + 1);
        }
    }

    public void addRow() {
        List<Bubble> list = new ArrayList<>(11);
        for (int i = 0; i < 11; i++) {
            Bubble b = getFromPool();
            b.setPosition((((float) i) * this.gridWidth) + (this.mTotalRow % 2 != 0 ? this.gridWidth / 2.0f : 0.0f), ((float) Math.min(this.mTotalRow, 12)) * this.gridHeight, this.mTotalRow, i);
            b.addToGame();
            list.add(b);
        }
        this.mBubbleList.add(list);
        this.mTotalRow++;
        for (int i2 = 0; i2 < 11; i2++) {
            addEdges((Bubble) this.mBubbleList.get(this.mTotalRow - 1).get(i2));
        }
        for (int i3 = 0; i3 < 11; i3++) {
            addEdges((Bubble) this.mBubbleList.get(this.mTotalRow - 2).get(i3));
        }
    }

    public void reduceRow() {
        int maxRow = getMaxRow() + 1;
        while (this.mTotalRow > maxRow) {
            for (int i = 0; i < 11; i++) {
                Bubble b = (Bubble) this.mBubbleList.get(this.mTotalRow - 2).get(i);
                b.mEdges[4] = null;
                b.mEdges[5] = null;
            }
            for (int i2 = 0; i2 < 11; i2++) {
                Bubble b2 = (Bubble) this.mBubbleList.get(this.mTotalRow - 1).get(i2);
                b2.removeFromGame();
                returnToPool(b2);
            }
            this.mBubbleList.remove(this.mTotalRow - 1);
            this.mTotalRow--;
        }
    }

    public Bubble getFromPool() {
        if (!this.mBubblePool.isEmpty()) {
            return this.mBubblePool.remove(0);
        }
        return new Bubble(this.mGame, BubbleColor.BLANK);
    }

    public void returnToPool(Bubble bubble) {
        if (bubble.getClass() == Bubble.class) {
            bubble.setBubbleColor(BubbleColor.BLANK);
            this.mBubblePool.add(bubble);
        }
    }

    public Bubble getCollidedBubble(PlayerBubble playerBubble, Bubble bubble) {
        Bubble newBubble = bubble.getCollidedBubble(playerBubble);
        if (newBubble.mRow == this.mTotalRow - 1) {
            addRow();
        }
        return newBubble;
    }

    public void addCollidedBubble(BasicBubble basicBubble, Bubble bubble) {
        Bubble newBubble = getCollidedBubble(basicBubble, bubble);
        newBubble.setBubbleColor(basicBubble.mBubbleColor);
        reduceRow();
        popBubble(newBubble);
        popFloater();
        shiftBubble();
    }

    public void popBubble(Bubble bubble) {
        bfs(bubble, bubble.mBubbleColor);
        int size = this.mRemovedList.size();
        for (Bubble b : this.mRemovedList) {
            if (size >= 3) {
                b.popBubble();
            } else {
                b.mDepth = -1;
            }
        }
        this.mRemovedList.clear();
        bubble.checkLockedBubble();
        bubble.checkObstacleBubble();
    }

    public void bfs(Bubble root, BubbleColor color) {
        Queue<Bubble> queue = new LinkedList<>();
        root.mDepth = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            Bubble currentBubble = queue.poll();
            this.mRemovedList.add(currentBubble);
            for (Bubble b : currentBubble.mEdges) {
                if (b != null && b.mDepth == -1 && b.mBubbleColor == color) {
                    b.mDepth = currentBubble.mDepth + 1;
                    queue.offer(b);
                }
            }
        }
    }

    public void popFloater() {
        for (int i = 0; i < 11; i++) {
            Bubble bubble = (Bubble) this.mBubbleList.get(0).get(i);
            if (bubble.mBubbleColor != BubbleColor.BLANK) {
                dfs(bubble);
            }
        }
        for (int i2 = 0; i2 < this.mTotalRow; i2++) {
            for (int j = 0; j < 11; j++) {
                Bubble bubble2 = (Bubble) this.mBubbleList.get(i2).get(j);
                if (bubble2.mDiscover || bubble2.mBubbleColor == BubbleColor.BLANK) {
                    bubble2.mDiscover = false;
                } else {
                    bubble2.popFloater();
                }
            }
        }
    }

    public void dfs(Bubble bubble) {
        bubble.mDiscover = true;
        for (Bubble b : bubble.mEdges) {
            if (!(b == null || b.mDiscover || b.mBubbleColor == BubbleColor.BLANK)) {
                dfs(b);
            }
        }
    }

    public void shiftBubble() {
        int rowAdd = 11 - getRowOnScreen();
        if (rowAdd > this.mRoot) {
            rowAdd = this.mRoot;
        }
        this.mRoot -= rowAdd;
        shift(((float) rowAdd) * this.gridHeight);
    }

    public int getMaxRow() {
        for (int i = this.mTotalRow - 1; i >= 0; i--) {
            for (int j = 0; j < 11; j++) {
                if (((Bubble) this.mBubbleList.get(i).get(j)).mBubbleColor != BubbleColor.BLANK) {
                    return i + 1;
                }
            }
        }
        return 0;
    }

    public int getRowOnScreen() {
        return getMaxRow() - this.mRoot;
    }

    public void shift(float shiftDistance) {
        if (shiftDistance != 0.0f) {
            for (int i = 0; i < this.mTotalRow; i++) {
                for (int j = 0; j < 11; j++) {
                    ((Bubble) this.mBubbleList.get(i).get(j)).shiftBubble(shiftDistance);
                }
            }
        }
    }

    public boolean isShifting() {
        if (((Bubble) this.mBubbleList.get(0).get(0)).isShifting()) {
            return true;
        }
        return false;
    }

    public void clearBubble() {
        int depth = 0;
        for (int i = 0; i < this.mTotalRow; i++) {
            for (int j = 0; j < 11; j++) {
                Bubble bubble = (Bubble) this.mBubbleList.get(i).get(j);
                if (bubble.mBubbleColor != BubbleColor.BLANK) {
                    bubble.mDepth = depth;
                    bubble.popBubble();
                    depth++;
                }
            }
        }
    }

    public void addBubbleHint(BubbleColor color) {
        for (int i = 0; i < this.mTotalRow; i++) {
            for (int j = 0; j < 11; j++) {
                Bubble bubble = (Bubble) this.mBubbleList.get(i).get(j);
                if (bubble.mBubbleColor == color) {
                    bubble.addHint();
                }
            }
        }
    }

    public void removeBubbleHint(BubbleColor color) {
        for (int i = 0; i < this.mTotalRow; i++) {
            for (int j = 0; j < 11; j++) {
                Bubble bubble = (Bubble) this.mBubbleList.get(i).get(j);
                if (bubble.mBubbleColor == color) {
                    bubble.removeHint();
                }
            }
        }
    }
}
