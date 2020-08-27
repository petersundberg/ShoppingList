package com.example.shoppinglist;

public class ListItemsModel {

    private int id;
    private String item;
    private int listId;


//Constructors
    public ListItemsModel(int id, String item, int listId) {
        this.id = id;
        this.item = item;
        this.listId = listId;
    }

    public ListItemsModel() {
    }


//getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }


//toString
//    @Override
//    public String toString() {
//        return "ListItemsModel{" +
//                "id=" + id +
//                ", item='" + item + '\'' +
//                ", listId=" + listId +
//                '}';
//    }

    //toString
    @Override
    public String toString() {
        return item;
    }



}
