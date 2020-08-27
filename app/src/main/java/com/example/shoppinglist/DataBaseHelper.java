package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String SHOPPINGLIST_TABLE = "SHOPPINGLIST_TABLE";
    public static final String LIST_ID = "LIST_ID";
    public static final String COLUMN_LIST_NAME = "LIST_NAME";


    public static final String ITEMS_TABLE = "ITEMS_TABLE";
    public static final String COLUMN_ITEM_ID = "ITEM_ID";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String FK_LIST_ID = "LIST_ID";


//    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }


    //Constructor for this Class
    public DataBaseHelper(@Nullable Context context) {
        super(context, "shoppinglists.db", null, 1);
    }


    //this is called the first time a database is accessed. There should be code here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {

        //create Table for ShoppingList name
        String createShoppinglistTableStatement = "CREATE TABLE " + SHOPPINGLIST_TABLE + " (" + LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_LIST_NAME + " TEXT)";

        String createItemlistTableStatement = "CREATE TABLE " + ITEMS_TABLE + " (" + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ITEM_NAME + " TEXT, " + FK_LIST_ID + " INTEGER, " +
                "FOREIGN KEY(LIST_ID) REFERENCES SHOPPINGLIST_TABLE(LIST_ID))";


        //db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL(createShoppinglistTableStatement);
        db.execSQL(createItemlistTableStatement);
        //db.execSQL("PRAGMA foreign_keys=ON;");

    }


    //this is called if the database version number changes. It prevents previous users apps from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + SHOPPINGLIST_TABLE);    //db.execSQL("DROP TABLE IF EXISTS " + SHOPPINGLIST_TABLE);
        //onCreate(db);

    }


    public boolean createShoppingList(String listName) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();     //"ContentValues" is like "putExtra" in an Intent (Key/Value-pairs)

        //Listname
        cv.put(COLUMN_LIST_NAME, listName);
        long insert = db.insert(SHOPPINGLIST_TABLE, null, cv);
        cv.clear();


        if (insert == -1) {
            return false;
        } else {
            db.close();
            return true;
        }


    }


//    public boolean createShoppingList(String listName, ListItemsModel listItemsModel){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        //Listname
//        cv.put(COLUMN_LIST_NAME, listName);
//        long insert = db.insert(SHOPPINGLIST_TABLE, null, cv);
////------------------------------------------------------
//
//        ContentValues cvItems = new ContentValues();
//
//        //item
//        cvItems.put(COLUMN_ITEM_NAME, listItemsModel.getItem());
//        long insertItem = db.insert(ITEMS_TABLE, null, cvItems);
//
////------------------------------------------------------
//        if(insert == -1){
//            return false;
//        }
//        else{
//            db.close();
//            return true;
//        }
//
//
//    }


    public boolean addItem(String listName, String itemToAdd) {

        String queryCurrentListId = "SELECT " + LIST_ID + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + "'" + listName + "'";

        SQLiteDatabase readDB = this.getWritableDatabase(); //SQLiteDatabase readDB = this.getReadableDatabase();
        readDB.execSQL("PRAGMA foreign_keys=ON;");
        Cursor cursor = readDB.rawQuery(queryCurrentListId, null);
        cursor.moveToFirst();

        int currentListID = cursor.getInt(0);

//------------------------------------------------------------------------------------------------------


        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");
        ContentValues cv = new ContentValues();


        //cv.put(COLUMN_LIST_ID, insert); //cv.put(COLUMN_LIST_ID, insert);
        cv.put(COLUMN_ITEM_NAME, itemToAdd);    //cv.put(COLUMN_ITEM_NAME, itemToAdd);
        cv.put(FK_LIST_ID, currentListID); //cv.put(COLUMN_LIST_ID, listID);
        long insertedOK = db.insert(ITEMS_TABLE, null, cv);
        //long insert = db.insert(ITEMS_TABLE, null, cv);

        if (insertedOK == -1) {
            cv.clear();
            db.close();
            return false;
        } else {
            cv.clear();
            db.close();
            return true;
        }


    }


    public List<ShoppingListModel> getAllSavedListsAsModel() {

        List<ShoppingListModel> returnList = new ArrayList<>();

        String queryGetAllLists = "SELECT * FROM " + SHOPPINGLIST_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryGetAllLists, null);

        if (cursor.moveToFirst()) {
            do {
                int listID = cursor.getInt(0);
                String listName = cursor.getString(1);

                ShoppingListModel tempList = new ShoppingListModel(listID, listName);
                returnList.add(tempList);

            } while (cursor.moveToNext());

        } else {
            //failure - do nothing
        }

        cursor.close();
        db.close();
        Collections.reverse(returnList); //reverse lists to show the latest first in ListView
        return returnList;

    }


    public List<String> getAllSavedLists() {
        List<String> listNames = new ArrayList<>();

        String queryGetAllListNames = "SELECT * FROM " + SHOPPINGLIST_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryGetAllListNames, null);

        if (cursor.moveToFirst()) {
            do {
                //int listID = cursor.getInt(0);
                String listName = cursor.getString(1);

                //ArrayList listNames = new ShoppingListModel(listID, listName);
                listNames.add(listName);

            } while (cursor.moveToNext());

        } else {
            //failure - do nothing
        }
        cursor.close();
        db.close();
        Collections.reverse(listNames); //reverse lists to show the latest first in ListView
        return listNames;
    }


    public List<ListItemsModel> getAllItemLists() {
        List<ListItemsModel> itemsList = new ArrayList<>();

        String queryGetAllItems = "SELECT * FROM " + ITEMS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryGetAllItems, null);
        if (cursor.moveToFirst()) {
            do {
                int itemID = cursor.getInt(0);
                String itemName = cursor.getString(1);
                int listID = cursor.getInt(2);

                //ArrayList listNames = new ShoppingListModel(listID, listName);
                ListItemsModel allItems = new ListItemsModel(itemID, itemName, listID);
                itemsList.add(allItems);

            } while (cursor.moveToNext());

        } else {
            //failure - do nothing
        }
        cursor.close();
        db.close();
        //Collections.reverse(listNames); //reverse lists to show the latest first in ListView
        return itemsList;
    }



    public List<ListItemsModel> getListItems() {
        List<ListItemsModel> itemsList = new ArrayList<>();

        String queryGetAllItems = "SELECT * FROM " + ITEMS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryGetAllItems, null);
        if (cursor.moveToFirst()) {
            do {
                int itemID = cursor.getInt(0);
                String itemName = cursor.getString(1);
                int listID = cursor.getInt(2);

                //ArrayList listNames = new ShoppingListModel(listID, listName);
                ListItemsModel allItems = new ListItemsModel(itemID, itemName, listID);
                itemsList.add(allItems);

            } while (cursor.moveToNext());

        } else {
            //failure - do nothing
        }
        cursor.close();
        db.close();
        //Collections.reverse(listNames); //reverse lists to show the latest first in ListView
        return itemsList;
    }



//    public List<String> getShoppingListName111(){
//        List<String> listNames = new ArrayList<>();
//
//        String queryGetAllListNames = "SELECT * FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(queryGetAllListNames, null);
//
//        if(cursor.moveToFirst()){
//            do{
//                //int listID = cursor.getInt(0);
//                String listName = cursor.getString(1);
//
//                //ArrayList listNames = new ShoppingListModel(listID, listName);
//                listNames.add(listName);
//
//            } while (cursor.moveToNext());
//
//        }
//        else{
//            //failure - do nothing
//        }
//        cursor.close();
//        db.close();
//        Collections.reverse(listNames); //reverse lists to show the latest first in ListView
//        return listNames;
//    }

    //show selected List's info from database
    public ShoppingListModel getShoppingListName(ShoppingListModel shoppingListModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String queryString = "SELECT " + COLUMN_LIST_NAME + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
        String queryString = "SELECT * FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        ShoppingListModel foundShoppingList = new ShoppingListModel();
        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                //int age = cursor.getInt(2);
                //boolean active = cursor.getInt(3) == 1 ? true:false;

                foundShoppingList = new ShoppingListModel(id, name);

            } while (cursor.moveToNext());

            return foundShoppingList;
        }
        return foundShoppingList;
    }




    public int getlistID(String listName) {
        List<ShoppingListModel> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //String queryString = "SELECT " + COLUMN_LIST_NAME + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
        //String queryListID = "SELECT * FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;

        String queryListID = "SELECT LIST_ID FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + "'" + listName + "'";     //String queryListID = "SELECT LIST_ID FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
        //  " = " + "'" + listName + "'";

        //int listID; //List<ShoppingListModel> listID = new ArrayList<>();
        Cursor cursor = db.rawQuery(queryListID, null);
        int id = 0;
        //ShoppingListModel shoppingListModel = new ShoppingListModel();

        if (cursor.moveToFirst()) {
            do {
                //int listID = cursor.getInt(0);
                id = cursor.getInt(0);  // listItem = cursor.getString(1);
                //String name = cursor.getString(1);

//                ShoppingListModel newIdList = new ShoppingListModel(id, null);
//                returnList.add(newIdList);

                //ArrayList listNames = new ShoppingListModel(listID, listName);
                //listID.add(newList);

            } while (cursor.moveToNext());

        } else {
            //failure - do nothing
        }
        cursor.close();
        db.close();
        //Collections.reverse(listItems); //reverse lists to show the latest first in ListView
        return id;  //return shoppingListModel;        //return getlistID(listName);

    }



    public int getLastSavedlistID() {
        SQLiteDatabase db = this.getReadableDatabase();
        //String queryString = "SELECT " + COLUMN_LIST_NAME + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
        //String queryListID = "SELECT * FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;

        String queryLastListID = "SELECT  * FROM " + SHOPPINGLIST_TABLE;     // FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + "'" + listName + "'";     //String queryListID = "SELECT LIST_ID FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
        //int listID; //List<ShoppingListModel> listID = new ArrayList<>();

        Cursor cursor = db.rawQuery(queryLastListID, null);
        cursor.moveToLast();

        int listID = cursor.getInt(0);
        return listID;

        //int id = 0;
        //ShoppingListModel shoppingListModel = new ShoppingListModel();

//        if (cursor.moveToFirst()) {
//            do {
//                //int listID = cursor.getInt(0);
//                id = cursor.getInt(0);  // listItem = cursor.getString(1);
//                //String name = cursor.getString(1);
//
////                ShoppingListModel newIdList = new ShoppingListModel(id, null);
////                returnList.add(newIdList);
//
//                //ArrayList listNames = new ShoppingListModel(listID, listName);
//                //listID.add(newList);
//
//            } while (cursor.moveToNext());
//
//        } else {
//            //failure - do nothing
//        }
//        cursor.close();
//        db.close();
//        //Collections.reverse(listItems); //reverse lists to show the latest first in ListView
//        return id;  //return shoppingListModel;        //return getlistID(listName);

    }
    public String getLastSavedlistName(int listID) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String queryString = "SELECT " + COLUMN_LIST_NAME + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
        //String queryListID = "SELECT * FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
        //String queryLastListID = "SELECT  * FROM " + SHOPPINGLIST_TABLE;     // FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + "'" + listName + "'";     //String queryListID = "SELECT LIST_ID FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
        String queryLastListName = "SELECT " + COLUMN_LIST_NAME + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + listID;

        //int listID; //List<ShoppingListModel> listID = new ArrayList<>();

        Cursor cursor = db.rawQuery(queryLastListName, null);
        //cursor.moveToLast();

//        String listName = cursor.getString(0);
//        return listName;
        String listName = "";

        if (cursor.moveToFirst()) {
            do {
                //int listID = cursor.getInt(0);
                listName = cursor.getString(0);  // listItem = cursor.getString(1);
                //String name = cursor.getString(1);

//                ShoppingListModel newIdList = new ShoppingListModel(id, null);
//                returnList.add(newIdList);

                //ArrayList listNames = new ShoppingListModel(listID, listName);
                //listID.add(newList);

            } while (cursor.moveToNext());

        } else {
            //failure - do nothing
        }
        cursor.close();
        db.close();
        //Collections.reverse(listItems); //reverse lists to show the latest first in ListView
        return listName;  //return shoppingListModel;        //return getlistID(listName);

    }

        //int id = 0;
        //ShoppingListModel shoppingListModel = new ShoppingListModel();

//        if (cursor.moveToFirst()) {
//            do {
//                //int listID = cursor.getInt(0);
//                id = cursor.getInt(0);  // listItem = cursor.getString(1);
//                //String name = cursor.getString(1);
//
////                ShoppingListModel newIdList = new ShoppingListModel(id, null);
////                returnList.add(newIdList);
//
//                //ArrayList listNames = new ShoppingListModel(listID, listName);
//                //listID.add(newList);
//
//            } while (cursor.moveToNext());
//
//        } else {
//            //failure - do nothing
//        }
//        cursor.close();
//        db.close();
//        //Collections.reverse(listItems); //reverse lists to show the latest first in ListView
//        return id;  //return shoppingListModel;        //return getlistID(listName);




    //List<ShoppingListModel> itemList = new ArrayList<>();



//    public List<ShoppingListModel> getlistID(String listName) {
//        List<ShoppingListModel> returnList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        //String queryString = "SELECT " + COLUMN_LIST_NAME + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
//        //String queryListID = "SELECT * FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
//
//        String queryListID = "SELECT LIST_ID FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + "'" + listName + "'";     //String queryListID = "SELECT LIST_ID FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
//                                                                                              //  " = " + "'" + listName + "'";
//
//        //int listID; //List<ShoppingListModel> listID = new ArrayList<>();
//        Cursor cursor = db.rawQuery(queryListID, null);
//        //int id = 0;
//        //ShoppingListModel shoppingListModel = new ShoppingListModel();
//
//        if (cursor.moveToFirst()) {
//            do {
//                //int listID = cursor.getInt(0);
//                int id = cursor.getInt(0);  // listItem = cursor.getString(1);
//                //String name = cursor.getString(1);
//
//                ShoppingListModel newIdList = new ShoppingListModel(id, null);
//                returnList.add(newIdList);
//
//                //ArrayList listNames = new ShoppingListModel(listID, listName);
//                //listID.add(newList);
//
//            } while (cursor.moveToNext());
//
//        } else {
//            //failure - do nothing
//        }
//        cursor.close();
//        db.close();
//        //Collections.reverse(listItems); //reverse lists to show the latest first in ListView
//        return returnList;  //return shoppingListModel;        //return getlistID(listName);
//
//    }



//    public int getlistID(String listName) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        //String queryString = "SELECT " + COLUMN_LIST_NAME + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
//        //String queryListID = "SELECT * FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
//        String queryListID = "SELECT LIST_ID FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
//        //int listID; //List<ShoppingListModel> listID = new ArrayList<>();
//        Cursor cursor = db.rawQuery(queryListID, null);
//        int id = 0;
//        //ShoppingListModel shoppingListModel = new ShoppingListModel();
//
//        if (cursor.moveToFirst()) {
//            do {
//                //int listID = cursor.getInt(0);
//                id = cursor.getInt(0);  // listItem = cursor.getString(1);
//                //String name = cursor.getString(1);
//
//                //ShoppingListModel newList = new ShoppingListModel(id);
//
//                //ArrayList listNames = new ShoppingListModel(listID, listName);
//                //listID.add(newList);
//
//            } while (cursor.moveToNext());
//
//        } else {
//            //failure - do nothing
//        }
//        cursor.close();
//        db.close();
//        //Collections.reverse(listItems); //reverse lists to show the latest first in ListView
//        return id;  //return shoppingListModel;        //return getlistID(listName);
//
//    }




    public List<String> getShoppingListItems(int listID){
        SQLiteDatabase db = this.getReadableDatabase();
        //String queryString = "SELECT " + COLUMN_LIST_NAME + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
        //String queryListItems = "SELECT * FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " + "'" + listID + "'";   //String queryListItems = "SELECT * FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " + listID;
        String queryListItems = "SELECT * FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " + listID;   //String queryListItems = "SELECT * FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " + listID;
                                                                                      //  " = " + "'" + listName + "'";
        List<String> listItems = new ArrayList<>();
        Cursor cursor = db.rawQuery(queryListItems, null);

        if(cursor.moveToFirst()){
            do{
                //int listID = cursor.getInt(0);
                String listItem = cursor.getString(1);

                //ArrayList listNames = new ShoppingListModel(listID, listName);
                listItems.add(listItem);

            } while (cursor.moveToNext());

        }
        else{
            //failure - do nothing
        }
        cursor.close();
        db.close();
        Collections.reverse(listItems); //reverse lists to show the latest first in ListView
        return listItems;
    }




//    public List<String> getShoppingListItems(ListItemsModel listID){
//        List<String> listItems = new ArrayList<>();
//        List<ListItemsModel> returnItemsList = new ArrayList<>();
//
//        //String queryString = "SELECT " + COLUMN_LIST_NAME + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
//        String queryListItems = "SELECT * FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " + listID;
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(queryListItems, null);
//
//        if(cursor.moveToFirst()){
//            do{
//                int id = cursor.getInt(0);
//                String listItem = cursor.getString(1);
//                //int theListID = cursor.getInt(2);
//
//                //listItems = new ShoppingListModel(id, listItem);
//                listItems.add(listItem);
//                //ListItemsModel itemsModel = new ListItemsModel(id, listItem, listID);
//                //returnItemsList.add(itemsModel);
//
//            } while (cursor.moveToNext());
//
//        }
//        else{
//            //failure - do nothing
//        }
//        cursor.close();
//        db.close();
//        Collections.reverse(returnItemsList); //reverse lists to show the latest first in ListView
//        return listItems;
//    }


//    public ListItemsModel getShoppingListItems(ListItemsModel listItemsModel){
//        SQLiteDatabase db = this.getReadableDatabase();
//        //String queryString = "SELECT " + COLUMN_LIST_NAME + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
//        String queryString = "SELECT * FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " + listItemsModel.getId();
//        Cursor cursor = db.rawQuery(queryString, null);
//
//        ListItemsModel foundListItems = new ListItemsModel();
//        if(cursor.moveToFirst()){
//
//            do{
//                int id = cursor.getInt(0);
//                String item = cursor.getString(1);
//                int listID = cursor.getInt(2);
//                //int age = cursor.getInt(2);
//                //boolean active = cursor.getInt(3) == 1 ? true:false;
//
//                foundListItems = new ListItemsModel(id, item, listID);
//
//            }while(cursor.moveToNext());
//
//            return foundListItems;
//        }
//        return foundListItems;
//    }





    public ShoppingListModel showListInfo(ShoppingListModel shoppingListModel){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        ShoppingListModel foundList = new ShoppingListModel();
        if(cursor.moveToFirst()){

            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                //int age = cursor.getInt(2);
                //boolean active = cursor.getInt(3) == 1 ? true:false;

                foundList = new ShoppingListModel(id, name);

            }while(cursor.moveToNext());

            return foundList;
        }
        return foundList;
    }




    public List<String> getItems(int listID){
        List<String> listItems = new ArrayList<>();

        String queryGetAllListItems = "SELECT * FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " +  listID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryGetAllListItems, null);

        if(cursor.moveToFirst()){
            do{
                //int listID = cursor.getInt(0);
                String listItem = cursor.getString(1);

                //ArrayList listNames = new ShoppingListModel(listID, listName);
                listItems.add(listItem);

            } while (cursor.moveToNext());

        }
        else{
            //failure - do nothing
        }
        cursor.close();
        db.close();
        //Collections.reverse(listItems); //reverse lists to show the latest first in ListView
        return listItems;
    }





    //get active list's items from database
    public List<String> getThisListItems(int listID){
        List<String> listItems = new ArrayList<>();


        String queryGetAllListItems = "SELECT * FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " +  listID;
        //String queryGetAllListItems = "SELECT * FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " +  listID + " AND " + COLUMN_ITEM_NAME + " = " + "'" + itemName + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryGetAllListItems, null);

        if(cursor.moveToFirst()){
            do{
                //int listID = cursor.getInt(0);
                String listItem = cursor.getString(1);

                //ArrayList listNames = new ShoppingListModel(listID, listName);
                listItems.add(listItem);

            } while (cursor.moveToNext());

        }
        else{
            //failure - do nothing
        }
        cursor.close();
        db.close();
        //Collections.reverse(listItems); //reverse lists to show the latest first in ListView
        Collections.reverse(listItems); //reverse lists to show the latest first in ListView
        return listItems;
    }




    public boolean deleteShoppingList(ShoppingListModel shoppingListModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return false;
        }
        else{
            return true;
        }
    }



    //delete all lists but the last created one
    public boolean deleteAllShoppingListsButOne(int listID){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " != " + "'" + listID + "'";
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return false;
        }
        else{
            return true;
        }
    }





//    public boolean setItemAsDone(int itemID){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String queryString = "DELETE FROM " + SHOPPINGLIST_TABLE + " WHERE " + LIST_ID + " = " + shoppingListModel.getId();
//        Cursor cursor = db.rawQuery(queryString, null);
//
//        if(cursor.moveToFirst()){
//            return false;
//        }
//        else{
//            return true;
//        }
//    }


//    //update chosen listItems
//    public void updateCustomer(CustomerModel customerToUpdate){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_ID,customerToUpdate.getId());
//        cv.put(COLUMN_CUSTOMER_NAME,customerToUpdate.getName());
//        cv.put(COLUMN_CUSTOMER_AGE, customerToUpdate.getAge());
//        cv.put(COLUMN_ACTIVE_CUSTOMER, customerToUpdate.isActive());
//
//        db.update(CUSTOMER_TABLE,cv,COLUMN_ID + " = " + customerToUpdate.getId(),null);
//
//    }



    public int getItemIDLastSavedList(int listID, String itemName){
        int itemID = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        //String queryString = "SELECT " + COLUMN_ITEM_ID + " FROM " + ITEMS_TABLE + " WHERE " + COLUMN_ITEM_NAME + " = " +  "'" + itemName + "'";
        String queryString = "SELECT " + COLUMN_ITEM_ID + " FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " + "'" + listID + "'" + " AND " + COLUMN_ITEM_NAME + " = " + "'" + itemName + "'";

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                //int listID = cursor.getInt(0);
                itemID = cursor.getInt(0);

            } while (cursor.moveToNext());

        } else {
            //failure - do nothing
        }
        cursor.close();
        db.close();

        return itemID;



//        if(cursor.moveToFirst()){
//            return 0;
//        }
//        else{
//            return itemID;
//        }

    }



    public void updateItemName(int listID, int itemID, String itemName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_NAME, itemName); //cv.put(COLUMN_CUSTOMER_NAME,customerToUpdate.getName());  //cv.put(COLUMN_CUSTOMER_AGE, customerToUpdate.getAge());   //cv.put(COLUMN_ACTIVE_CUSTOMER, customerToUpdate.isActive());

        //db.update(ITEMS_TABLE, cv,COLUMN_ID + " = " + customerToUpdate.getId(),null);
        //db.update(ITEMS_TABLE, cv,FK_LIST_ID + " = " + listID + " AND " + COLUMN_ITEM_ID + " = " + itemID ,null);
        db.update(ITEMS_TABLE, cv,FK_LIST_ID + " = " + "'" + listID + "'" + " AND " + COLUMN_ITEM_ID + " = " + itemID ,null);

    }



//    public void updateItemName(int listID, int itemID, String itemName){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_ITEM_NAME, itemName);
////        cv.put(COLUMN_CUSTOMER_NAME,customerToUpdate.getName());
////        cv.put(COLUMN_CUSTOMER_AGE, customerToUpdate.getAge());
////        cv.put(COLUMN_ACTIVE_CUSTOMER, customerToUpdate.isActive());
//
//        //db.update(ITEMS_TABLE, cv,COLUMN_ID + " = " + customerToUpdate.getId(),null);
//        db.update(ITEMS_TABLE, cv,FK_LIST_ID + " = " + listID + " AND " + COLUMN_ITEM_ID + " = " + itemID ,null);
//
//    }





    public int getItemID(String itemName){
        int itemID = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        //String queryString = "SELECT " + COLUMN_ITEM_ID + " FROM " + ITEMS_TABLE + " WHERE " + COLUMN_ITEM_NAME + " = " +  "'" + itemName + "'";
        String queryString = "SELECT " + COLUMN_ITEM_ID + " FROM " + ITEMS_TABLE + " WHERE " + COLUMN_ITEM_NAME + " = " +  "'" + itemName + "'";

        //String queryStringFromDeleteOneItem = "DELETE FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " + "'" + listID + "'" + " AND " + COLUMN_ITEM_NAME + " = " + "'" + itemName + "'";
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return 0;
        }
        else{
            return itemID;
        }
    }

//    public int getItemID(String itemName){
//        int itemID = 0;
//        SQLiteDatabase db = this.getReadableDatabase();
//        String queryString = "SELECT " + COLUMN_ITEM_ID + " FROM " + ITEMS_TABLE + " WHERE " + COLUMN_ITEM_NAME + " = " +  "'" + itemName + "'";
//        Cursor cursor = db.rawQuery(queryString, null);
//
//        if(cursor.moveToFirst()){
//            return 0;
//        }
//        else{
//            return itemID;
//        }
//    }



    public boolean deleteOneItem(int listID, String itemName){
        SQLiteDatabase db = this.getWritableDatabase();
        //String queryString = "DELETE FROM " + ITEMS_TABLE + " WHERE " + COLUMN_ITEM_NAME + " = " + "'" + itemName + "'";
        String queryString = "DELETE FROM " + ITEMS_TABLE + " WHERE " + FK_LIST_ID + " = " + "'" + listID + "'" + " AND " + COLUMN_ITEM_NAME + " = " + "'" + itemName + "'";
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return false;
        }
        else{
            return true;
        }
    }








//    public boolean deleteOneItem(ListItemsModel listItemsModel){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String queryString = "DELETE FROM " + ITEMS_TABLE + " WHERE " + COLUMN_ITEM_ID + " = " + listItemsModel.getId();
//        Cursor cursor = db.rawQuery(queryString, null);
//
//        if(cursor.moveToFirst()){
//            return false;
//        }
//        else{
//            return true;
//        }
//    }



//    public boolean deleteOneItem(ListItemsModel listItemsModel){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String queryString = "DELETE FROM " + ITEMS_TABLE + " WHERE " + COLUMN_ITEM_ID + " = " + listItemsModel.getId();
//        Cursor cursor = db.rawQuery(queryString, null);
//
//        if(cursor.moveToFirst()){
//            return false;
//        }
//        else{
//            return true;
//        }
//    }


//    public boolean deleteOneItem(long id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String queryString = "DELETE FROM " + ITEMS_TABLE + " WHERE " + COLUMN_ITEM_ID + " = " + id;
//        Cursor cursor = db.rawQuery(queryString, null);
//
//        if(cursor.moveToFirst()){
//            return false;
//        }
//        else{
//            return true;
//        }
//    }




    public List<ShoppingListModel> findListID(){

        List<ShoppingListModel> returnList = new ArrayList<>();

        String queryIntID = "SELECT * FROM " + SHOPPINGLIST_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery(queryIntID, null);

        if(cursor.moveToFirst()){
            do{
                int listID = cursor.getInt(0);
                String listName = cursor.getString(1);

                ShoppingListModel tempList = new ShoppingListModel(listID, listName);
                returnList.add(tempList);

            } while (cursor.moveToNext());

            }

        else{
            //failure - do nothing
        }

        cursor.close();
        db.close();
        return returnList;

    }




}



//        if(cursor.moveToFirst()){
//            //loop through cursor result, create new object, put it into returnList
//
//            do{
//                String listID = cursor.getString(0);  // String position in table: 0
//
//
//                //CustomerModel newCustomer = new CustomerModel(customerName);
//                //returnList.add(newCustomer);
//
//            }while (cursor.moveToNext());
//        }
//        else{
//            //if it fails, don't add anything to list
//        }



//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        else{
//            return true;
//        }



//        int id = cursor.getInt(0);
//        cursor.moveToLast();

//        if(cursor.moveToFirst()){
//            return false;
//        }
//        else{
//            return true;
//        }

//------------------------------------------------------------------------------------------------------







//        String selectQuery = "SELECT  * FROM " + "SHOPPING_LIST_TABLE";
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        cursor.moveToLast();





//-----------------------------------------------------------------------------------------------------------------------------------
//Listname
//        cv.put(COLUMN_LIST_NAME, listName);
//        long insert = db.insert(SHOPPINGLIST_TABLE, null, cv);
//        cv.clear();



//        cv.put(COLUMN_LIST_NAME, listName);
//        long insert = db.insert(SHOPPINGLIST_TABLE, null, cv);
//        cv.clear();

//cv.put(COLUMN_LIST_ID, id);
//long insert = db.insert(ITEMS_TABLE, null, cv);
//cv.clear();

//String currentListId = "SELECT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE + " WHERE COLUMN_LIST_NAME = `" + listName + "`";
//String currentListId = "SELECT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
//Cursor cursor = db.rawQuery(currentListId, null);


//SQLiteDatabase db = this.getWritableDatabase();
//db.execSQL("PRAGMA foreign_keys=ON;");
//ContentValues cv = new ContentValues();



//        cv.put(COLUMN_ITEM_NAME, itemToAdd);
//        //cv.put(COLUMN_LIST_ID, insert); //cv.put(COLUMN_LIST_ID, insert);
//        cv.put(COLUMN_LIST_ID, String.valueOf(cursor));
//        long ok = db.insert(ITEMS_TABLE, null, cv);
//        //long insert = db.insert(ITEMS_TABLE, null, cv);
//
//
//        if(ok == -1){
//            db.close();
//            return false;
//        }
//        else{
//            db.close();
//            return true;
//        }


//        if(insert == -1){
//            db.close();
//            return false;
//        }
//        else{
//            db.close();
//            return true;
//        }






//        String currentListId = "SELECT COLUMN_ID " + " FROM " + SHOPPINGLIST_TABLE  + " WHERE " + COLUMN_LIST_NAME + "=" + listName;

//        String currentListId = "SELECT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE  + " WHERE " + COLUMN_LIST_NAME + " = " + listName;

//        String currentListId = "SELECT DISTINCT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE  + " WHERE " + COLUMN_LIST_NAME + " = " + listName;

//String currentListId = "SELECT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE  + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
//String queryString = "SELECT * FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_ID + " = " + customerModel.getId();
//String currentListId = "SELECT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE  + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
//String currentListId = "SELECT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE  + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
//String currentListId = "SELECT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;

//String currentListId = "SELECT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_LIST_NAME + " = " + listName;
//String currentListId = "SELECT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE + " WHERE COLUMN_LIST_NAME = '" + listName + "'";


//db.execSQL(currentListId);
//Cursor cursor = db.rawQuery(currentListId, null);


//        cv.put(COLUMN_ITEM_NAME, itemToAdd);
//        cv.put(COLUMN_LIST_ID, 10);
////        cv.put(COLUMN_LIST_ID, String.valueOf(cursor));
//        long insert = db.insert(ITEMS_TABLE, null, cv);
//
//
//        if(insert == -1){
//            db.close();
//            return false;
//        }
//        else{
//            db.close();
//            return true;
//        }
//
//    }


//String currentListId = SELECT DISTINCT COLUMN_ID FROM SHOPPINGLIST_TABLE WHERE COLUMN_LIST_NAME = listName;



//        ShoppingListModel foundList = new ShoppingListModel();
//        if(cursor.moveToFirst()){
//
//            do{
//                int id = cursor.getInt(0);
//                String name = cursor.getString(1);
//                //int age = cursor.getInt(2);
//                //boolean active = cursor.getInt(3) == 1 ? true:false;
//
//                foundList = new ShoppingListModel(id, name);
//
//            }while(cursor.moveToNext());
//
//            return foundList;
//        }
//        return foundList;
//    }



//        String gotId;   //ListItemsModel foundListID = new ListItemsModel();
//        if(cursorID.moveToFirst()){
//
//            do{
//                int id = cursorID.getInt(0);
//                String item = cursorID.getString(1);
//                int listId = cursorID.getInt(2);
//
//                foundListID = new ListItemsModel(id, item, listId);
//
//            }while(cursorID.moveToNext());
//
//            //return foundListID;
//        }



//        String qry3 = "SELECT * FROM " + TABLE_RESULT + " WHERE " + TOP_ID + " = " + "1" + " AND " + USER_NAME + " = " + user;
//                          String qry3 = "SELECT * FROM " + TABLE_RESULT + " WHERE " + TOP_ID + "=" + "1" + " AND " + USER_NAME + "='" + user +"'";


//db.execSQL(currentListId);

//SQLiteDatabase dbListId = this.getReadableDatabase();
//String currentListId = ("SELECT COLUMN_ID FROM " + SHOPPINGLIST_TABLE  + " WHERE " + COLUMN_LIST_NAME + " = " + listName);

//       String queryCurrentListId = "SELECT COLUMN_ID FROM " + SHOPPINGLIST_TABLE  + " WHERE " + COLUMN_LIST_NAME + " = " + shoppingListModel.getListName();
//
//        Cursor c = db.rawQuery(queryCurrentListId);
//        String name = c.getString(c.getColumnIndex("NAME"));
//        String place = c.getString(c.getColumnIndex("PLACE"));



//        String queryCurrentListId = "SELECT COLUMN_ID FROM SHOPPINGLIST_TABLE WHERE + COLUMN_LIST_NAME = listName";
//        Cursor cursor = db.rawQuery(queryCurrentListId, null);




//        String query = "SELECT * FROM SHOPPINGLIST_TABLE WHERE Col_Name='name'";
//        Cursor cursor = db.rawQuery(query, null);



//String idFromList = SELECT COLUMN_ID FROM SHOPPINGLIST_TABLE WHERE COLUMN_LIST_NAME = listName;


//String queryString = "DELETE FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_ID + " = " + customerModel.getId();
//String queryNameString =  "SELECT CUSTOMER_NAME FROM " + CUSTOMER_TABLE;


//        //item in the list
//        cv.put(COLUMN_ITEM_NAME, itemToAdd);
//        //cv.put(COLUMN_ITEM_NAME, foundListID.getItem());
//        //cv.put(COLUMN_LIST_ID, foundListID.getItem());
//        long insert = db.insert(ITEMS_TABLE, null, cv);


//item in the list
//        cv.put(COLUMN_ITEM_NAME, itemToAdd);
//        cv.put(COLUMN_LIST_ID, currentListId);
//        long insert = db.insert(ITEMS_TABLE, null, cv);








//String currentListId = "SELECT COLUMN_ID " + " FROM " + SHOPPINGLIST_TABLE  + " WHERE " + COLUMN_LIST_NAME + " = " + listName;   //String currentListId = "SELECT COLUMN_ID " + "FROM SHOPPINGLIST_TABLE " + "WHERE COLUMN_LIST_NAME = " + listName;
//Cursor cursorListID = db.rawQuery(currentListId, null);



//String queryString = "DELETE FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_ID + " = " + customerModel.getId();
//String currentListId = "SELECT COLUMN_ID " + "FROM SHOPPINGLIST_TABLE " + "WHERE COLUMN_LIST_NAME = " + listName;





//        String queryString = "DELETE FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_ID + " = " + customerModel.getId();
//        Cursor cursor = db.rawQuery(queryString, null);



//        if(cursorListID.moveToFirst()){
//            //loop through cursor result, create new object, put it into returnList
//
//            do{
//                String listId = cursorListID.getString(1);  // String position in table: 1
//
//                //CustomerModel newCustomer = new CustomerModel(customerName);
//                //returnList.add(newCustomer);
//
//            }while (cursorListID.moveToNext());
//        }
//        else{
//            //if it fails, don't add anything to list
//        }


//String currentListId = "SELECT COLUMN_ID " + "FROM SHOPPINGLIST_TABLE " + "WHERE COLUMN_LIST_NAME = " + listName;

//Cursor currentListId = db.rawQuery("SELECT COLUMN_ID FROM " + SHOPPINGLIST_TABLE + " WHERE `" + COLUMN_LIST_NAME + "`=" + listName, null);
//Cursor currentListId = db.rawQuery("SELECT COLUMN_ID FROM SHOPPINGLIST_TABLE where COLUMN_LIST_NAME = (listName)");


//Cursor currentListId = db.rawQuery("SELECT COLUMN_ID FROM " + SHOPPINGLIST_TABLE + " WHERE `" + COLUMN_LIST_NAME + "`=" + listName, null);

//String queryNameString =  "SELECT CUSTOMER_NAME FROM " + CUSTOMER_TABLE;

//Cursor currentListId = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE + " WHERE `" + COLUMN_LIST_NAME + "`=" + listName, null);
//Cursor currentListId = db.rawQuery("SELECT " +COLUMN_ID + " FROM " + SHOPPINGLIST_TABLE + " WHERE `" + COLUMN_LIST_NAME + "`=" + listName, null);

//String areaTyp = "SELECT " +AREA_TYPE + "  FROM " + AREA_TYPE_TABLE + " where `" + TYPE + "`=" + id;

//String createShoppinglistTableStatement = "CREATE TABLE " + SHOPPINGLIST_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_LIST_NAME + " TEXT)";


//        //item in the list
//        cv.put(COLUMN_ITEM_NAME, itemToAdd);
//        //cv.put(COLUMN_LIST_ID, String.valueOf(cursorListID));
//        long insert = db.insert(ITEMS_TABLE, null, cv);



//cv.put(COLUMN_LIST_ID, shoppingListModel.getId());  //id from Shoppinglist as FOREIGN KEY in Listitem table

//        String listNameToCreate = "INSERT INTO " + ITEMS_TABLE  + " (" + COLUMN_ITEM_NAME + " ) VALUES " + (itemToAdd);
//        db.execSQL(listNameToCreate);

//        String createShoppinglistTableStatement = "CREATE TABLE " + SHOPPINGLIST_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_LIST_NAME + " TEXT)";



//        if(insert == -1){
//            db.close();
//            return false;
//        }
//        else{
//            db.close();
//            return true;
//        }
//
//    }




//    public boolean addItem(ShoppingListModel shoppingListModel, ListItemsModel listItemsModel){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        //item in the list
//        cv.put(COLUMN_ITEM_NAME, listItemsModel.getItem());
//        cv.put(COLUMN_LIST_ID, shoppingListModel.getId());  //id from Shoppinglist as FOREIGN KEY in Listitem table
//
//        long insert = db.insert(ITEMS_TABLE, null, cv);
//
//        if(insert == -1){
//            return false;
//        }
//        else{
//            db.close();
//            return true;
//        }
//
//    }







//    public int findListID(){
//
//        String listName = tv_listName.getText().toString();
//        int listNameID = SELECT COLUMN_ID FROM     int listNameID = SELECT ID FROM tv_listName.getText().toString();
//
//    }








//    public int findShoppingListID(){

//        SQLiteDatabase db = this.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//
//        int shopListID = SELECT ID FROM

        //int shoppingListID = "SELECT " + " COLUMN_ID + " + FROM + " SHOPPINGLIST_TABLE";
        //int wer = SELECT COLUMN_ID FROM SHOPPINGLIST_TABLE;



//        int shoppingListID = SELECT name FROM sqlite_master
//        WHERE type='table';
//
//        int wer = SELECT COLUMN_ID FROM SHOPPINGLIST_TABLE;

//    }













//    public boolean addShoppingListNameAndItem(ShoppingListModel shoppingListModel, ListItemsModel listItemsModel){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv_ShoppingList = new ContentValues();
//        ContentValues cv_Items = new ContentValues();
//
//        //Listname
//        cv_ShoppingList.put(COLUMN_LIST_NAME, shoppingListModel.getListName());
//
//        //item in the list
//        cv_Items.put(COLUMN_ITEM_NAME, listItemsModel.getItem());
//        cv_Items.put(COLUMN_LIST_ID, shoppingListModel.getId());  //id from Shopplist as FOREIGN KEY in Listitem table
//
//
//        long insert1 = db.insert(SHOPPINGLIST_TABLE, null, cv_ShoppingList);
//        long insert2 = db.insert(ITEMS_TABLE, null, cv_Items);
//
//        if(insert1 == -1 || insert2 == -1){
//            return false;
//        }
//        else{
//            return true;
//        }
//
//
//    }






//    public boolean createShoppingList(ShoppingListModel shoppingListModel){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        //Listname
//        cv.put(COLUMN_LIST_NAME, shoppingListModel.getListName());
//        long insert = db.insert(SHOPPINGLIST_TABLE, null, cv);
//
//        if(insert == -1){
//            return false;
//        }
//        else{
//            db.close();
//            return true;
//        }
//
//
//    }
//
//
//    public boolean addItem(ShoppingListModel shoppingListModel, ListItemsModel listItemsModel){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        //item in the list
//        cv.put(COLUMN_ITEM_NAME, listItemsModel.getItem());
//        cv.put(COLUMN_LIST_ID, shoppingListModel.getId());  //id from Shoppinglist as FOREIGN KEY in Listitem table
//
//        long insert = db.insert(ITEMS_TABLE, null, cv);
//
//        if(insert == -1){
//            return false;
//        }
//        else{
//            db.close();
//            return true;
//        }
//
//    }





























