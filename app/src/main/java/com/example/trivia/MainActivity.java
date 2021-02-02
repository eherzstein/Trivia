package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

   RadioButton choice1, choice2, choice3, choice4;
   RadioGroup radioGroup;
   Button generate, verify;
   TextView question;

   String category = "";
   String difficulty = "";
   String [] choices = new String [4];

	public void shuffleChoices(String choices[], RadioGroup radioGroup)
    {
       // choices = new String [] {"0","1","2","correct_answer"};


        Random rand = new Random();

        for (int i = 0; i < choices.length; i++) {
            int randomSwap = rand.nextInt(choices.length);
            String temp = choices[randomSwap];
            choices[randomSwap] = choices[i];
            choices[i] = temp;
        }

        /*for (int i = 0; i < radioGroup.getChildCount(); i++) {
            ((RadioButton) radioGroup.getChildAt(i)).setText(String.valueOf(choices[i]));
        }*/

       // Log.d("tag","hello" + choice4.getText().toString());

    }

    public String correctFormat(String str)
    {
       // if(str.contains("&oacute;"))
            str = str.replace("&oacute;","ó");

      //  if(str.contains("&quot;"))
        str = str.replace("&quot;","'");

       // if(str.contains("&Eacute;"))
        str = str.replace("&Eacute;","É");

       // if(str.contains("&#039;"))
        str = str.replace("&#039;","'");

       // if(str.contains("&amp;"))
        str = str.replace("&amp;","&");

       // if(str.contains("&shy;"))
        str = str.replace("&shy;","");

       return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        radioGroup = findViewById(R.id.radioGroup);
        generate = findViewById(R.id.generate);
        question = findViewById(R.id.question);
        verify = findViewById(R.id.verify);
        afficher();

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficher();
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // verify(verify);
            }
        });
    }

    /*public void verify(String correct)
    {
        String url = "https://opentdb.com/api.php?amount=50&category=9&difficulty=easy&type=multiple";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            for(int k=0; k<radioGroup.getChildCount(); k++) {
                                if (((RadioButton) radioGroup.getChildAt(k)).getText().equals(correct))
                                {


                                }
                            }
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
    }*/

    public void afficher()
    {
        String url = "https://opentdb.com/api.php?amount=50&category=9&difficulty=easy&type=multiple";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int amount = 50;

                            JSONArray array = response.getJSONArray("results");

                            //get random question
                            Random rand = new Random();
                            int randomNum = rand.nextInt(amount);
                            JSONObject object = array.getJSONObject(randomNum);

                            String q = object.getString("question");

                            question.setText(correctFormat(q));

                            JSONArray incorrect_answers = object.getJSONArray("incorrect_answers");

                            String correct = object.getString("correct_answer");
                            choices[0] = correct;

                            for (int i = 1; i < radioGroup.getChildCount(); i++) {
                                choices[i] = incorrect_answers.getString(i-1);
                                choices[i] = correctFormat(choices[i]);
                            }

                            shuffleChoices(choices, radioGroup);

                          //  Log.d("tag","helloo"+incorrect_answers.getJSONObject(0).toString());

                            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                                ((RadioButton) radioGroup.getChildAt(j)).setText(choices[j]);
                            }



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