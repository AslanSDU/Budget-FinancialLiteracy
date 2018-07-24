package kz.sdu.budgetfinancialliteracy;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapterForInteger extends ArrayAdapter<Integer> {

	private Context context;
	private ArrayList<Integer> list;
	private Typeface font;

	public SpinnerAdapterForInteger(Context context, ArrayList<Integer> list) {
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
		text.setText(list.get(position).toString());

		return view;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.spinner_item,
				null);
		TextView text = (TextView) view.findViewById(R.id.spinner_item_text);
		text.setTypeface(font);
		text.setText(list.get(position).toString());

		return view;
	}
}
