package com.mycompany.app.initializer.structure;

import java.util.Set;

/**
 * Created by devlakhova on 1/12/15.
 */
public class E {
    private Set<A> setA;
    private Long b;
    private F f;

    public Set<A> getSetA() {
        return setA;
    }

    public void setSetA(Set<A> setA) {
        this.setA = setA;
    }

    public Long getB() {
        return b;
    }

    public void setB(Long b) {
        this.b = b;
    }

    public F getF() {
        return f;
    }

    public void setF(F f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "E{" +
                "setA=" + setA +
                ", b=" + b +
                ", f=" + f +
                '}';
    }
}
