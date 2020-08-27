package com.example.shoppinglist;

public class ShoppingListModel {

    private int id;
    private String listName;


//Constructors
    public ShoppingListModel(int id, String listName) {
        this.id = id;
        this.listName = listName;
    }

    public ShoppingListModel() {
    }


//getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }


//toString
    @Override
    public String toString() {
        return listName;

//        return "ShoppingListModel{" +
//                "id=" + id +
//                ", listName='" + listName + '\'' +
//                '}';
    }
}
