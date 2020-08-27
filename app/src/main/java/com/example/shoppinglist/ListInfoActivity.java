package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListInfoActivity extends AppCompatActivity {

    private Button btn_updateList;
    private TextView tv_listName;
    private EditText et_itemToAdd;
    private ListView lv_itemList;
    private ArrayAdapter listArrayAdapter;
    private DataBaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_info);

        btn_updateList = findViewById(R.id.btn_updateList);
        tv_listName = findViewById(R.id.tv_listName);
        et_itemToAdd = findViewById(R.id.et_itemToAdd);
        lv_itemList = findViewById(R.id.lv_itemList);
        registerForContextMenu(lv_itemList);   //register context menu to listView


        //recieveTextView = findViewById(R.id.recieveTextView);
        Intent itemIntent = getIntent();
        //int id = itemIntent.getIntExtra("id", 0);
        //int id = itemIntent.getIntExtra("id", 0);
        int idInt = itemIntent.getIntExtra("id", 0);
        String name = itemIntent.getStringExtra("name");
        ArrayList<String> listItems = itemIntent.getStringArrayListExtra("listItems");

        tv_listName.setText(name);

        ArrayAdapter itemsAdapter = new ArrayAdapter(ListInfoActivity.this, android.R.layout.simple_list_item_1, listItems);
        lv_itemList.setAdapter(itemsAdapter);

        Toast.makeText(this, "Info: " + name, Toast.LENGTH_SHORT).show();

    }


    //create Context Menu
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        getMenuInflater().inflate(R.menu.context_menu_list_item, menu);
//
//    }
//
//    //Create action to perform on each clicked item in list (context menu)
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch (item.getItemId()) {
//
//            case R.id.item_setDone:
//
//                String doneColor = "#ffffff";
//                int itemID = item.getItemId();
//                //dbHelper.setItemAsDone(itemID);    //List<String> listItems = dbHelper.getShoppingListItems(itemID);
//
//                //lv_itemList(itemID);
//
//
//                //Toast.makeText(this, "Item to setDone ...", Toast.LENGTH_SHORT).show();
//                ////Toast.makeText(this, "Pos: " + listArrayAdapter.getItemId(info.position), Toast.LENGTH_SHORT).show();
//
////                ShoppingListModel shoppingListModel = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));
//                //ShoppingListModel shoppingListModel = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));
//
////                Intent itemIntent = new Intent(SavedShoppingLists.this, ListInfoActivity.class);
////                itemIntent.putExtra("id",  -1);  //itemIntent.putExtra("id",  shoppingListModel.getId());
////                itemIntent.putExtra("name",  shoppingListModel.getListName());
////                startActivity(itemIntent);
////              break;
//
//                //long pos = listArrayAdapter.getItemId(info.position);
////                ShoppingListModel shoppingListModel = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));
//                //ShoppingListModel shoppingList = dbHelper.showListInfo((ShoppingListModel) listArrayAdapter.getItem(info.position));
//
//                //ShoppingListModel shoppingList = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));
//
////                ShoppingListModel shoppingList = dbHelper.getShoppingListName((ShoppingListModel) listArrayAdapter.getItem(info.position));
////
////                //ListItemsModel listItems = dbHelper.getShoppingListItems((ListItemsModel) listArrayAdapter.getItem(info.position));
////                int listID = shoppingList.getId();
////                List<String> listItems = dbHelper.getShoppingListItems(listID);
////
////
////                Intent listInfoIntent = new Intent(ListInfoActivity.this, ListInfoActivity.class);
////                //add id and name of the shoppinglist
////                listInfoIntent.putExtra("id", shoppingList.getId());
////                listInfoIntent.putExtra("name", shoppingList.getListName());
////
////                //add arrayList od items in list
////                listInfoIntent.putStringArrayListExtra("listItems", (ArrayList<String>) listItems);
////                //listInfoIntent.putExtra("listID", listID);  // Foreign Key ID from ShoppingList Table
////                startActivity(listInfoIntent);
//                break;
//
//
////--------------------------------------------
//            //delete chosen item
////            case R.id.delete_list:
////////                //delete chosen list
////////                boolean status = dbHelper.deleteOneCustomer((CustomerModel) customerArrayAdapter.getItem(info.position));
//////                Toast.makeText(SavedShoppingLists.this, "List deleted: ", Toast.LENGTH_SHORT).show();
////////                showCustomersOnListView();
//////                break;
////                boolean status = dbHelper.deleteShoppingList((ShoppingListModel) listArrayAdapter.getItem(info.position));
////
////                Toast.makeText(ListInfoActivity.this, "Lista borttagen: " + status, Toast.LENGTH_SHORT).show();
////                //showListsInListView();
////                break;
//
////--------------------------------------------
//            //edit chosen item
////            case R.id.update_list:
////
//
//        }
//        return super.onContextItemSelected(item);
//
//
//    }




}






//-------------------------------------------------------------------------------


//    View.OnLongClickListener listener = new View.OnLongClickListener() {
//        public boolean onLongClick(View v) {
//            Button clickedButton = (Button) v;
//            String buttonText = clickedButton.getText().toString();
//            Toast.makeText(ListInfoActivity.this, "LOOONG click detected ...", Toast.LENGTH_SHORT).show();
//        //Log.v(TAG, "button long pressed --> " + buttonText);
//            return true;
//        }
//    };
//    button.setOnLongClickListener(listener);



//    private void updateListData() {
//        lv_itemList.setAdapter(listArrayAdapter);
//    }













































