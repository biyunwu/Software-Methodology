package com.project5.app;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MuseumActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final double NYC_TAX = 0.08875;

    String[] tickets, museumNames, urls;
    int[] adultPrices, seniorPrices, studentPrices;
    private int adultPrice, seniorPrice, studentPrice;

    ImageView museumPic;
    TextView adultLabel, seniorLabel, studentLabel;
    Spinner adultQuantity, seniorQuantity, studentQuantity;
    EditText subTotal, taxAmount, totalAmount;
    Toast toast;

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
        museumNames = res.getStringArray(R.array.museums);
        urls = res.getStringArray(R.array.web_address);
        adultPrices = res.getIntArray(R.array.adult);
        seniorPrices = res.getIntArray(R.array.senior);
        studentPrices = res.getIntArray(R.array.student);

        museumPic = findViewById(R.id.museumPic);
        adultLabel = findViewById(R.id.adultLabel);
        seniorLabel = findViewById(R.id.seniorLabel);
        studentLabel = findViewById(R.id.studentLabel);
        adultQuantity = findViewById(R.id.adultQuantity);
        seniorQuantity = findViewById(R.id.seniorQuantity);
        studentQuantity = findViewById(R.id.studentQuantity);
        subTotal = findViewById(R.id.subTotal);
        subTotal.setInputType(InputType.TYPE_NULL); // set editable to false.
        taxAmount = findViewById(R.id.taxAmount);
        taxAmount.setInputType(InputType.TYPE_NULL);
        totalAmount = findViewById(R.id.totalAmount);
        totalAmount.setInputType(InputType.TYPE_NULL);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, tickets);
        adultQuantity.setAdapter(adapter);
        seniorQuantity.setAdapter(adapter);
        studentQuantity.setAdapter(adapter);

        adultQuantity.setOnItemSelectedListener(this);
        seniorQuantity.setOnItemSelectedListener(this);
        studentQuantity.setOnItemSelectedListener(this);

        for (int i = 0; i < museumNames.length; i++) {
            if (museumNames[i].equals(museum)) {
                TypedArray images = res.obtainTypedArray(R.array.images);
                museumPic.setImageResource(images.getResourceId(i, 0));
                images.recycle();
                int finalI = i;
                museumPic.setOnClickListener(v -> {
                    Uri uri = Uri.parse(urls[finalI]);
                    Intent web = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(web);
                    toast.cancel(); // when image is clicked and toast is still visible.
                });
                adultPrice = adultPrices[i];
                adultLabel.setText(res.getString(R.string.adultLabel, adultPrice));
                seniorPrice = seniorPrices[i];
                seniorLabel.setText(res.getString(R.string.seniorLabel, seniorPrice));
                studentPrice = studentPrices[i];
                studentLabel.setText(res.getString(R.string.studentLabel, studentPrice));
                break;
            }
        }

        toast = Toast.makeText(this, res.getString(R.string.ticket_toast), Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int subTotalPrice = Integer.parseInt(adultQuantity.getSelectedItem().toString()) * adultPrice
                + Integer.parseInt(seniorQuantity.getSelectedItem().toString()) * seniorPrice
                + Integer.parseInt(studentQuantity.getSelectedItem().toString()) * studentPrice;
        subTotal.setText(getString(R.string.ticket_cost_int, subTotalPrice));
        double taxPrice = subTotalPrice * NYC_TAX;
        taxAmount.setText(getString(R.string.ticket_cost, taxPrice));
        double totalPrice = subTotalPrice + taxPrice;
        totalAmount.setText(getString(R.string.ticket_cost, totalPrice));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        subTotal.setText(getString(R.string.ticket_cost_int, 0));
        taxAmount.setText(getString(R.string.ticket_cost, 0.0));
        totalAmount.setText(getString(R.string.ticket_cost_int, 0.0));
    }
}