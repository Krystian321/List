package com.example.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> list = new ArrayList<>();
    ListView list_view;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,list);
        list_view.setAdapter(arrayAdapter);


        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu,popupMenu.getMenu());


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        switch (item.getItemId()){
                            case R.id.item_update:

                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dialog,null,false);
                            builder.setTitle("Update Item");
                            final EditText editText = v.findViewById(R.id.etItem);
                            editText.setText(list.get(position));

                            builder.setView(v);


                            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!editText.getText().toString().isEmpty()) {

                                        list.set(position, editText.getText().toString().trim());
                                        arrayAdapter.notifyDataSetChanged();
                                        Toast.makeText(MainActivity.this,"Item Updated !", Toast.LENGTH_SHORT).show();

                                    } else {
                                        editText.setError("add item here !");
                                    }

                                }
                            });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                                builder.show();

                                break;

                            case R.id.item_delete:

                                Toast.makeText(MainActivity.this,"Item Deleted",Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                break;

                        }
                        return true;
                    }
                });

                popupMenu.show();

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_manu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                _addItem();

                break;
        }
        return true;
    }

    private void _addItem() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add new item");

        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dialog,null,false);


        builder.setView(v);
        final EditText etItem = v.findViewById(R.id.etItem);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!etItem.getText().toString().isEmpty()) {

                    list.add(etItem.getText().toString().trim());
                    arrayAdapter.notifyDataSetChanged();

                } else {
                    etItem.setError("add item here !");
                }
            }

        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();


    }
}

