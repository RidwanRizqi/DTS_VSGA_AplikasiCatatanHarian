package com.example.dts_vsga_aplikasicatatanharian;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Note extends AppCompatActivity {
    public static final String HEADER_MSG = "com.example.notes.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        final TextView textView = findViewById(R.id.Heading);
        textView.setText(message);
        final String FILE_NAME = message + ".txt";
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader( new InputStreamReader( fis ) );
            String line;
            StringBuilder whole = new StringBuilder();
            while ( (line = reader.readLine()) != null ) {
                if(whole.toString().equals("")) {
                    whole.append(line);
                }else{
                    whole.append("\n").append(line);
                }
            }
            reader.close();
            TextView textView1 = findViewById(R.id.content);
            textView1.setText(whole.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            File dir = getFilesDir();
            File file = new File(dir, FILE_NAME);
            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
            file.delete();
            startActivity(intent1);
        });
        Button edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(v -> {
            TextView textViewcon = (TextView) findViewById(R.id.content);
            Intent intent12 = new Intent(getApplicationContext(), EditActivity.class);
            intent12.putExtra(HEADER_MSG, message);
            startActivity(intent12);
        });
    }
}