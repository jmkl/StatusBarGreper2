package dcsms.statusbargreper2.adapter;

import dcsms.statusbargreper2.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private String[] data;
	private LayoutInflater inflater;
	private TextView tv;
	private ImageView iv;

	public GridAdapter(Context ctx, String[] data) {
		this.data = data;
		inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return data[pos];
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View conView, ViewGroup parent) {
		View v = conView;
		if (v == null) {
			v = inflater.inflate(R.layout.megrid, null);
			tv = (TextView) v.findViewById(R.id.grid_txt);
			iv = (ImageView) v.findViewById(R.id.grid_view);
		}
		tv.setText(data[pos]);
		return v;
	}

}
