package com.bango.bangoLive.ZegoServices;

import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Utils {
    public Utils() {
    }

    public static int dp2px(float var0, DisplayMetrics var1) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, var0, var1);
    }

    public static int sp2px(float var0, DisplayMetrics var1) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, var0, var1);
    }
}
