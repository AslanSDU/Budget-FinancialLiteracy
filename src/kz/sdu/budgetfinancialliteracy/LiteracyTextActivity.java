package kz.sdu.budgetfinancialliteracy;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class LiteracyTextActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.literacy_text_activity);

		Typeface fontNormal = Typeface.createFromAsset(getAssets(),
				"fonts/arial.ttf");
		Typeface fontBold = Typeface.createFromAsset(getAssets(),
				"fonts/arialb.ttf");

		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		String s = intent.getStringExtra("text");

		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		TextView actionBarTitle = (TextView) actionBar.getCustomView()
				.findViewById(R.id.action_bar_title);
		actionBarTitle.setTypeface(fontBold);
		actionBarTitle.setText(title);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		TextView text = (TextView) findViewById(R.id.literacy_text_activity_text);
		text.setTypeface(fontNormal);
		text.setText(s);
	}
}
