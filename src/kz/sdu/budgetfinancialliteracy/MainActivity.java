package kz.sdu.budgetfinancialliteracy;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SharedPreferences sp;
	private TextView actionBarTitle;
	private MainListAdapter adapter;
	private ListView list;
	private String[] mainPageItems = { "Literacy", "Budget", "Settings" };
	private String[] mainPageItemsRu = { "Грамотность", "Бюджет", "Настройки" };
	private String[] mainPageItemsKz = { "Сауаттылық", "Бюджет", "Баптаулары" };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/arialb.ttf");

		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle.setTypeface(font);
		actionBarTitle.setText(R.string.short_name);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		list = (ListView) findViewById(R.id.main_activity_list);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch (position) {
				case 0:
					intent = new Intent(MainActivity.this,
							LiteracyActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(MainActivity.this, BudgetActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(MainActivity.this,
							SettingsActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
	}

	protected void onResume() {
		super.onResume();

		sp = PreferenceManager.getDefaultSharedPreferences(this);
		String currentLanguage = sp.getString("language", "English");
		if (currentLanguage.equals("English")) {
			toEng();
		} else if (currentLanguage.equals("Русский")) {
			toRus();
		} else if (currentLanguage.equals("Қазақша")) {
			toKaz();
		}

		list.setAdapter(adapter);
	}

	private void toEng() {
		adapter = new MainListAdapter(this, mainPageItems);
		actionBarTitle.setText(R.string.short_name);
	}

	private void toRus() {
		adapter = new MainListAdapter(this, mainPageItemsRu);
		actionBarTitle.setText(R.string.short_name_ru);
	}

	private void toKaz() {
		adapter = new MainListAdapter(this, mainPageItemsKz);
		actionBarTitle.setText(R.string.short_name_kz);
	}
}
