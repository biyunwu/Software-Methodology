package com.project5.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Main activity to start the app. It triggers the side activity when museum list is clicked.
 *
 * @author Anthony Triolo, Biyun Wu
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    protected static final String MUSEUM_NAME = "com.project5.app.MESSAGE";

    /**
     * Method to create the initial view for the main activity.
     *
     * @param savedInstanceState state info for the view.
     */
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

    /**
     * Method to load the museum view when the child view (museum) in the museum list is clicked.
     *
     * @param parent parent view of the selected element.
     * @param view selected view.
     * @param position index of the selected child view.
     * @param id unique identifier of the selected child view.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String museum = ((TextView) view).getText().toString();
        Intent intent = new Intent(this, MuseumActivity.class);
        intent.putExtra(MUSEUM_NAME, museum);
        startActivity(intent);
    }
}