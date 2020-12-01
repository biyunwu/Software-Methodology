package com.project5.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MuseumActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] tickets;
    int adultPrice;
    int seniorPrice;
    int studentPrice;

    String webURL;
    public static final double NYC_TAX = 0.08875;

    ImageView museumPic;
    TextView adultLabel;
    TextView seniorLabel;
    TextView studentLabel;
    Spinner adultQuantity;
    Spinner seniorQuantity;
    Spinner studentQuantity;
    EditText subTotal;
    EditText taxAmount;
    EditText totalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);
        setTitle(getResources().getString(R.string.museum_activity_title));
        Intent main = getIntent();
        String museum = main.getStringExtra(MainActivity.MUSEUM_NAME);
        TextView museumName = findViewById(R.id.museumName);
        museumName.setText(museum);
        Resources res = getResources();
        tickets = res.getStringArray(R.array.tickets);

        museumPic = findViewById(R.id.museumPic);
        adultLabel = findViewById(R.id.adultLabel);
        seniorLabel = findViewById(R.id.seniorLabel);
        studentLabel = findViewById(R.id.studentLabel);
        adultQuantity = (Spinner) findViewById(R.id.adultQuantity);
        seniorQuantity = (Spinner) findViewById(R.id.seniorQuantity);
        studentQuantity = (Spinner) findViewById(R.id.studentQuantity);
        subTotal = findViewById(R.id.subTotal);
        taxAmount = findViewById(R.id.taxAmount);
        totalAmount = findViewById(R.id.totalAmount);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tickets);
        adultQuantity.setAdapter(adapter);
        seniorQuantity.setAdapter(adapter);
        studentQuantity.setAdapter(adapter);

        adultQuantity.setOnItemSelectedListener(this);
        seniorQuantity.setOnItemSelectedListener(this);
        studentQuantity.setOnItemSelectedListener(this);

        switch (museum) {
            case "The Museum of Modern Art": {
                museumPic.setImageResource(R.drawable.moma);
                webURL = res.getString(R.string.moma_url);
                adultPrice = res.getInteger(R.integer.moma_adult);
                adultLabel.setText(res.getString(R.string.adultLabel, adultPrice));
                seniorPrice = res.getInteger(R.integer.moma_senior);
                ;
                seniorLabel.setText(res.getString(R.string.seniorLabel, seniorPrice));
                studentPrice = res.getInteger(R.integer.moma_student);
                ;
                studentLabel.setText(res.getString(R.string.studentLabel, studentPrice));
                break;
            }
            case "American Museum of Natural History": {
                museumPic.setImageResource(R.drawable.natural_history);
                webURL = res.getString(R.string.amnh_url);
                adultPrice = res.getInteger(R.integer.amnh_adult);
                adultLabel.setText(res.getString(R.string.adultLabel, adultPrice));
                seniorPrice = res.getInteger(R.integer.amnh_senior);
                seniorLabel.setText(res.getString(R.string.seniorLabel, seniorPrice));
                studentPrice = res.getInteger(R.integer.amnh_student);
                studentLabel.setText(res.getString(R.string.studentLabel, studentPrice));
                break;
            }
            case "The Metropolitan Museum of Art": {
                museumPic.setImageResource(R.drawable.met);
                webURL = res.getString(R.string.met_url);
                adultPrice = res.getInteger(R.integer.met_adult);
                adultLabel.setText(res.getString(R.string.adultLabel, adultPrice));
                seniorPrice = res.getInteger(R.integer.met_senior);
                seniorLabel.setText(res.getString(R.string.seniorLabel, seniorPrice));
                studentPrice = res.getInteger(R.integer.met_student);
                studentLabel.setText(res.getString(R.string.studentLabel, studentPrice));
                break;
            }
            case "The Noguchi Museum": {
                museumPic.setImageResource(R.drawable.noguchi);
                webURL = res.getString(R.string.noguchi_url);
                adultPrice = res.getInteger(R.integer.noguchi_adult);
                adultLabel.setText(res.getString(R.string.adultLabel, adultPrice));
                seniorPrice = res.getInteger(R.integer.noguchi_senior);
                seniorLabel.setText(res.getString(R.string.seniorLabel, seniorPrice));
                studentPrice = res.getInteger(R.integer.noguchi_student);
                studentLabel.setText(res.getString(R.string.studentLabel, studentPrice));
                break;
            }
        }
        Toast.makeText(getApplicationContext(), res.getString(R.string.ticket_toast), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Resources res = getResources();
        int subTotalPrice = Integer.parseInt(adultQuantity.getSelectedItem().toString()) * adultPrice + Integer.parseInt(seniorQuantity.getSelectedItem().toString()) * seniorPrice + Integer.parseInt(studentQuantity.getSelectedItem().toString()) * studentPrice;
        subTotal.setText(res.getString(R.string.ticket_cost_int, subTotalPrice));
        double taxPrice = subTotalPrice * NYC_TAX;
        taxAmount.setText(res.getString(R.string.ticket_cost, taxPrice));
        double totalPrice = subTotalPrice + taxPrice;
        totalAmount.setText(res.getString(R.string.ticket_cost, totalPrice));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClick(View v) {
        Uri uri = Uri.parse(webURL);
        Intent web = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(web);
    }
}