package kz.sdu.budgetfinancialliteracy;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends Activity implements
		OnItemSelectedListener {

	private SharedPreferences sp;
	private TextView currencyText;
	private TextView languageText;
	private TextView sortText;
	private TextView actionBarText;
	private String[] languageList = { "English", "Русский", "Қазақша" };
	private String[] currencyList = { "KZT", "RUB", "USD", "EUR" };
	private String[] sortList = { "Day", "Month", "Year" };
	private String[] sortListRu = { "День", "Месяц", "Год" };
	private String[] sortListKz = { "Күн", "Ай", "Жыл" };
	private Spinner currency;
	private Spinner sortBy;
	private SpinnerAdapterForString sortByAdapter;
	private Typeface fontNormal;
	private Typeface fontBold;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);

		fontNormal = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");
		fontBold = Typeface.createFromAsset(getAssets(), "fonts/arialb.ttf");

		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarText = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarText.setTypeface(fontBold);
		actionBarText.setText(R.string.settings);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		currencyText = (TextView) findViewById(R.id.settings_activity_currency_text);
		currencyText.setTypeface(fontNormal);
		currency = (Spinner) findViewById(R.id.settings_activity_currency);
		SpinnerAdapterForString currencyAdapter = new SpinnerAdapterForString(
				this, currencyList);
		currency.setAdapter(currencyAdapter);
		currency.setOnItemSelectedListener(this);
		languageText = (TextView) findViewById(R.id.settings_activity_language_text);
		languageText.setTypeface(fontNormal);
		Spinner language = (Spinner) findViewById(R.id.settings_activity_language);
		SpinnerAdapterForString languageAdapter = new SpinnerAdapterForString(
				this, languageList);
		language.setAdapter(languageAdapter);
		language.setOnItemSelectedListener(this);
		sortText = (TextView) findViewById(R.id.settings_activity_by_text);
		sortText.setTypeface(fontNormal);
		sortBy = (Spinner) findViewById(R.id.settings_activity_by);
		sortByAdapter = new SpinnerAdapterForString(this, sortList);
		sortBy.setAdapter(sortByAdapter);
		sortBy.setOnItemSelectedListener(this);

		sp = PreferenceManager.getDefaultSharedPreferences(this);
		String currentLanguage = sp.getString("language", "English");
		if (currentLanguage.equals(languageList[0])) {
			toEng();
			language.setSelection(0);
		} else if (currentLanguage.equals(languageList[1])) {
			toRus();
			language.setSelection(1);
		} else if (currentLanguage.equals(languageList[2])) {
			toKaz();
			language.setSelection(2);
		}
		checkSort();
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent.getId() == R.id.settings_activity_language) {
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("language", languageList[position]);
			editor.commit();
			if (position == 0) {
				toEng();
			} else if (position == 1) {
				toRus();
			} else if (position == 2) {
				toKaz();
			}
			checkSort();
		} else if (parent.getId() == R.id.settings_activity_by) {
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt("sort", position);
			editor.commit();
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}

	private void checkSort() {
		int sort = sp.getInt("sort", 0);
		sortBy.setSelection(sort);
	}

	private void toEng() {
		currencyText.setText(R.string.currency);
		languageText.setText(R.string.language);
		sortText.setText(R.string.by);
		actionBarText.setText(R.string.settings);
		sortByAdapter = new SpinnerAdapterForString(this, sortList);
		sortBy.setAdapter(sortByAdapter);
	}

	private void toRus() {
		currencyText.setText(R.string.currency_ru);
		languageText.setText(R.string.language_ru);
		sortText.setText(R.string.by_ru);
		actionBarText.setText(R.string.settings_ru);
		sortByAdapter = new SpinnerAdapterForString(this, sortListRu);
		sortBy.setAdapter(sortByAdapter);
	}

	private void toKaz() {
		currencyText.setText(R.string.currency_kz);
		languageText.setText(R.string.language_kz);
		sortText.setText(R.string.by_kz);
		actionBarText.setText(R.string.settings_kz);
		sortByAdapter = new SpinnerAdapterForString(this, sortListKz);
		sortBy.setAdapter(sortByAdapter);
	}
}
