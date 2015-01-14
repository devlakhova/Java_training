package com.mycompany.app.initializer.structure;

import java.util.Arrays;

/**
 * Created by devlakhova on 1/12/15.
 */
public class G {
    private String s;

    @Provider(HProvider.class)
    private H h;

    private int[] intArr;

    private H[] hArr;


    public int[] getIntArr() {
        return intArr;
    }

    public void setIntArr(int[] intArr) {
        this.intArr = intArr;
    }

    public H[] gethArr() {
        return hArr;
    }

    public void sethArr(H[] hArr) {
        this.hArr = hArr;
    }

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }



    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "G{" +
                "s='" + s + '\'' +
                ", h=" + h +
                ", intArr=" + Arrays.toString(intArr) +
                ", hArr=" + Arrays.toString(hArr) +
                '}';
    }
}
