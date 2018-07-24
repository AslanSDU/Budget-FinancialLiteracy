package kz.sdu.budgetfinancialliteracy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class SplashScreen extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		Typeface font = Typeface
				.createFromAsset(getAssets(), "fonts/arial.ttf");

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		String language = sp.getString("language", "English");
		TextView text = (TextView) findViewById(R.id.splash_screen_text);
		text.setTypeface(font);
		if (language.equals("English")) {
			text.setText(R.string.app_name);
		} else if (language.equals("Русский")) {
			text.setText(R.string.app_name_ru);
		} else if (language.equals("Қазақша")) {
			text.setText(R.string.app_name_kz);
		}

		Thread thread = new Thread() {
			public void run() {
				try {
					int timer = 0;
					while (timer < 3000) {
						sleep(100);
						timer += 100;
					}
					Intent intent = new Intent(SplashScreen.this,
							MainActivity.class);
					startActivity(intent);
				} catch (Exception e) {
				} finally {
					finish();
				}
			}
		};
		thread.start();
	}
}
