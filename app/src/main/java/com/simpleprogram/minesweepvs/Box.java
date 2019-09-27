package com.simpleprogram.minesweepvs;

import android.media.Image;

public enum Box {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,

    BOMB,

    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;
    public Image image;

    public int getImage() {
        switch (this) {
            case ZERO: return R.drawable.number_0;
            case NUM1: return R.drawable.number_1;
            case NUM2: return R.drawable.number_2;
            case NUM3: return R.drawable.number_3;
            case NUM4: return R.drawable.number_4;
            case NUM5: return R.drawable.number_5;
            case NUM6: return R.drawable.number_6;
            case NUM7: return R.drawable.number_7;
            case NUM8: return R.drawable.number_8;

            case BOMB: return R.drawable.bomb_normal;
            case FLAGED: return R.drawable.flag;
            case BOMBED: return R.drawable.bomb_exploded;



            default: return R.drawable.button;
        }
    }

    Box nextNumberBox() {
        return Box.values()[this.ordinal() + 1];
    }

    int getNumber() {
        int nr = ordinal();
        if (nr>=Box.NUM1.ordinal() && nr <= Box.NUM8.ordinal())
            return ordinal();
        return -1;
    }
}
