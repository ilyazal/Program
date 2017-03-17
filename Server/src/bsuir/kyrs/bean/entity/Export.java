package bsuir.kyrs.bean.entity;

import java.io.Serializable;
import java.util.Date;


public class Export  implements Serializable{
    private static long serialVersionUID = 1L;
    private int id;
    private String called;
    private String material;
    private int quantity;
    private Date date2;
    private String factory;
    private int sum;

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Export export = (Export) o;

        if (id != export.id) return false;
        if (quantity != export.quantity) return false;
        if (called != null ? !called.equals(export.called) : export.called != null) return false;
        if (material != null ? !material.equals(export.material) : export.material != null) return false;
        return date2 != null ? date2.equals(export.date2) : export.date2 == null;

    }


    @Override
    public String toString() {
        return "Export{" +
                "sum=" + sum +
                ", factory='" + factory + '\'' +
                ", date2=" + date2 +
                ", quantity=" + quantity +
                ", material='" + material + '\'' +
                ", called='" + called + '\'' +
                ", id=" + id +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        Export.serialVersionUID = serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }



}