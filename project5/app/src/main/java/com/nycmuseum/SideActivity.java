package com.nycmuseum;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.String.*;

public class SideActivity extends AppCompatActivity {
	private String[] data;
	private static final String[] ticketTypes = {"Adult Ticket $", "Senior Ticket $", "Student Ticket $"};
	private static final int MAX_TICKETS = 5;
	private static final int PRICES_NUM = 3;
	private static final double TAX_RATE = 0.08875;
	private LinearLayout priceLayout;
	private TextView ticketPrice, taxPrice, totalPrice;
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_side);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // enable back button
		priceLayout = findViewById(R.id.priceLayout);
		ticketPrice = findViewById(R.id.ticketPrice);
		taxPrice = findViewById(R.id.taxPrice);
		totalPrice = findViewById(R.id.totalPrice);

		Bundle extras = getIntent().getExtras(); // get museum info from parent activity.
		data = extras.getStringArray("museum");
		this.setTitle(getString(R.string.sideTitle));
		TextView tv = findViewById(R.id.museum);
		tv.setText(data[0]);
		setImage();
		setPrices();
		toast = Toast.makeText(this, "Maximum of 5 tickets for each!", Toast.LENGTH_LONG);
		toast.show();
	}

	private void setImage() {
		ImageView iv = findViewById(R.id.image);
		int IMAGE_ADDRESS_INDEX = 5;
		Picasso.get()
				.load(data[IMAGE_ADDRESS_INDEX])
				.fit()
				.into(iv);
		iv.setOnClickListener(v -> {
			toast.cancel();
			Uri uri = Uri.parse(data[4]);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		});
	}

	private void setPrices() {
		int PADDING_LEFT = 20, PADDING_TOP = 5, PADDING_RIGHT = 20, PADDING_BOTTOM = 5;
		priceLayout.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
		priceLayout.setOrientation(LinearLayout.VERTICAL);
		priceLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		);

		Integer[] ticketsNumOptions = new Integer[MAX_TICKETS + 1];
		for (int i = 0; i <= MAX_TICKETS; i++) ticketsNumOptions[i] = i;

		for (int i = 1; i <= PRICES_NUM; i++) {
			LinearLayout row = new LinearLayout(this);
			row.setOrientation(LinearLayout.HORIZONTAL);
			row.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
			row.setGravity(Gravity.CENTER);
			TextView price = new TextView(this);
			price.setText(format("%s%s", ticketTypes[i - 1], data[i]));
			price.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
			Spinner sp = new Spinner(this);
			sp.setId(i);
			sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
										   int position, long id) {
					updatePrices();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parentView) {
					updatePrices();
				}
			});
			ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
					android.R.layout.simple_spinner_item, ticketsNumOptions);
			sp.setAdapter(adapter);
			row.addView(price, params);
			row.addView(sp, params);
			priceLayout.addView(row);
		}
	}

	private void updatePrices () {
		int[] numbs = new int[PRICES_NUM];
		for (int i = 0; i < PRICES_NUM; i++) {
			numbs[i] = Integer.parseInt(((Spinner) findViewById(i + 1)).getSelectedItem().toString());
		}
		double sum = 0;
		for (int i = 0; i < PRICES_NUM; i++) sum += numbs[i] * Double.parseDouble(data[i+1]);
		DecimalFormat df = new DecimalFormat("####0.00");
		String priceBeforeTaxStr = getString(R.string.dollar) + df.format(sum);
		ticketPrice.setText(priceBeforeTaxStr);
		double tax = sum * TAX_RATE;
		String taxStr = getString(R.string.dollar) + df.format(tax);
		taxPrice.setText(taxStr);
		String totalStr = getString(R.string.dollar) + df.format(sum + tax);
		totalPrice.setText(totalStr);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			toast.cancel();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}