package dcsms.statusbargreper2;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import net.margaritov.preference.colorpicker.ColorPickerDialog.OnColorChangedListener;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import dcsms.statusbargreper2.adapter.GridAdapter;
import dcsms.statusbargreper2.systemui.Unit;
import dcsms.statusbargreper2.util.updateGUI;

public class Hello extends Activity {
	private Unit unit = new Unit();
	private GridView gdv;
	ColorPickerDialog cp_dial;
	private DcsmsPreference dcsms;
	private String[] menu = { "traficstate color", "adadeh", "a", "b0" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello);
		dcsms = new DcsmsPreference(Hello.this);
		gdv = (GridView) findViewById(R.id.gdv);
		GridAdapter adap = new GridAdapter(Hello.this, menu);
		gdv.setAdapter(adap);
		gdv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long id) {
				switch (pos) {
				case 0:
					ShowDialog();
					break;

				default:
					break;
				}

			}
		});

	}

	private void ShowDialog() {
		cp_dial = new ColorPickerDialog(this, dcsms.getTraficStateColor());
		cp_dial.setAlphaSliderVisible(true);
		cp_dial.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChanged(int color) {
				dcsms.saveIntSetting(unit.PREF_TRAFICSTATE_COLOR, color);
				new updateGUI(Hello.this);
			}
		});
		cp_dial.show();
	}
}
