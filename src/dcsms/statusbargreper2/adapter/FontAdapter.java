package dcsms.statusbargreper2.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dcsms.statusbargreper2.R;

public class FontAdapter extends ArrayAdapter<String> {

	private ArrayList<String> dir;
	private ArrayList<String> nama;
	public Context c;

	public FontAdapter(Context context, List<String> direktori,
			List<String> namafont) {
		super(context, 0, 0, direktori);
		this.c = context;
		this.dir = (ArrayList<String>) direktori;
		this.nama = (ArrayList<String>) namafont;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.fontrow, null);
		}

		String o = nama.get(position);
		Typeface tf = Typeface.createFromFile(dir.get(position));
		if (o != null) {
			TextView tt = (TextView) v.findViewById(R.id.txt);
			ImageView iv = (ImageView) v.findViewById(R.id.img);
			if (tt != null) {
				tt.setText(o);
				tt.setTypeface(tf);
			}

		}
		return v;
	}
}