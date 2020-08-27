package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_createNewShoppingList;
    private Button btn_saved_shoppingLists;
    private Button btn_lastSavedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_createNewShoppingList = findViewById(R.id.btn_createNewShoppingList);
            btn_createNewShoppingList.setOnClickListener((View.OnClickListener) this);

        btn_saved_shoppingLists = findViewById(R.id.btn_saved_shoppingLists);
            btn_saved_shoppingLists.setOnClickListener((View.OnClickListener) this);

        btn_lastSavedList = findViewById(R.id.btn_lastSavedList);
            btn_lastSavedList.setOnClickListener((View.OnClickListener) this);

    }




    //assign action for each clickable button in Activity
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_createNewShoppingList:
                createNewShoppingList();
                break;

            case R.id.btn_saved_shoppingLists:
                gotoSavedShoppingListsAct();
                break;

            case R.id.btn_lastSavedList:
                gotoLastSavedListAct();
                break;

        }
    }

    public void createNewShoppingList(){
        //create new ShoppingList in a new Activity
        Intent intentCreateNewShoppingList = new Intent(MainActivity.this, CreateNewShoppingList.class);
        //intentCreateNewShoppingList.putExtra("listName", nameTime);
        startActivity(intentCreateNewShoppingList);


    }

    public void gotoSavedShoppingListsAct(){
        Intent intentSavedShoppingLists = new Intent(MainActivity.this, SavedShoppingLists.class);
        startActivity(intentSavedShoppingLists);
    }

    public void gotoLastSavedListAct(){
        Intent intentLastSavedShoppingList = new Intent(MainActivity.this, LastSavedShoppingList.class);
        startActivity(intentLastSavedShoppingList);
    }


}







//                //goto new activity to populate new List
//                Intent i = new Intent(MainActivity.this, CreateListActivity.class);
//                i.putExtra("dateTime", nameTime);
//                startActivity(i);

//Create database "Listname"
//declare empty list
//Listname listName;
//Reference database helper
//DBHelper dbHelper = new DBHelper(MainActivity.this);
//Init listname
//listName = new Listname(-1, nameTime);

//Add listname (boolean to check for errors)
//        boolean addedListname = false;
//        try {
//            addedListname = dbHelper.addListnameToDb(listName);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Toast.makeText(MainActivity.this, "ADDED LISTNAME: " + addedListname, Toast.LENGTH_SHORT).show();








