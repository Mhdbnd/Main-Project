package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    Button btnADD,btnView,btnReport;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editText = (EditText) findViewById(R.id.editText);
        btnADD = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        btnReport = (Button) findViewById(R.id.btnReport);
        myDB = new DatabaseHelper(this);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SecondActivity.this,ViewListContents.class);
                startActivity(intent);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SecondActivity.this, report.class);
                startActivity(i);
            }
        });


        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if(editText.length() !=0){
                    AddData(newEntry);
                    editText.setText("");
                }
                else {
                    Toast.makeText(SecondActivity.this, "You must put something in text field", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void AddData (String newEntry){
        boolean insertData = myDB.addData(newEntry);

        if(insertData == true){
            Toast.makeText(SecondActivity.this,"Successfully Entered Data",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(SecondActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }
}