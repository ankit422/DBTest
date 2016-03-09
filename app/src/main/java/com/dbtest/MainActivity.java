package com.dbtest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        rv = (RecyclerView)findViewById(R.id.cardList);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        db = new DatabaseHandler(this);

        if (db.getContactsCount()>0){
            //=====set data in lists==
            setdataInList();
        }else {
            CallApi();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void CallApi(){
        // Tag used to cancel the request
        String  tag_string_req = "string_req";

        String url = "http://appsculture.com/vocab/words.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());
                Gson gson = new GsonBuilder().serializeNulls().create();
                ModelWords wordsData = gson.fromJson(response, ModelWords.class);
                try {
                    if (wordsData.getWords().length>0){
                        for (int i = 0; i < wordsData.getWords().length; i++) {
                            if (Float.parseFloat(wordsData.getWords()[i].getRatio())>0) {
                                db.addContact(wordsData.getWords()[i]);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //=====set data in lists==
                setdataInList();
                
                pDialog.hide();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
                try {
                    Toast.makeText(MainActivity.this, "Somethings wrong.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

// Adding request to request queue
        AppClass.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void setdataInList() {
        List<Words> words = db.getAllContacts();
        RVAdapter adapter = new RVAdapter(words, this);
        rv.setAdapter(adapter);
    }
}
