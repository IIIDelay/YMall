/*
 * Copyright (c) 2024. 版权归III_Delay所有
 */

package org.iiidev.ymall.ui;

import java.text.DecimalFormat;

public class ConsoleProgressBarDemo {

    /**
     * 进度条长度
     */
    private int barLen;

    /**
     * 用于进度条显示的字符
     */
    private char showChar;

    private DecimalFormat formater = new DecimalFormat("#.##%");

    /**
     * 使用系统标准输出，显示字符进度条及其百分比
     */
    public ConsoleProgressBarDemo(int barLen, char showChar) {
        this.barLen = barLen;
        this.showChar = showChar;
    }

    /**
     * 显示进度条
     */
    public void show(int value) {
        if (value < 0 || value > 100) {
            return;
        }

        reset();

        // 比例
        float rate = (float) (value*1.0 / 100);
        // 比例*进度条总长度=当前长度
        draw(barLen, rate);
        if (value == 100L) {
            afterComplete();
        }
    }

    /**
     * 画指定长度个showChar
     */
    private void draw(int barLen, float rate) {
        int len = (int) (rate * barLen);
        System.out.print("Progress: ");
        for (int i = 0; i < len; i++) {
            System.out.print(showChar);
        }
        for (int i = 0; i < barLen-len; i++) {
            System.out.print(" ");
        }
        System.out.print(" |" + format(rate));
    }


    /**
     * 光标移动到行首
     */
    private void reset() {
        System.out.print('\r');
    }

    /**
     * 完成后换行
     */
    private void afterComplete() {
        System.out.print('\n');
    }

    private String format(float num) {
        return formater.format(num);
    }

    /**
     * main : 进度条
     *
     * @param args args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ConsoleProgressBarDemo cpb = new ConsoleProgressBarDemo(50, '#');
        for (int i = 1; i <= 100; i++) {
            cpb.show(i);
            Thread.sleep(100);
        }
    }

}

