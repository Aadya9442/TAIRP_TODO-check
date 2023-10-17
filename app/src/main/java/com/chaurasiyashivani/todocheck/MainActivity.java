package com.chaurasiyashivani.todocheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;

    private Button button;
    private int selectedItemPosition = -1; // Store the selected item's position for editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItemPosition == -1) {
                    addItem(v);
                } else {
                    updateItem(v);
                }
            }
        });

        items = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(arrayAdapter);

        setupListviewListener();
    }

    private void setupListviewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Item Removed", Toast.LENGTH_SHORT).show();
                items.remove(position);
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item edit when it's clicked
                selectedItemPosition = position;
                EditText input = findViewById(R.id.input);
                input.setText(items.get(position));
                button.setText("Update");
            }
        });
    }

    private void addItem(View v) {
        EditText input = findViewById(R.id.input);
        String itemText = input.getText().toString();
        if (!itemText.equals(" ")) {
            arrayAdapter.add(itemText);
            input.setText(" ");
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Text....", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateItem(View v) {
        EditText input = findViewById(R.id.input);
        String itemText = input.getText().toString();
        if (!itemText.equals(" ")) {
            items.set(selectedItemPosition, itemText);
            arrayAdapter.notifyDataSetChanged();
            input.setText(" ");
            button.setText("Add");
            selectedItemPosition = -1;
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Text....", Toast.LENGTH_SHORT).show();
        }
    }
}
