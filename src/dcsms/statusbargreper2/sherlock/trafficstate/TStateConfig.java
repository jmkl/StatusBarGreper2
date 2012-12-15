package dcsms.statusbargreper2.sherlock.trafficstate;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import net.margaritov.preference.colorpicker.ColorPickerDialog.OnColorChangedListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import dcsms.statusbargreper2.DcsmsPreference;
import dcsms.statusbargreper2.R;
import dcsms.statusbargreper2.adapter.GridAdapter;
import dcsms.statusbargreper2.sherlock.BaseSherlockFragment;
import dcsms.statusbargreper2.systemui.Unit;
import dcsms.statusbargreper2.util.updateGUI;

public class TStateConfig extends BaseSherlockFragment {
	private Unit unit = new Unit();
	private GridView gdv;
	private ColorPickerDialog cp_dial;
	private DcsmsPreference dcsms;
	private String[] menu = { "Color", "Fonts", "Size", "Hide/Show" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.hello, container, false);

		dcsms = new DcsmsPreference(getSherlockActivity());
		gdv = (GridView) v.findViewById(R.id.gdv);
		GridAdapter adap = new GridAdapter(getSherlockActivity(), menu);
		gdv.setAdapter(adap);
		gdv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,	long id) {
				switch (pos) {
				case 0:
					ShowDialog();
					break;

				default:
					break;
				}

			}
		});

		return v;
	}

	private void ShowDialog() {
		cp_dial = new ColorPickerDialog(getSherlockActivity(),
				dcsms.getTraficStateColor());
		cp_dial.setAlphaSliderVisible(true);
		cp_dial.setOnColorChangedListener(new OnColorChangedListener() {
			@Override
			public void onColorChanged(int color) {
				dcsms.saveIntSetting(unit.PREF_TRAFICSTATE_COLOR, color);
				new updateGUI(getSherlockActivity(),
						unit.GREPER_UPDATESTATUSBARTRAFFIC);
			}
		});
		cp_dial.show();
	}

}
