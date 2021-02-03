package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
   Button generate, verify, back;
   TextView question;
   ImageView imageView, imageView2, imageView3, imageView4;

   String category = "";
   String difficulty = "";
   String [] choices = new String [4];

	public void shuffleChoices(String[] choices, RadioGroup radioGroup)
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
        choice4 = findViewById(R.id.choice4);
        radioGroup = findViewById(R.id.radioGroup);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        generate = findViewById(R.id.generate);
        question = findViewById(R.id.question);
        verify = findViewById(R.id.verify);
        back = findViewById(R.id.back);
        afficher();

        String[] itemsC = new String[]{"Any Category", "General Knowledge", "Entertainment: Books", "Entertainment: Film", "Entertainment: Music",
                "Entertainment: Musicals & Theatres", "Entertainment: Television", "Entertainment: Video Games", "Entertainment: Board Games", "Science & Nature",
                "Science: Computers", "Science: Mathematics", "Mythology", "Sports", "Geography", "History", "Politics", "Art", "Celebrities",
                "Animals", "Vehicles", "Entertainment: Comics", "Science: Gadgets", "Entertainment: Japanese Anime & Manga",
                "Entertainment: Cartoon & Animation"};

        String[] itemsD = new String[]{"Any Difficulty", "Easy", "Medium", "Hard"};

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            String dropdownC = extras.getString("Message1");
            String dropdownD = extras.getString("Message2");

            for(int i = 1; i < itemsC.length; i++)
            {
                if(itemsC[i].equals(dropdownC))
                {
                    category = "&category="+(i+8);
                }
            }
            for(int j = 1; j < itemsD.length; j++) {
                if (itemsD[j].equals(dropdownD)) {
                    difficulty = "&difficulty=" + dropdownD.toLowerCase();
                }
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code ici
                Intent returnIntent=getIntent();
                returnIntent.putExtra("returnData", "");
                setResult(RESULT_OK,returnIntent);
                finish();

            }
        });

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


    public void verify(String correct)
    {
        boolean correctA = false;

            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);
            if(selectedRadioButton.getText().equals(correct)) {
                correctA = true;
            }

        if(correctA)
        {
            switch(selectedId) {

                case R.id.choice1:
                    imageView.setImageResource(R.drawable.checkmark);
                break;

                case R.id.choice2:
                    imageView2.setImageResource(R.drawable.checkmark);
                    break;

                case R.id.choice3:
                    imageView3.setImageResource(R.drawable.checkmark);
                    break;

                case R.id.choice4:
                    imageView4.setImageResource(R.drawable.checkmark);
                    break;
            }

          //  Log.d("hi","dude im working");
            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_LONG).show();

        }
    }

    public void afficher()
    {
      //  String url = "https://opentdb.com/api.php?amount=50+&category=9&difficulty=easy&type=multiple";
        String url = "https://opentdb.com/api.php?amount=50" + category + difficulty + "&type=multiple";
        Log.d("dis the url", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            radioGroup.clearCheck();
                            imageView.setImageResource(R.drawable.blank);
                            imageView2.setImageResource(R.drawable.blank);
                            imageView3.setImageResource(R.drawable.blank);
                            imageView4.setImageResource(R.drawable.blank);

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

                            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                                ((RadioButton) radioGroup.getChildAt(j)).setText(choices[j]);
                            }

                            verify.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    verify(correct);
                                }
                            });

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