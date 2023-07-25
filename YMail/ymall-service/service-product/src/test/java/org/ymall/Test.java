/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall;

public class Test {
    public static void main(String[] args) {
        Son son = new Son();
        Son get = son.GET();
        Parent parent = new Son();
        System.out.println("get = " + get.getPname());
        System.out.println("get = " + parent.getPname());
    }
}
