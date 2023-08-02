/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package utils;

import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ValidationUtilsTest
 *
 * @Author IIIDelay
 * @Date 2023/8/1 21:39
 **/
public class ValidationUtilsTest {

    @Test
    public void checkPhone() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        deque.add("d");
        String pop = deque.pop();
        System.out.println("pop = " + pop);
        System.out.println("pop = " + deque.pop());
        System.out.println("pop = " + deque.pop());
        System.out.println("pop = " + deque.pop());
        System.out.println("pop = " + deque.poll());
        System.out.println("deque = " + deque);
    }
}