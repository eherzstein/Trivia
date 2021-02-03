package com.example.trivia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    Button parameters;
    final int REQUEST_CODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        parameters = findViewById(R.id.parameters);

        Spinner dropdownC = findViewById(R.id.spinnerC);
        Spinner dropdownD = findViewById(R.id.spinnerD);

        String[] itemsC = new String[]{"Any Category", "General Knowledge", "Entertainment: Books", "Entertainment: Film", "Entertainment: Music",
                "Entertainment: Musicals & Theatres", "Entertainment: Television", "Entertainment: Video Games", "Entertainment: Board Games", "Science & Nature",
                "Science: Computers", "Science: Mathematics", "Mythology", "Sports", "Geography", "History", "Politics", "Art", "Celebrities",
                "Animals", "Vehicles", "Entertainment: Comics", "Science: Gadgets", "Entertainment: Japanese Anime & Manga",
                "Entertainment: Cartoon & Animation"};

        String[] itemsD = new String[]{"Any Difficulty", "Easy", "Medium", "Hard"};

        ArrayAdapter<String> adapterC = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsC);
        ArrayAdapter<String> adapterD = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsD);
        dropdownC.setAdapter(adapterC);
        dropdownD.setAdapter(adapterD);

        parameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,MainActivity.class);

                intent.putExtra("Message1", dropdownC.getSelectedItem().toString());
                intent.putExtra("Message2", dropdownD.getSelectedItem().toString());

                startActivityForResult(intent,REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                String result=data.getStringExtra("returnData");
                Toast.makeText(MainActivity2.this,result,Toast.LENGTH_LONG).show();
            }
        }
    }
}