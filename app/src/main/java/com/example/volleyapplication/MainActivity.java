package com.example.volleyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Song> songs;
    private static String JSON_URL = "http://starlord.hackerearth.com/studio";
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("start ---- -- -- - - - - - -- - - --- ");
        recyclerView = findViewById(R.id.songsList);
        songs = new ArrayList<>();
        extractSongs();


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new Adapter(getApplicationContext(),songs);
        recyclerView.setAdapter(adapter);

    }

    private void extractSongs() {
        System.out.println("start 2 # # # # # #  # ### # # #  # # #  # # # # ## # # ### # #  #####  #");
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println(" } | }|} |}| | |} |} | }| }| | }|  *******");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.println(" } | }|} |}| | |} |} | }| }| | }| "+response.length());
                for (int i = 0; i < response.length(); i++) {
                    try {

                        System.out.println("=- =-= -= -= -= -= -= -=-= -= -=-= -= -= - " + i);
                        JSONObject songObject = response.getJSONObject(i);

                        Song song = new Song();
                        song.setTitle(songObject.getString("song").toString());
                        song.setArtists(songObject.getString("artists".toString()));
                        song.setCoverImage(songObject.getString("cover_image"));
                        song.setSongURL(songObject.getString("url"));
                        songs.add(song);
                        System.out.println("=- =-= -= -= -= -= -= -=-= -= -=-= -= -= - " + i +"   "+song.getTitle());


                    } catch (JSONException e) {
                        System.out.println("Errooooooooooor ");
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(),songs);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }

}
