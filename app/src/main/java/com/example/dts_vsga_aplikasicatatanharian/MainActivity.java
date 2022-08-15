package com.example.dts_vsga_aplikasicatatanharian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.notes.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File files = getFilesDir();
        String[] array = files.list();
        ArrayList<String> arrayList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        for (String filename : array) {
            filename = filename.replace(".txt", "");
            System.out.println(filename);
            adapter.add(filename);
        }
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        Button button = (Button) findViewById(R.id.savebutton);
        button.setOnClickListener(v -> {
            EditText editTextHeading = findViewById(R.id.editTextTextPersonName);
            EditText editTextContent = findViewById(R.id.contentfield);
            String heading = editTextHeading.getText().toString().trim();
            String content = editTextContent.getText().toString().trim();
            if (!heading.isEmpty()) {
                if(!content.isEmpty()) {
                    try {
                        FileOutputStream fileOutputStream = openFileOutput(heading + ".txt", Context.MODE_PRIVATE); //heading will be the filename
                        fileOutputStream.write(content.getBytes());
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    adapter.add(heading);
                    listView.setAdapter(adapter);
                }else {
                    editTextContent.setError("Isi catatan tidak boleh kosong!");
                }
            }else{
                editTextHeading.setError("Judul catatan tidak boleh kosong!");
            }
            editTextContent.setText("");
            editTextHeading.setText("");
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = listView.getItemAtPosition(position).toString();
            Intent intent = new Intent(getApplicationContext(), Note.class);
            intent.putExtra(EXTRA_MESSAGE, item);
            startActivity(intent);
        });
    }
}