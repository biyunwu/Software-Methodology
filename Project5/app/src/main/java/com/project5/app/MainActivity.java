package com.project5.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    protected static final String MUSEUM_NAME = "com.project5.app.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] museumArray = getResources().getStringArray(R.array.museums);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, museumArray);
        ListView museumList = findViewById(R.id.museumList);
        museumList.setAdapter(adapter);
        museumList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String museum = ((TextView) view).getText().toString();
        Intent intent = new Intent(this, MuseumActivity.class);
        intent.putExtra(MUSEUM_NAME, museum);
        startActivity(intent);
    }
}