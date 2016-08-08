package com.example.teplus.ciscourses.model;

import java.util.ArrayList;

public class DataModel {

    private String headerTitle;
    private String descTitle;
    private ArrayList<String> nameThItems;
    private ArrayList<String> nameEnItems;
    private ArrayList<String> creditItems;
    private ArrayList<String> codeItems;
    private ArrayList<String> idItems;


    public DataModel() {

    }

    public String getDescTitle() {
        return descTitle;
    }

    public void setDescTitle(String descTitle) {
        this.descTitle = descTitle;
    }

    public ArrayList<String> getIdItems() {
        return idItems;
    }

    public void setIdItems(ArrayList<String> idItems) {
        this.idItems = idItems;
    }

    public DataModel(String headerTitle) {
        this.headerTitle = headerTitle;

    }

    public ArrayList<String> getCreditItems() {
        return creditItems;
    }

    public void setCreditItems(ArrayList<String> creditItems) {
        this.creditItems = creditItems;
    }

    public ArrayList<String> getCodeItems() {
        return codeItems;
    }

    public void setCodeItems(ArrayList<String> codeItems) {
        this.codeItems = codeItems;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<String> getNameThItems() {
        return nameThItems;
    }

    public void setNameThItems(ArrayList<String> nameThItems) {
        this.nameThItems = nameThItems;
    }

    public ArrayList<String> getNameEnItems() {
        return nameEnItems;
    }

    public void setNameEnItems(ArrayList<String> nameEnItems) {
        this.nameEnItems = nameEnItems;
    }
}
