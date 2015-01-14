package com.mycompany.app;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Hello world!
 *
 */
public class App 
{
    static {
        System.out.println(200);
    }
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
//        X varXX = new X();
        X varXY = new Y();
        Y.f(455555);
        Y.f(44242436);
        varXY.getClass();
        LinkedList <String> list = new LinkedList<String>();
        list.add("a");
        list.add("c");
        System.out.println(list.getFirst());
        System.out.println(list.getLast());
        list.add(1,"b");
        System.out.println(list.getFirst());
        System.out.println(list.getLast());
        list.add("d");
        list.add("e");
        System.out.println(list);
        Class c = X.class;
        if (c.isInstance(varXY)) {
            System.out.println("\nwjkefk\n");
        }
        Object[] a = new Object[10];
        int[] b = new int[10];
        Object o = (Object)b;
        System.out.println(Arrays.toString(c.getDeclaredFields()));
        System.out.println(Arrays.toString(c.getMethods()));
        System.out.println(Arrays.toString(c.getConstructors()));
        try {
            for (Method method : c.getDeclaredMethods()) {
                method.getName();
            }

            c.getMethod("invokeMe", Integer.class).invoke(varXY, 32);

        }
        catch (NoSuchMethodException noMethod) {
            System.out.println("No such method");
        }
        catch (IllegalAccessException acces) {
            System.out.println("Illegal access");
        }
        catch (InvocationTargetException ex) {
            System.out.println("Bla");
        }

        ListIterator <String> itr = list.listIterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
//        Y varYY = new Y();

//        System.out.println(varXX.a);
//        System.out.println(varXX.getA());
//
//        System.out.println(varXY.a);
//        System.out.println(varXY.getA());
//
//        System.out.println(varYY.a);
//        System.out.println(varYY.getA());

    }

    static class Bla {
        private int a;
        private String b;
        private boolean assignable;

        public boolean isAssignable() {
            return assignable;
        }

        public void setAssignable(boolean assignable) {
            this.assignable = assignable;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return "Bla{" +
                    "a=" + a +
                    ", b='" + b + '\'' +
                    '}';
        }
    }

}


class X {
    static int s = f(999);
    int k = f(122);
    public static int f(int i) {
        System.out.println(i);
        return i;
    }
    public void invokeMe (Integer bc) {
        System.out.println("abdirbi");
    }

    {
        System.out.println(333);
    }
    static
    {
        System.out.println(444);
    }

    public int a =1;

    public X() {
        System.out.println("Create X");
    }


    public  int getA() {
        return a;
    }
}

class Y extends X {
    static int e = f(73739);
    {
        System.out.println(555);
    }
    static
    {
        System.out.println(666);
    }
    public int a = 2;

    public Y() {
        System.out.println("Create Y");
    }
    public static int fy(int i) {
        System.out.println(64564);
        return i;
    }
    public int getA() {
        return a;
    }

}
