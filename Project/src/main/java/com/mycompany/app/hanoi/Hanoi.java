package com.mycompany.app.hanoi;

import java.util.Stack;

/**
 * Created by devlakhova on 1/27/15.
 */
public class Hanoi {

    public static void main(String[] args) {

        Stack<Integer> from = new Stack<Integer>();
        Stack<Integer> help = new Stack<Integer>();
        Stack<Integer> to = new Stack<Integer>();
        for (int i = 30; i > 0; i--) {
            from.push(i);
        }
        exchange(from, help, to, from.size());
        System.out.println(from + " " + help + " " + to);

    }

    public static void exchange (Stack<Integer> from, Stack<Integer> help, Stack<Integer> to, int count) {
        if (count > 0) {
            System.out.println(from + " " + help + " " + to);
            exchange(from, to, help, count - 1);
            int biggest = from.pop();
            to.push(biggest);
            exchange(help, from, to, count-1);
        }
    }
}
