package com.example.bookaholic.cart;

import com.example.bookaholic.details.Book;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Voucher implements Serializable {
    public static Voucher currentVoucher;

    private String id, nameVoucher, idVoucher, typeVoucher, startVoucher, endVoucher;
    private int discountVoucher, minimumVoucher, quantityVoucher, limitVoucher;
    private ArrayList<String> userID;

    @Exclude
    public static ArrayList<Voucher> allVouchers = new ArrayList<>();

    public Voucher(){}
    public Voucher(Voucher voucher){
        this.nameVoucher = voucher.nameVoucher;
        this.idVoucher = voucher.idVoucher;
        this.typeVoucher = voucher.typeVoucher;
        this.startVoucher = voucher.startVoucher;
        this.endVoucher = voucher.endVoucher;
        this.discountVoucher = voucher.discountVoucher;
        this.minimumVoucher = voucher.minimumVoucher;
        this.quantityVoucher = voucher.quantityVoucher;
        this.limitVoucher = voucher.limitVoucher;
        this.userID = voucher.userID;
    }
    public Voucher(String nameVoucher, String idVoucher, String typeVoucher, String startVoucher, String endVoucher,
                   int discountVoucher, int minimumVoucher, int quantityVoucher, int limitVoucher, ArrayList<String> userID){
        this.nameVoucher = nameVoucher;
        this.idVoucher = idVoucher;
        this.typeVoucher = typeVoucher;
        this.startVoucher = startVoucher;
        this.endVoucher = endVoucher;
        this.discountVoucher = discountVoucher;
        this.minimumVoucher = minimumVoucher;
        this.quantityVoucher = quantityVoucher;
        this.limitVoucher = limitVoucher;
        this.userID = userID;
    }

    public static Voucher findVoucherByName(String title) {
        for (int i = 0; i < allVouchers.size(); i++){
            if (allVouchers.get(i).getNameVoucher().contains(title)){
                return allVouchers.get(i);
            }
        }
        return null;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setNameVoucher(String nameVoucher){
        this.nameVoucher = nameVoucher;
    }

    public String getNameVoucher(){
        return nameVoucher;
    }

    public void setIdVoucher(String idVoucher){
        this.idVoucher = idVoucher;
    }

    public String getIdVoucher(){
        return idVoucher;
    }

    public void setTypeVoucher(String typeVoucher){
        this.typeVoucher = typeVoucher;
    }

    public String getTypeVoucher(){
        return typeVoucher;
    }

    public void setStartVoucher(String startVoucher){
        this.startVoucher = startVoucher;
    }

    public String getStartVoucher(){
        return startVoucher;
    }

    public void setEndVoucher(String endVoucher){
        this.endVoucher = endVoucher;
    }

    public String getEndVoucher(){
        return endVoucher;
    }

    public void setDiscountVoucher(int discountVoucher){
        this.discountVoucher = discountVoucher;
    }

    public int getDiscountVoucher(){
        return discountVoucher;
    }

    public void setMinimumVoucher(int minimumVoucher){
        this.minimumVoucher = minimumVoucher;
    }

    public int getMinimumVoucher(){
        return minimumVoucher;
    }

    public void setQuantityVoucher(int quantityVoucher){
        this.quantityVoucher = quantityVoucher;
    }

    public int getQuantityVoucher(){
        return quantityVoucher;
    }

    public void setLimitVoucher(int limitVoucher){
        this.limitVoucher = limitVoucher;
    }

    public int getLimitVoucher(){
        return limitVoucher;
    }

    public ArrayList<String> getUserID() {
        return userID;
    }

    public void setUserID(ArrayList<String> userID) {
        this.userID = userID;
    }

    public boolean checkVoucher(String id) {
        if (userID == null)
            return false;
        for (int i = 0; i < userID.size(); i++){
            if (id.equalsIgnoreCase(userID.get(i))){
                return true;
            }
        }
        return false;
    }
}
