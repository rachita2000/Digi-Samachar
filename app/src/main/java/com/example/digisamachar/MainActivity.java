package com.example.digisamachar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;


public class MainActivity extends AppCompatActivity implements NewsItemClicked{
    private RecyclerView mRecyclerView;
    NewsListAdapter mAdapter;
    String category;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        Intent intent=getIntent();
        category=intent.getStringExtra("category");

        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData();
        mAdapter= new NewsListAdapter(MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void fetchData()
    {
        String url="https://api.currentsapi.services/v1/latest-news?language=en&country=in&category="+category+"&apiKey=nLFWnuafEBOrWUNz_KrOQrf2EAqyoyUQehinJ4_BldIVvkef";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray newJsonArray = response.getJSONArray("news");
                            ArrayList<News> newArray = new ArrayList<News>();
                            for (int i = 0; i < newJsonArray.length(); i++) {
                                JSONObject newJsonObject = newJsonArray.getJSONObject(i);
                                News news = new News(newJsonObject.getString("title"),
                                        newJsonObject.getString("author"),
                                        newJsonObject.getString("url"),
                                        newJsonObject.getString("image"));
                                newArray.add(news);
                            }
                            mAdapter.updateNews(newArray);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Something is wrong" , Toast.LENGTH_LONG).show();
                    }
                });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onItemClicked(News item) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}