package com.mycompany.app.initializer.structure;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by devlakhova on 1/12/15.
 */
public class A {
    private Integer a;

    @Providere(MyStringIntegerProvider.class)
    private String b;
    private boolean c;
    private Date d;
    private List<E> listE;
    private Collection<F> collectionF;
    private Map<F, G> mapFE;

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public boolean isC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public List<E> getListE() {
        return listE;
    }

    public void setListE(List<E> listE) {
        this.listE = listE;
    }

    public Collection<F> getCollectionF() {
        return collectionF;
    }

    public void setCollectionF(Collection<F> collectionF) {
        this.collectionF = collectionF;
    }

    public Map<F, G> getMapFE() {
        return mapFE;
    }

    public void setMapFE(Map<F, G> mapFE) {
        this.mapFE = mapFE;
    }

    @Override
    public String toString() {
        return "A{" +
                "a=" + a +
                ", b='" + b + '\'' +
                ", c=" + c +
                ", d=" + d +
                ", listE=" + listE +
                ", collectionF=" + collectionF +
                ", mapFE=" + mapFE +
                '}';
    }
}
