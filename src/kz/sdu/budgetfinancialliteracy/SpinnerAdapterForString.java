package kz.sdu.budgetfinancialliteracy;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapterForString extends ArrayAdapter<String> {

	private Context context;
	private String[] list;
	private Typeface font;

	public SpinnerAdapterForString(Context context, String[] list) {
		super(context, R.layout.spinner_item, list);
		this.context = context;
		this.list = list;
		font = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
	}

	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.spinner_drop,
				null);
		TextView text = (TextView) view.findViewById(R.id.spinner_drop_text);
		text.setTypeface(font);
		text.setText(list[position]);

		return view;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.spinner_item,
				null);
		TextView text = (TextView) view.findViewById(R.id.spinner_item_text);
		text.setTypeface(font);
		text.setText(list[position]);

		return view;
	}
}
