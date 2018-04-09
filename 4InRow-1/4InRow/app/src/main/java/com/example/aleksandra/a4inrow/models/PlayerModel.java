package com.example.aleksandra.a4inrow.models;

import java.io.Serializable;

/**
 * Created by Aleksandra on 21/01/2018.
 */

@SuppressWarnings("unused")
public class PlayerModel implements Serializable {

    @Override
    public String toString() {
        String s = "";
        s += this.getFirst_name() + " ";
        s += this.getLast_name() + " ";
        s += this.getDate_of_birth() + " ";
        s += this.getGender() + " ";
        s += this.getEmail() + " ";
        s += this.getActive();
        return s;
    }

    private String First_name;
    private String Last_name;
    private String Date_of_birth;
    private String Gender;
    private String Email;
    private String Active;

    public PlayerModel() {
    }

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }

    public String getDate_of_birth() {
        return Date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        Date_of_birth = date_of_birth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    private void setEmail(String email) {
        Email = email;
    }

    public String getActive() {
        return Active;
    }

    private void setActive(String active) {
        Active = active;
    }

    public byte[] PlayerObjectToBytes() {
        String temp = "";
        temp += this.getFirst_name() + "";
        temp += this.getLast_name() + "";
        temp += this.getDate_of_birth() + "";
        temp += this.getGender() + "";
        temp += this.getEmail() + "";
        temp += this.getActive();
        return temp.getBytes();
    }

    public void BytesToPlayerObject(byte[] bytes) {
        String temp = new String(bytes);
        String[] parts = temp.split(" ");
        this.setFirst_name(parts[0]);
        this.setLast_name(parts[1]);
        this.setDate_of_birth(parts[2]);
        this.setGender(parts[3]);
        this.setEmail(parts[4]);
        this.setActive(parts[5]);
    }

}
