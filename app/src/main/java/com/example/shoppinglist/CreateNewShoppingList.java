package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateNewShoppingList extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_listName;
    private EditText et_itemToAdd;
    private ListView lv_itemList;
    private Button btn_saveToList;
    private Button btn_saveAsList;
    //private Button btn_updateList;
    private ArrayAdapter itemsAdapter;
    private DataBaseHelper dbHelper;
    private Object AdapterView;
    private static final String MY_PREF = "SHAREDPREF";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_shopping_list);

        tv_listName = findViewById(R.id.tv_listName);
        et_itemToAdd = findViewById(R.id.et_itemToAdd);
        lv_itemList = findViewById(R.id.lv_itemList);

        btn_saveAsList = findViewById(R.id.btn_saveAsList);
            btn_saveAsList.setOnClickListener((View.OnClickListener) this);

        btn_saveToList = findViewById(R.id.btn_saveToList);
            btn_saveToList.setOnClickListener((View.OnClickListener) this);

//        btn_updateList = findViewById(R.id.btn_updateList);
//            btn_updateList.setOnClickListener((View.OnClickListener) this);


        registerForContextMenu(lv_itemList);   //register context menu to listView
        dbHelper = new DataBaseHelper(CreateNewShoppingList.this);


//        //Create list of all lists on create (get lists as ShoppingListModel (ID + name))
        List<ListItemsModel> listItems = dbHelper.getAllItemLists();
        itemsAdapter = new ArrayAdapter<ListItemsModel>(CreateNewShoppingList.this, android.R.layout.simple_list_item_1, listItems);

        //et_itemToAdd.requestFocus();

//        //Show keyboard FORCED
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(et_itemToAdd, InputMethodManager.SHOW_IMPLICIT);



//        List<ListItemsModel> listItems = dbHelper.getThisListItems(int listID, String itemName);
//        itemsAdapter = new ArrayAdapter<ListItemsModel>(CreateNewShoppingList.this, android.R.layout.simple_list_item_1, listItems);



//        updateItemsList();
//
//        //db.getAllSavedListsAsModel();
//        updateItemsList();


        //loadData();

        //restore state, from landscape mode
        if(savedInstanceState != null) {
            String savedStateText = savedInstanceState.getString("listName");
            String savedStateText2 = savedInstanceState.getString("itemToAdd");
            tv_listName.setText(savedStateText);
            et_itemToAdd.setText(savedStateText2);

        }


    }


    //save state, for landscape mode
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putString("listName", tv_listName.getText().toString());
            outState.putString("itemToAdd", et_itemToAdd.getText().toString());

    }



    //update listView with fetched Items
    private void updateItemsList() {
        //itemsAdapter = new ArrayAdapter<ListItemsModel>(this, android.R.layout.simple_list_item_1, dbHelper.getThisListItems(listID, itemName)); ///////////////////////////////////////////////
        lv_itemList.setAdapter(itemsAdapter);
    }

    private void updateThisListItems(List<String> itemsList){
        //itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dbHelper.getThisListItems(itemsList));
        itemsAdapter = new ArrayAdapter<String>(CreateNewShoppingList.this, android.R.layout.simple_list_item_1, itemsList);
        lv_itemList.setAdapter(itemsAdapter);
    }


// ShoppingListActivity -------------------------------------------------------------------------------------------------


    //get all items and show in listview
//    private void showItemsOnListView() {
//        //itemsAdapter = new ArrayAdapter<ListItemsModel>(this, android.R.layout.simple_list_item_1, dbHelper.getShoppingListItems(id));
//        itemsAdapter = new ArrayAdapter<ListItemsModel>(this, android.R.layout.simple_list_item_1, dbHelper.getShoppingListItems(id));
//        //itemsAdapter = new ArrayAdapter<ListItemsModel>(this, android.R.layout.simple_list_item_1, dbHelper.getShoppingListItems(listID));
//        //itemsAdapter = new ArrayAdapter<ListItemsModel>(this, android.R.layout.simple_list_item_1, dbHelper.getShoppingListItems());
//        lv_itemList.setAdapter(itemsAdapter);
//    }

//    private void showCustomersOnListView() {
//        customerArrayAdapter = new ArrayAdapter<CustomerModel>(this, android.R.layout.simple_list_item_1, dataBaseHelper.getAllCustomers());
//        lv_CustomerList.setAdapter(customerArrayAdapter);
//    }



    //assign action for each clickable button in Activity
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_saveAsList:

                //Create the ShoppingList table in database

                //String currentDate = new SimpleDateFormat("yy-MM-dd", Locale.getDefault()).format(new Date());
                //String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                //String nameTime = "List " + currentDate + ", " + currentTime; //String nameTime = "Handla " + currentDate + ", " + currentTime;

                //String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                //String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
//                String nameTime = currentDate + ", " + currentTime; //String nameTime = currentDate + ", " + currentTime;
//                tv_listName.setText(nameTime);

//---------------------------------------------------------------------------
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                String tempDate = currentDate;
                LocalDate date = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    date = LocalDate.parse(tempDate);
                }
                DayOfWeek day = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    day = date.getDayOfWeek();
                }

                //get weekday from date
                String shortDay = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    switch (day) {
                        case MONDAY:
                            shortDay = "Mon";
                            break;
                        case TUESDAY:
                            shortDay = "Tue";
                            break;
                        case WEDNESDAY:
                            shortDay = "Wed";
                            break;
                        case THURSDAY:
                            shortDay = "Thu";
                            break;
                        case FRIDAY:
                            shortDay = "Fri";
                            break;
                        case SATURDAY:
                            shortDay = "Sat";
                            break;
                        case SUNDAY:
                            shortDay = "Sun";
                            break;
                    }
                }
                String nameTime = shortDay + " " + currentDate + ", " + currentTime; //String nameTime = currentDate + ", " + currentTime;
                tv_listName.setText(nameTime);
//---------------------------------------------------------------------------


                DataBaseHelper dataBaseHelper = new DataBaseHelper(CreateNewShoppingList.this);

                boolean success = dataBaseHelper.createShoppingList(tv_listName.getText().toString());

                Toast.makeText(CreateNewShoppingList.this, "Shoppinglist created", Toast.LENGTH_SHORT).show();
                btn_saveAsList.setClickable(false); //deactivate button after saving as list
                et_itemToAdd.requestFocus();    //set cursor in view

                //Show keyboard FORCED
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


                break;


        case R.id.btn_saveToList:

        ShoppingListModel shoppingListModel;
        ListItemsModel listItemsModel;

        String listName = tv_listName.getText().toString();
        String itemToAdd = et_itemToAdd.getText().toString();




        //make sure a list is saved before adding items
        //if(!(listName =="") && !(itemToAdd=="")) {

        if(listName.length()>0) {

                    if(itemToAdd.length()>0) {
                        DataBaseHelper db = new DataBaseHelper(CreateNewShoppingList.this);

                        //Toast.makeText(this, itemToAdd + " added", Toast.LENGTH_SHORT).show();
                        db.addItem(listName, itemToAdd);
                        int listID = db.getlistID(listName);
                        DataBaseHelper dbUpdate = new DataBaseHelper(CreateNewShoppingList.this);
                        listName = tv_listName.getText().toString();
                        //Toast.makeText(this, "Namn: " + listName, Toast.LENGTH_SHORT).show();
                        int id = dbUpdate.getlistID(listName);  //get id from list with this name

                        //Create list of all lists on create (get lists as ShoppingListModel (ID + name))
                        List<String> allItems = dbHelper.getShoppingListItems(id);
                        itemsAdapter = new ArrayAdapter<String>(CreateNewShoppingList.this, android.R.layout.simple_list_item_1, allItems);

                        updateItemsList();
                        //break;

                        et_itemToAdd.setText("");

                    }
                    else{
                        Toast.makeText(this, "Enter item to add ...", Toast.LENGTH_SHORT).show();
                    }


        }
        else{
            Toast.makeText(this, "Save list before adding items ...", Toast.LENGTH_SHORT).show();
        }

                    break;

        }

    }


    //create Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_create_new_list, menu);

    }

    //Create action to perform on each clicked item in list (context menu)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); //final android.widget.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {

            case R.id.update_item:

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.update_item_dialog,null);
                dialogBuilder.setView(dialogView);

                final TextView dialog_tv_listName;
                final EditText dialog_update_item_name;
                //final Switch dialog_sw_active;
                Button dialog_btn_updateItem;

                dialog_tv_listName = dialogView.findViewById(R.id.tv_listName);
                dialog_update_item_name = dialogView.findViewById(R.id.dialog_update_item_name);
                dialog_btn_updateItem = dialogView.findViewById(R.id.dialog_btn_updateItem);

                //final List<String> itemsList = (CustomerModel) customerArrayAdapter.getItem(info.position);
                //final ListItemsModel listItemsModel = (ListItemsModel) itemsAdapter.getItem(info.position);

                String listNameItem = tv_listName.getText().toString();
                //Toast.makeText(this, "ListName: " + listNameItem, Toast.LENGTH_SHORT).show();


                final int listIDItem = dbHelper.getlistID(listNameItem);  //getlistID from db
                //Toast.makeText(this, "List ID: " + listIDItem, Toast.LENGTH_SHORT).show();


                String itemsName = (String) itemsAdapter.getItem(info.position);
                //Toast.makeText(this, "Items name: " + itemsName, Toast.LENGTH_SHORT).show();

                final int itemsID = dbHelper.getItemIDLastSavedList(listIDItem, itemsName);
                //Toast.makeText(this, "Items ID: " +itemsID, Toast.LENGTH_SHORT).show();

                //-------------------------------------------------------

                dialog_tv_listName.setText(listNameItem);   //set listName
                dialog_update_item_name.setText(itemsName);

                final AlertDialog updateDialog = dialogBuilder.create();

                //updateDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

                updateDialog.show();

                //show keyboard in dialog automatically
                Window window = updateDialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                //set focus after text in view
                dialog_update_item_name.requestFocus();
//                    int pos = dialog_update_item_name.getText().length();
//                    dialog_update_item_name.setSelection(pos);



                dialog_btn_updateItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String updatedName = dialog_update_item_name.getText().toString();

                        //parse age int to string
                        //int ageValue = Integer.parseInt(dialog_edit_age.getText().toString());
                        //tempCustomer.setAge(ageValue);  //age as String
                        //tempCustomer.setActive(dialog_sw_active.isChecked());

                        dbHelper.updateItemName(listIDItem, itemsID, updatedName);
                        Toast.makeText(CreateNewShoppingList.this, "Updated", Toast.LENGTH_SHORT).show();
                        //updateItemsList();

                        List<String> allItems = dbHelper.getShoppingListItems(listIDItem);
                        itemsAdapter = new ArrayAdapter<String>(CreateNewShoppingList.this, android.R.layout.simple_list_item_1, allItems);
                        updateThisListItems(allItems);

                        updateDialog.hide();

                    }
                });

                break;
//--------------------------------------------

            //delete chosen item
            case R.id.delete_item:

                String listName = tv_listName.getText().toString();
                //Toast.makeText(this, "ListName: " + listName, Toast.LENGTH_SHORT).show();


                int listID = dbHelper.getlistID(listName);  //getlistID from db
                //Toast.makeText(this, "List id: " + listID, Toast.LENGTH_SHORT).show();


                //String itemName = (String) itemsAdapter.getItem((int) info.id);
                String itemName = (String) itemsAdapter.getItem(info.position);
                //Toast.makeText(this, "Item name: " + itemName, Toast.LENGTH_SHORT).show();

                int itemID = dbHelper.getItemID(itemName);
                //Toast.makeText(this, "Item id: " + itemID, Toast.LENGTH_SHORT).show();

                boolean status = dbHelper.deleteOneItem(listID, itemName);

                List<String> itemsList = dbHelper.getThisListItems(listID); //List<String> itemsList = dbHelper.getThisListItems(listID);

                //lv_itemList.setAdapter((ListAdapter) itemsList);   //lv_itemList.setAdapter(itemsAdapter);
                updateThisListItems(itemsList);
                Toast.makeText(this, "Item " + itemName + " deleted", Toast.LENGTH_SHORT).show();
                break;


        }

        return super.onContextItemSelected(item);

    }





//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        SharedPreferences sh = getSharedPreferences(MY_PREF, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sh.edit();
//
//        editor.putString("listName", tv_listName.getText().toString());
//        editor.putString("itemToAdd", String.valueOf(et_itemToAdd.getText()));
//        editor.apply();
//    }


//    public void loadData(){
//        SharedPreferences sh = getSharedPreferences(MY_PREF, MODE_PRIVATE);
//        String savedStateName = sh.getString("listName","");
//        tv_listName.setText(savedStateName);
//        //Toast.makeText(this, "GOT: " + savedStateName, Toast.LENGTH_SHORT).show();
//
//        String savedStateAge = sh.getString("itemToAdd","");
//        et_itemToAdd.setText(savedStateAge);
//
//
//    }




}




