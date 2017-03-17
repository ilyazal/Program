package bsuir.kyrs.bean.entity;

import java.io.Serializable;
import java.util.Date;

public class Import implements Serializable {
    private static long serialVersionUID = 1L;
    private int id;
    private String called;
    private String material;
    private int quantity;
    private Date date2;
    private String factory;
    private int sum;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        Import.serialVersionUID = serialVersionUID;
    }

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
    public String toString() {
        return "Import{" +
                "id=" + id +
                ", called='" + called + '\'' +
                ", material='" + material + '\'' +
                ", quantity=" + quantity +
                ", date2=" + date2 +
                ", factory='" + factory + '\'' +
                ", sum=" + sum +
                '}';
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
