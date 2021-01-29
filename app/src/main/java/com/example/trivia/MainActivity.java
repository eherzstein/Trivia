package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

   RadioButton choice1, choice2, choice3;
   RadioGroup radioGroup;

   String category = "";
   String difficulty = "";
   String [] choices = new String [] {"0","1","2","correct_answer"};

	public void shuffleChoices(String choices[])
    {
       // choices = new String [] {"0","1","2","correct_answer"};

        Random rand = new Random();

        for (int i = 0; i < choices.length; i++) {
            int randomSwap = rand.nextInt(choices.length);
            String temp = choices[randomSwap];
            choices[randomSwap] = choices[i];
            choices[i] = temp;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        radioGroup = findViewById(R.id.radioGroup);
    }

    public void afficher()
    {
        String url = "https://opentdb.com/api.php?amount=50&category=9&difficulty=easy&type=multiple";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            shuffleChoices(choices);


                           // choice1.setText();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Tag","resultat =  "+error);
            }
        });
        // ajouter tous les éléments à la queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}