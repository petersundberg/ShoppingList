package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SavedShoppingLists extends AppCompatActivity implements View.OnClickListener {

    private Button btn_deleteAllLists;
    private TextView tv_savedLists;
    private ListView lv_savedLists;
    private ArrayAdapter listArrayAdapter;
    private DataBaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_shopping_lists);

        btn_deleteAllLists = findViewById(R.id.btn_deleteAllLists);
            btn_deleteAllLists.setOnClickListener((View.OnClickListener) this);

        tv_savedLists = findViewById(R.id.tv_savedLists);
        lv_savedLists = findViewById(R.id.lv_savedLists);
        registerForContextMenu(lv_savedLists);   //register context menu to listView

        dbHelper = new DataBaseHelper(SavedShoppingLists.this);
        //DataBaseHelper db = new DataBaseHelper(this);

        //Create list of all lists on create (get lists as ShoppingListModel (ID + name))
        List<ShoppingListModel> allLists = dbHelper.getAllSavedListsAsModel();
        listArrayAdapter = new ArrayAdapter<ShoppingListModel>(SavedShoppingLists.this, android.R.layout.simple_list_item_1, allLists);

        //db.getAllSavedListsAsModel();
        updateListData();

        //get only the name of all saved lists
//        List<String> allSavedListNames = db.getAllSavedLists();
//        listArrayAdapter = new ArrayAdapter<String>(SavedShoppingLists.this, android.R.layout.simple_list_item_1, allSavedListNames);
//
//        db.getAllSavedLists();
//        updateListData();

    }

    //update listView with fetched lists
    private void updateListData() {
        lv_savedLists.setAdapter(listArrayAdapter);
    }


    private void showListsInListView() {
        listArrayAdapter = new ArrayAdapter<ShoppingListModel>(this, android.R.layout.simple_list_item_1, dbHelper.getAllSavedListsAsModel());
        lv_savedLists.setAdapter(listArrayAdapter);
    }

    private void getListItemsToListView() {
        List<String> listItems = new ArrayList<>();

        listArrayAdapter = new ArrayAdapter<ShoppingListModel>(this, android.R.layout.simple_list_item_1, dbHelper.getAllSavedListsAsModel());
        lv_savedLists.setAdapter(listArrayAdapter);
    }





    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_deleteAllLists:

                //delete all ShoppingLists but the last one
                //get list name of last saved list from db
                int listID = dbHelper.getLastSavedlistID(); //first get last saved listID
                Toast.makeText(this, "Old lists deleted", Toast.LENGTH_SHORT).show();
                dbHelper.deleteAllShoppingListsButOne(listID);




                showListsInListView();
                //updateListData();



                //Create the ShoppingList table in database
//                String currentDate = new SimpleDateFormat("yy-MM-dd", Locale.getDefault()).format(new Date());
//                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
//                String nameTime = "List " + currentDate + ", " + currentTime; //String nameTime = "Handla " + currentDate + ", " + currentTime;
//                tv_listName.setText(nameTime);

//                DataBaseHelper dataBaseHelper = new DataBaseHelper(CreateNewShoppingList.this);

//                boolean success = dataBaseHelper.createShoppingList(tv_listName.getText().toString());
//                Toast.makeText(CreateNewShoppingList.this, "Shoppinglist created: " + success, Toast.LENGTH_SHORT).show();

              break;

        }

    }













//-------------------------------------------------------------------------------------------------------------------------------------


    //create Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);

    }

    //Create action to perform on each clicked item in list (context menu)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            case R.id.list_info:
                //Toast.makeText(this, "Pos: " + listArrayAdapter.getItemId(info.position), Toast.LENGTH_SHORT).show();
                ////Toast.makeText(this, "Pos: " + listArrayAdapter.getItemId(info.position), Toast.LENGTH_SHORT).show();

//                ShoppingListModel shoppingListModel = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));
                //ShoppingListModel shoppingListModel = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));

//                Intent itemIntent = new Intent(SavedShoppingLists.this, ListInfoActivity.class);
//                itemIntent.putExtra("id",  -1);  //itemIntent.putExtra("id",  shoppingListModel.getId());
//                itemIntent.putExtra("name",  shoppingListModel.getListName());
//                startActivity(itemIntent);
//              break;

                //long pos = listArrayAdapter.getItemId(info.position);
//                ShoppingListModel shoppingListModel = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));
                //ShoppingListModel shoppingList = dbHelper.showListInfo((ShoppingListModel) listArrayAdapter.getItem(info.position));


                //ShoppingListModel shoppingList = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));


                //ShoppingListModel shoppingList = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));
                ShoppingListModel shoppingList = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));


//                try {ShoppingListModel shoppingList = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));
//
//
//                }
//                catch (IOException e){
//                    e.printStackTrace();
//                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//                }




                //ShoppingListModel getShoppingListItems = dbHelper.getShoppingListItems((ListItemsModel) listArrayAdapter.getItem(info.position));


                //ShoppingListModel shoppingListItems = dbHelper.getShoppingListItems((ListItemsModel) listArrayAdapter.getItem(info.position));



                //ListItemsModel getShoppingListItems = (ListItemsModel) dbHelper.getShoppingListItems((ListItemsModel) listArrayAdapter.getItem(info.position));

                //ListItemsModel getShoppingListItems = dbHelper.getShoppingListItems((ShoppingListModel) listArrayAdapter.getItem(info.position));

                //ListItemsModel listItems = dbHelper.getShoppingListItems((ListItemsModel) listArrayAdapter.getItem(info.position));
                int listID = shoppingList.getId();
                List<String> listItems = dbHelper.getShoppingListItems(listID);


                //int listItemID = shoppingList.getId();
                //List<String> = dbHelper.getShoppingListItems(listID);

                //listArrayAdapter = new ArrayAdapter<ShoppingListModel>(this, android.R.layout.simple_list_item_1, dbHelper.getListItems());
                //boolean status = dbHelper.getListItems((ListItemsModel) listArrayAdapter.getItem(info.position));
                //dbHelper.getShoppingListItems(ListItemsModel, listID);
                //getListItemsToListView();
                //lv_savedLists.setAdapter(listArrayAdapter);

                //ListItemsModel allItems = new ListItemsModel();
                //allItems = dbHelper.getShoppingListItems(listID);
                //listArrayAdapter = new ArrayAdapter<ListItemsModel>(SavedShoppingLists.this, android.R.layout.simple_list_item_1, allItems);




                //ListItemsModel listItems = dbHelper.getShoppingListItems((ListItemsModel) listArrayAdapter.getItem(info.position));


                Intent listInfoIntent = new Intent(SavedShoppingLists.this, ListInfoActivity.class);
                //add id and name of the shoppinglist
                listInfoIntent.putExtra("id",  shoppingList.getId());
                listInfoIntent.putExtra("name",  shoppingList.getListName());

                //add arrayList od items in list
                listInfoIntent.putStringArrayListExtra("listItems", (ArrayList<String>) listItems);
                //listInfoIntent.putExtra("listID", listID);  // Foreign Key ID from ShoppingList Table
                startActivity(listInfoIntent);
                break;


//                Intent listInfoIntent = new Intent(SavedShoppingLists.this, ListInfoActivity.class);
//                //add id and name of the shoppinglist
//                listInfoIntent.putExtra("id",  shoppingList.getId());
//                listInfoIntent.putExtra("name",  shoppingList.getListName());
//
//                //add arrayList od items in list
//                listInfoIntent.putStringArrayListExtra("listItems", (ArrayList<String>) listItems);
//                //listInfoIntent.putExtra("listID", listID);  // Foreign Key ID from ShoppingList Table
//                startActivity(listInfoIntent);
//                break;






//                ShoppingListModel shoppingList = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));
//
//                //List<String> items = dbHelper.getListItems(shoppingList.getId());        // listItems = dbHelper.getListItems((ListItemsModel listItemsModel) listArrayAdapter.getItem(info.position));
//
//                Toast.makeText(this, "Tjohoo", Toast.LENGTH_SHORT).show();
//
////                private void getAllCustomersNameToListView() {
////                listArrayAdapter = new ArrayAdapter<ListItemsModel>(this, android.R.layout.simple_list_item_1, dbHelper.getListItems(shoppingList.getId()));
////                lv_CustomerList.setAdapter(listArrayAdapter);
////        }
//
//
//
//                Intent itemIntent = new Intent(SavedShoppingLists.this, ListInfoActivity.class);
//                itemIntent.putExtra("id",  shoppingList.getId());
//                //itemIntent.putExtra("name",  shoppingList.getListName());
//                //itemIntent.putExtra("items", items);
//                //itemIntent.putExtra("active",  customer.isActive());
//
//                startActivity(itemIntent);
//
//                break;
//--------------------------------------------
            //delete chosen item
            case R.id.delete_list:
////                //delete chosen list
////                boolean status = dbHelper.deleteOneCustomer((CustomerModel) customerArrayAdapter.getItem(info.position));
//                Toast.makeText(SavedShoppingLists.this, "List deleted: ", Toast.LENGTH_SHORT).show();
////                showCustomersOnListView();
//                break;
            boolean status = dbHelper.deleteShoppingList((ShoppingListModel) listArrayAdapter.getItem(info.position));

            String deleteResult;
            if(status){
                deleteResult = "List deleted";
            }
            else{
                deleteResult = "List not deleted!";
            }
            Toast.makeText(SavedShoppingLists.this, deleteResult, Toast.LENGTH_SHORT).show();

            showListsInListView();
            break;

//--------------------------------------------
            //edit chosen item
//            case R.id.update_list:
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//
//                LayoutInflater inflater = getLayoutInflater();
//                final View dialogView = inflater.inflate(R.layout.edit_dialog,null);
//
//                dialogBuilder.setView(dialogView);
//
//                //Declare  variables
//                final EditText dialog_edit_name, dialog_edit_age;
//                final Switch dialog_sw_active;
//                Button dialog_btn_update;
//
//                //initiate Views
//                dialog_edit_name = dialogView.findViewById(R.id.dialog_edit_name);
//                dialog_edit_age = dialogView.findViewById(R.id.dialog_edit_age);
//                dialog_sw_active = dialogView.findViewById(R.id.dialog_sw_active);
//                dialog_btn_update = dialogView.findViewById(R.id.dialog_btn_update);
//
//                final ShoppingListModel tempShoppingList = (ShoppingListModel) listArrayAdapter.getItem(info.position);
//
//                //Setters for current values
//                dialog_edit_name.setText(tempShoppingList.getListName());
//                dialog_edit_age.setText(String.valueOf(tempShoppingList.getAge()));
//
//                //dialog_sw_active.setChecked(tempCustomer.isActive());
//
//                final AlertDialog updateDialog = dialogBuilder.create();
//                updateDialog.show();
//
//                dialog_btn_update.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        //tempShoppingList.setName(dialog_edit_name.getText().toString());
//
//                        //parse age int to string
//                        int ageValue = Integer.parseInt(dialog_edit_age.getText().toString());
//                        tempShoppingList.setAge(ageValue);  //age as String
//                        //tempShoppingList.setActive(dialog_sw_active.isChecked());
//
//                        dbHelper.updateCustomer(tempShoppingList);
//                        Toast.makeText(SavedShoppingLists.this, "Uppdaterad", Toast.LENGTH_SHORT).show();
//                        showCustomersOnListView();
//
//                        updateDialog.hide();
//
//                    }
//                });

        }
        return super.onContextItemSelected(item);

    }






}
