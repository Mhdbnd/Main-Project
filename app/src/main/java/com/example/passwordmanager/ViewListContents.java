package com.example.passwordmanager;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {


    DatabaseHelper myDB;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        final ListView listView = (ListView) findViewById(R.id.ListView);
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        myDB = new DatabaseHelper(this);

        final ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();

        if(data.getCount()==0){
            Toast.makeText(ViewListContents.this, "The Database was empty", Toast.LENGTH_LONG).show();
        }
        else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice,theList);
                listView.setAdapter(adapter);
                theFilter.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        (ViewListContents.this).adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        SparseBooleanArray positionchecker= listView.getCheckedItemPositions();

                        int count =listView.getCount();

                        for(int item = count-1; item>=0; item--){
                            if (positionchecker.get(item)){
                                adapter.remove(theList.get(item));
                                Toast.makeText(ViewListContents.this,"Deletion Successful",Toast.LENGTH_SHORT).show();
                            }
                        }

                        positionchecker.clear();

                        adapter.notifyDataSetChanged();

                        return false;
                    }
                });
            }
        }

    }
}
