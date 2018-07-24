package kz.sdu.budgetfinancialliteracy;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LiteracyListAdapter extends ArrayAdapter<Literacy> {

	private Context context;
	private ArrayList<Literacy> list;
	private Typeface font;

	public LiteracyListAdapter(Context context, ArrayList<Literacy> list) {
		super(context, R.layout.literacy_list_item, list);
		this.context = context;
		this.list = list;
		font = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.literacy_list_item, null);
		TextView text = (TextView) view
				.findViewById(R.id.literacy_list_item_text);
		text.setTypeface(font);
		text.setText(list.get(position).getText());

		return view;
	}
}
