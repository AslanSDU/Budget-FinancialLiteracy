package kz.sdu.budgetfinancialliteracy;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class BudgetActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private SharedPreferences sp;
	private DatabaseHelper db;
	private Dialog dialog;

	private String money = "";
	private int w = 0;
	private int forwhat = 0;
	private int sort;

	private EditText moneyText;
	private TextView incomingMoney;
	private TextView incomingMoneyText;
	private TextView spendingMoney;
	private TextView spendingMoneyText;
	private TextView remainingMoney;
	private TextView remainingMoneyText;
	private TextView actionBarText;

	private String[] account = { "Cash", "ATM Card", "Deposit" };
	private String[] accountRu = { "Наличные", "Банкомат", "Депозит" };
	private String[] accountKz = { "Ақша", "Банкомат", "Салым" };

	private int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private String[] months = { "January", "February", "Martch", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };
	private String[] monthsRu = { "Январь", "Февраль", "Март", "Апрель", "Май",
			"Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь",
			"Декабрь" };
	private String[] monthsKz = { "Қаңтар", "Ақпан", "Наурыз", "Сәуір",
			"Мамыр", "Маусым", "Шілде", "Тамыз", "Қыркүйек", "Қазан", "Қараша",
			"Желтоқсан" };
	private int[] years = { 2014, 2015 };

	private Spinner sortBy;
	private Spinner accountType;
	private SpinnerAdapterForString accountTypeAdapter;

	private Typeface fontNormal;
	private Typeface fontBold;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.budget_activity);

		fontNormal = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");
		fontBold = Typeface.createFromAsset(getAssets(), "fonts/arialb.ttf");

		db = new DatabaseHelper(this);

		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.action_bar_with_settings);
		actionBarText = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_with_settings_title);
		actionBarText.setTypeface(fontBold);
		actionBarText.setText(R.string.budget);
		Button actionBarSettings = (Button) actionBar.getCustomView()
				.findViewById(R.id.action_bar_with_settings_settings);
		actionBarSettings.setOnClickListener(this);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		sortBy = (Spinner) findViewById(R.id.budget_activity_by);
		sortBy.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (sort == 0 || sort == 2) {
					ArrayList<ForDB> listI = db.get(0,
							accountType.getSelectedItemPosition(), sort,
							(int) (Integer) sortBy.getSelectedItem());
					int incomingMoneyCount = 0;
					for (int i = 0; i < listI.size(); i++) {
						incomingMoneyCount += listI.get(i).getMoney();
					}
					incomingMoney.setText(incomingMoneyCount + " KZT");
					ArrayList<ForDB> listS = db.get(1,
							accountType.getSelectedItemPosition(), sort,
							(int) (Integer) sortBy.getSelectedItem());
					int spendingMoneyCount = 0;
					for (int i = 0; i < listS.size(); i++) {
						spendingMoneyCount += listS.get(i).getMoney();
					}
					spendingMoney.setText(spendingMoneyCount + " KZT");
					remainingMoney.setText(incomingMoneyCount
							- spendingMoneyCount + " KZT");
				} else if (sort == 1) {
					ArrayList<ForDB> listI = db.get(0,
							accountType.getSelectedItemPosition(), sort,
							sortBy.getSelectedItemPosition());
					int incomingMoneyCount = 0;
					for (int i = 0; i < listI.size(); i++) {
						incomingMoneyCount += listI.get(i).getMoney();
					}
					incomingMoney.setText(incomingMoneyCount + " KZT");
					ArrayList<ForDB> listS = db.get(1,
							accountType.getSelectedItemPosition(), sort,
							sortBy.getSelectedItemPosition());
					int spendingMoneyCount = 0;
					for (int i = 0; i < listS.size(); i++) {
						spendingMoneyCount += listS.get(i).getMoney();
					}
					spendingMoney.setText(spendingMoneyCount + " KZT");
					remainingMoney.setText(incomingMoneyCount
							- spendingMoneyCount + " KZT");
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		accountType = (Spinner) findViewById(R.id.budget_activity_account);

		dialog = new Dialog(BudgetActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.enter_money);
		moneyText = (EditText) dialog.findViewById(R.id.enter_money_text);
		Button ok = (Button) dialog.findViewById(R.id.enter_money_ok);
		ok.setOnClickListener(this);

		incomingMoney = (TextView) findViewById(R.id.budget_activity_incoming_money);
		incomingMoney.setTypeface(fontNormal);
		incomingMoneyText = (TextView) findViewById(R.id.budget_activity_incoming_money_text);
		incomingMoneyText.setTypeface(fontNormal);
		spendingMoney = (TextView) findViewById(R.id.budget_activity_spending_money);
		spendingMoney.setTypeface(fontNormal);
		spendingMoneyText = (TextView) findViewById(R.id.budget_activity_spending_money_text);
		spendingMoneyText.setTypeface(fontNormal);
		remainingMoney = (TextView) findViewById(R.id.budget_activity_sum);
		remainingMoney.setTypeface(fontNormal);
		remainingMoneyText = (TextView) findViewById(R.id.budget_activity_account_text);
		remainingMoneyText.setTypeface(fontNormal);

		Button income_salary = (Button) findViewById(R.id.income_salary);
		income_salary.setOnClickListener(this);
		Button income_persent = (Button) findViewById(R.id.income_persent);
		income_persent.setOnClickListener(this);
		Button income_portfolio = (Button) findViewById(R.id.income_portfolio);
		income_portfolio.setOnClickListener(this);
		Button income_other = (Button) findViewById(R.id.income_other);
		income_other.setOnClickListener(this);
		Button spending_benzin = (Button) findViewById(R.id.spending_benzin);
		spending_benzin.setOnClickListener(this);
		Button spending_communication = (Button) findViewById(R.id.spending_communication);
		spending_communication.setOnClickListener(this);
		Button spending_debt = (Button) findViewById(R.id.spending_debt);
		spending_debt.setOnClickListener(this);
		Button spending_dom = (Button) findViewById(R.id.spending_dom);
		spending_dom.setOnClickListener(this);
		Button spending_fitness = (Button) findViewById(R.id.spending_fitness);
		spending_fitness.setOnClickListener(this);
		Button spending_fly = (Button) findViewById(R.id.spending_fly);
		spending_fly.setOnClickListener(this);
		Button spending_food = (Button) findViewById(R.id.spending_food);
		spending_food.setOnClickListener(this);
		Button spending_for_people = (Button) findViewById(R.id.spending_for_people);
		spending_for_people.setOnClickListener(this);
		Button spending_household_appliances = (Button) findViewById(R.id.spending_household_appliances);
		spending_household_appliances.setOnClickListener(this);
		Button spending_med = (Button) findViewById(R.id.spending_med);
		spending_med.setOnClickListener(this);
		Button spending_media = (Button) findViewById(R.id.spending_media);
		spending_media.setOnClickListener(this);
		Button spending_public_services = (Button) findViewById(R.id.spending_public_services);
		spending_public_services.setOnClickListener(this);
		Button spending_shop = (Button) findViewById(R.id.spending_shop);
		spending_shop.setOnClickListener(this);
		Button spending_shops = (Button) findViewById(R.id.spending_shops);
		spending_shops.setOnClickListener(this);
		Button spending_transport = (Button) findViewById(R.id.spending_transport);
		spending_transport.setOnClickListener(this);
		Button spending_other = (Button) findViewById(R.id.spending_other);
		spending_other.setOnClickListener(this);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.action_bar_with_settings_settings:
			Intent intent = new Intent(BudgetActivity.this,
					SettingsActivity.class);
			startActivity(intent);
			break;
		case R.id.income_salary:
			forwhat = 0;
			w = 0;
			dialog.show();
			break;
		case R.id.income_persent:
			forwhat = 1;
			w = 0;
			dialog.show();
			break;
		case R.id.income_portfolio:
			forwhat = 2;
			w = 0;
			dialog.show();
			break;
		case R.id.income_other:
			forwhat = 3;
			w = 0;
			dialog.show();
			break;
		case R.id.spending_benzin:
			forwhat = 4;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_communication:
			forwhat = 5;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_debt:
			forwhat = 6;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_dom:
			forwhat = 7;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_fitness:
			forwhat = 8;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_fly:
			forwhat = 9;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_food:
			forwhat = 10;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_for_people:
			forwhat = 11;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_household_appliances:
			forwhat = 12;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_med:
			forwhat = 13;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_media:
			forwhat = 14;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_other:
			forwhat = 15;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_public_services:
			forwhat = 16;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_shop:
			forwhat = 17;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_shops:
			forwhat = 18;
			w = 1;
			dialog.show();
			break;
		case R.id.spending_transport:
			forwhat = 19;
			w = 1;
			dialog.show();
			break;
		case R.id.enter_money_ok:
			money = moneyText.getText().toString();
			if (money.length() > 0) {
				if (w == 0) {
					String current = incomingMoney.getText().toString();
					int toInt = Integer.parseInt(current.substring(0,
							current.length() - 4));
					incomingMoney.setText(toInt + Integer.parseInt(money)
							+ " KZT");
					Calendar calendar = Calendar.getInstance();
					int day = calendar.get(Calendar.DAY_OF_MONTH);
					int month = calendar.get(Calendar.MONTH);
					int year = calendar.get(Calendar.YEAR);
					int accountDB = accountType.getSelectedItemPosition();
					current = incomingMoney.getText().toString();
					toInt = Integer.parseInt(current.substring(0,
							current.length() - 4));
					db.add(new ForDB(toInt, forwhat, w, accountDB, day, month,
							year));
				} else if (w == 1) {
					String current = spendingMoney.getText().toString();
					int toInt = Integer.parseInt(current.substring(0,
							current.length() - 4));
					spendingMoney.setText(toInt + Integer.parseInt(money)
							+ " KZT");
					Calendar calendar = Calendar.getInstance();
					int day = calendar.get(Calendar.DAY_OF_MONTH);
					int month = calendar.get(Calendar.MONTH);
					int year = calendar.get(Calendar.YEAR);
					int accountDB = accountType.getSelectedItemPosition();
					current = spendingMoney.getText().toString();
					toInt = Integer.parseInt(current.substring(0,
							current.length() - 4));
					db.add(new ForDB(toInt, forwhat, w, accountDB, day, month,
							year));
				}
				moneyText.setText("");
				String incomingValue = incomingMoney.getText().toString();
				int incomingToInt = Integer.parseInt(incomingValue.substring(0,
						incomingValue.length() - 4));
				String spendingValue = spendingMoney.getText().toString();
				int spendingToInt = Integer.parseInt(spendingValue.substring(0,
						spendingValue.length() - 4));
				remainingMoney.setText(incomingToInt - spendingToInt + " KZT");
			}
			dialog.dismiss();
		default:
			break;
		}
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
	}

	public void onNothingSelected(AdapterView<?> parent) {
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

		sort = sp.getInt("sort", 0);
		if (sort == 0) {
			Calendar calendar = Calendar.getInstance();
			int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
			int currentPosition = 0;
			int month = calendar.get(Calendar.MONTH);
			ArrayList<Integer> dayList = new ArrayList<Integer>();
			for (int i = 1; i <= days[month]; i++) {
				dayList.add(i);
			}
			SpinnerAdapterForInteger adapter = new SpinnerAdapterForInteger(
					this, dayList);
			sortBy.setAdapter(adapter);
			for (int i = 0; i < dayList.size(); i++) {
				if (dayList.get(i) == currentDay) {
					currentPosition = i;
					break;
				}
			}
			sortBy.setSelection(currentPosition);
		} else if (sort == 1) {
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH);
			SpinnerAdapterForString adapter = null;
			if (currentLanguage.equals("English")) {
				adapter = new SpinnerAdapterForString(this, months);
			} else if (currentLanguage.equals("Русский")) {
				adapter = new SpinnerAdapterForString(this, monthsRu);
			} else if (currentLanguage.equals("Қазақша")) {
				adapter = new SpinnerAdapterForString(this, monthsKz);
			}
			sortBy.setAdapter(adapter);
			sortBy.setSelection(month);
		} else if (sort == 2) {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int currentPosition = 0;
			ArrayList<Integer> yearList = new ArrayList<Integer>();
			for (int i = 0; i < years.length; i++) {
				yearList.add(years[i]);
			}
			SpinnerAdapterForInteger adapter = new SpinnerAdapterForInteger(
					this, yearList);
			sortBy.setAdapter(adapter);
			for (int i = 0; i < yearList.size(); i++) {
				if (yearList.get(i) == currentYear) {
					currentPosition = i;
					break;
				}
			}
			sortBy.setSelection(currentPosition);
		}

		if (sort == 0 || sort == 2) {
			ArrayList<ForDB> listI = db.get(0,
					accountType.getSelectedItemPosition(), sort,
					(int) (Integer) sortBy.getSelectedItem());
			int incomingMoneyCount = 0;
			for (int i = 0; i < listI.size(); i++) {
				incomingMoneyCount += listI.get(i).getMoney();
			}
			incomingMoney.setText(incomingMoneyCount + " KZT");
			ArrayList<ForDB> listS = db.get(1,
					accountType.getSelectedItemPosition(), sort,
					(int) (Integer) sortBy.getSelectedItem());
			int spendingMoneyCount = 0;
			for (int i = 0; i < listS.size(); i++) {
				spendingMoneyCount += listS.get(i).getMoney();
			}
			spendingMoney.setText(spendingMoneyCount + " KZT");
			remainingMoney.setText(incomingMoneyCount - spendingMoneyCount
					+ " KZT");
		} else if (sort == 1) {
			ArrayList<ForDB> listI = db.get(0,
					accountType.getSelectedItemPosition(), sort,
					sortBy.getSelectedItemPosition());
			int incomingMoneyCount = 0;
			for (int i = 0; i < listI.size(); i++) {
				incomingMoneyCount += listI.get(i).getMoney();
			}
			incomingMoney.setText(incomingMoneyCount + " KZT");
			ArrayList<ForDB> listS = db.get(1,
					accountType.getSelectedItemPosition(), sort,
					sortBy.getSelectedItemPosition());
			int spendingMoneyCount = 0;
			for (int i = 0; i < listS.size(); i++) {
				spendingMoneyCount += listS.get(i).getMoney();
			}
			spendingMoney.setText(spendingMoneyCount + " KZT");
			remainingMoney.setText(incomingMoneyCount - spendingMoneyCount
					+ " KZT");
		}
	}

	private void toEng() {
		accountTypeAdapter = new SpinnerAdapterForString(this, account);
		accountType.setAdapter(accountTypeAdapter);
		actionBarText.setText(R.string.budget);
		incomingMoneyText.setText(R.string.income);
		spendingMoneyText.setText(R.string.spending);
		remainingMoneyText.setText(R.string.account);
	}

	private void toRus() {
		accountTypeAdapter = new SpinnerAdapterForString(this, accountRu);
		accountType.setAdapter(accountTypeAdapter);
		actionBarText.setText(R.string.budget_ru);
		incomingMoneyText.setText(R.string.income_ru);
		spendingMoneyText.setText(R.string.spending_ru);
		remainingMoneyText.setText(R.string.account_ru);
	}

	private void toKaz() {
		accountTypeAdapter = new SpinnerAdapterForString(this, accountKz);
		accountType.setAdapter(accountTypeAdapter);
		actionBarText.setText(R.string.budget_kz);
		incomingMoneyText.setText(R.string.income_kz);
		spendingMoneyText.setText(R.string.spending_kz);
		remainingMoneyText.setText(R.string.account_kz);
	}
}
