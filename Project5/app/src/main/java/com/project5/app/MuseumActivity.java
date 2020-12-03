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
        adultQuantity = findViewById(R.id.adultQuantity);
        seniorQuantity = findViewById(R.id.seniorQuantity);
        studentQuantity = findViewById(R.id.studentQuantity);
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

        String[] museumInfo;
        
        switch (museum) {
            case "The Museum of Modern Art": {
                museumInfo = res.getStringArray(R.array.moma_info);
                museumPic.setImageResource(R.drawable.moma);
                setMuseumInfo(museumInfo);
                break;
            }
            case "American Museum of Natural History": {
                museumInfo = res.getStringArray(R.array.amnh_info);
                museumPic.setImageResource(R.drawable.natural_history);
                setMuseumInfo(museumInfo);
                break;
            }
            case "The Metropolitan Museum of Art": {
                museumInfo = res.getStringArray(R.array.met_info);
                museumPic.setImageResource(R.drawable.met);
                setMuseumInfo(museumInfo);
                break;
            }
            case "The Noguchi Museum": {
                museumInfo = res.getStringArray(R.array.noguchi_info);
                museumPic.setImageResource(R.drawable.noguchi);
                setMuseumInfo(museumInfo);
                break;
            }
        }
        Toast.makeText(getApplicationContext(), res.getString(R.string.ticket_toast), Toast.LENGTH_LONG).show();
    }

    public void setMuseumInfo(String[] info){
        Resources res = getResources();
        webURL = info[0];
        adultPrice = Integer.parseInt(info[1]);
        adultLabel.setText(res.getString(R.string.adultLabel, adultPrice));
        seniorPrice = Integer.parseInt(info[2]);
        seniorLabel.setText(res.getString(R.string.seniorLabel, seniorPrice));
        studentPrice = Integer.parseInt(info[3]);
        studentLabel.setText(res.getString(R.string.studentLabel, studentPrice));
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
        Resources res = getResources();
        subTotal.setText(res.getString(R.string.ticket_cost_int, 0));
        taxAmount.setText(res.getString(R.string.ticket_cost, 0.0));
        totalAmount.setText(res.getString(R.string.ticket_cost_int, 0.0));
    }

    public void onClick(View v) {
        Uri uri = Uri.parse(webURL);
        Intent web = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(web);
    }
}