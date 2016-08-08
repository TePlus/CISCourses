package com.example.teplus.ciscourses.item;

public class SubjectsItem {
    private int id;
    private String nameTh;
    private String nameEn;
    private String subGroupsNameTh;
    private String code;
    private int credit;

    public String getSubGroupsNameTh() {
        return subGroupsNameTh;
    }

    public void setSubGroupsNameTh(String subGroupsNameTh) {
        this.subGroupsNameTh = subGroupsNameTh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}