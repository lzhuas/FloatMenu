package com.gaiso.floatmenu.base;

/**
 * Created by Gaiso on 2016/11/2.
 */

public interface IBarge {

    int LEFT = 0;

    int RIGHT = 1;

    void showBarge();

    void hideBarge();

    void reverseBarge();

    boolean isBargeShow();

    void setDefaultBarge(int side);
}
