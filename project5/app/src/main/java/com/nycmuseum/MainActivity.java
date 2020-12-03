package com.nycmuseum;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	// [name, adult, child, senior price, website, pic address]
	private final ArrayList<String[]> museums = new ArrayList<>();
	private static final int PADDING = 20;
	private static final int TITLE_SIZE = 20;
	private static final int TEXT_SIZE = 16;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try (BufferedReader reader = new BufferedReader( // get museums info from assets.
				new InputStreamReader(getAssets().open("data.txt"), StandardCharsets.UTF_8))
		) {
			String line;
			while ((line = reader.readLine()) != null) museums.add(line.split(";"));
		} catch (IOException e) {
			Log.e("Error: ", e.toString());
		}

		ScrollView sv = new ScrollView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
		);
		int PADDING_LEFT = 40;
		params.setMargins(PADDING_LEFT, PADDING, PADDING, PADDING);
		sv.addView(getList(), params);
		setContentView(sv);
	}

	private LinearLayout getList() {
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		TextView title = new TextView(this);
		title.setText(getString(R.string.title));
		title.setPadding(PADDING, PADDING, PADDING, PADDING);
		title.setTextSize(TITLE_SIZE);
		title.setTextColor(getColor(R.color.titleColor));
		ll.addView(title);
		museums.forEach(museum -> {
			TextView tv = new TextView(this);
			tv.setText(museum[0]);
			tv.setTextSize(TEXT_SIZE);
			tv.setOnClickListener(v -> {
				Intent intent = new Intent(MainActivity.this, SideActivity.class);
				intent.putExtra("museum", museum);
				startActivity(intent);
			});
			tv.setPadding(PADDING, PADDING, PADDING, PADDING);
			tv.setTextColor(Color.BLACK);
			ll.addView(tv);
		});
		return ll;
	}
}