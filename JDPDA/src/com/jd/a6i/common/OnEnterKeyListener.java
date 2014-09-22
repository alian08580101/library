package com.jd.a6i.common;

import android.view.KeyEvent;
import android.view.View;

public abstract class OnEnterKeyListener implements View.OnKeyListener {
    public static final boolean NO_CONSUMED = false;
    public static final boolean CONSUMED = true;

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            return onEnter(view);
        }
        return NO_CONSUMED;
    }

    public abstract boolean onEnter(View view);
}
