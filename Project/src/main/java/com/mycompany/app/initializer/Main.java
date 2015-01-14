package com.mycompany.app.initializer;

import com.mycompany.app.initializer.structure.A;
import com.mycompany.app.initializer.structure.E;

/**
 * Created by devlakhova on 1/12/15.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(new InitializerImpl().getInstance(A.class));
    }
}

