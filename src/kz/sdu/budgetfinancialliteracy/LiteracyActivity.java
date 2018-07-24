package kz.sdu.budgetfinancialliteracy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LiteracyActivity extends Activity {

	private SharedPreferences sp;
	private LiteracyListAdapter adapter;
	private ArrayList<Literacy> literacyItems;
	private ArrayList<String> literacyText;
	private TextView actionBarTitle;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.literacy_activity);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/arialb.ttf");

		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar_with_settings);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_with_settings_title);
		actionBarTitle.setTypeface(font);
		actionBarTitle.setText(R.string.literacy);
		Button actionBarSettings = (Button) actionBar.getCustomView()
				.findViewById(R.id.action_bar_with_settings_settings);
		actionBarSettings.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LiteracyActivity.this,
						SettingsActivity.class);
				startActivity(intent);
			}
		});
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		literacyItems = new ArrayList<Literacy>();
		literacyText = new ArrayList<String>();

		ListView list = (ListView) findViewById(R.id.literacy_activity_list);

		adapter = new LiteracyListAdapter(this, literacyItems);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(LiteracyActivity.this,
						LiteracyTextActivity.class);
				intent.putExtra("title", literacyItems.get(position).getText()
						.substring(3));
				intent.putExtra("text", literacyText.get(position));
				startActivity(intent);
			}
		});
	}

	protected void onResume() {
		super.onResume();

		sp = PreferenceManager.getDefaultSharedPreferences(this);
		String currentLanguage = sp.getString("language", "English");
		literacyItems.clear();
		literacyText.clear();
		if (currentLanguage.equals("English")) {
			toEng();
		} else if (currentLanguage.equals("Русский")) {
			toRus();
		} else if (currentLanguage.equals("Қазақша")) {
			toKaz();
		}
	}

	private void toEng() {
		actionBarTitle.setText(R.string.literacy);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(getAssets().open(
					"title.txt"), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				literacyItems.add(new Literacy(line, false));
			}
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ex) {
				}
			}
		}
		try {
			reader = new BufferedReader(new InputStreamReader(getAssets().open(
					"text.txt"), "UTF-8"));
			String line;
			String text = "";
			while ((line = reader.readLine()) != null) {
				if (line.equals("*****")) {
					literacyText.add(text);
					text = "";
				} else {
					text += line + "\n";
				}
			}
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	private void toRus() {
		actionBarTitle.setText(R.string.literacy_ru);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(getAssets().open(
					"title_ru.txt"), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				literacyItems.add(new Literacy(line, false));
			}
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ex) {
				}
			}
		}
		try {
			reader = new BufferedReader(new InputStreamReader(getAssets().open(
					"text_ru.txt"), "UTF-8"));
			String line;
			String text = "";
			while ((line = reader.readLine()) != null) {
				if (line.equals("*****")) {
					literacyText.add(text);
					text = "";
				} else {
					text += line + "\n";
				}
			}
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	private void toKaz() {
		actionBarTitle.setText(R.string.literacy_kz);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(getAssets().open(
					"title_kz.txt"), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				literacyItems.add(new Literacy(line, false));
			}
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ex) {
				}
			}
		}
		try {
			reader = new BufferedReader(new InputStreamReader(getAssets().open(
					"text_kz.txt"), "UTF-8"));
			String line;
			String text = "";
			while ((line = reader.readLine()) != null) {
				if (line.equals("*****")) {
					literacyText.add(text);
					text = "";
				} else {
					text += line + "\n";
				}
			}
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ex) {
				}
			}
		}
	}
}
