package dcsms.statusbargreper2;

import android.os.Bundle;
import dcsms.statusbargreper2.sherlock.BaseSherlockFragmentActivity;
import dcsms.statusbargreper2.sherlock.trafficstate.TStateConfig;

public class Hello extends BaseSherlockFragmentActivity {
	private TStateConfig traffic = new TStateConfig();
	private TStateConfig traffic2 = new TStateConfig();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		mActionBar.addTab(mActionBar.newTab().setText("TrafficState")
				.setTabListener(new TabListener(mPager, 0)));
		mActionBar.addTab(mActionBar.newTab().setText("Clock")
				.setTabListener(new TabListener(mPager, 1)));

		mFragments.add(traffic);
		mFragments.add(traffic2);
		setAdapter();
	}
}

/**
 * private Unit unit = new Unit(); private GridView gdv; ColorPickerDialog
 * cp_dial; private DcsmsPreference dcsms; private String[] menu = {
 * "TraficState", "Battery", "Data", "Signal", "Clock", "Statusbar Backgroud",
 * "SuperShorCut", "Weather", "Carrier" };
 * 
 * @Override protected void onCreate(Bundle savedInstanceState) {
 *           setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
 *           super.onCreate(savedInstanceState); setContentView(R.layout.hello);
 *           dcsms = new DcsmsPreference(Hello.this); gdv = (GridView)
 *           findViewById(R.id.gdv);
 * 
 *           GridAdapter adap = new GridAdapter(Hello.this, menu);
 *           gdv.setAdapter(adap); gdv.setOnItemClickListener(new
 *           OnItemClickListener() {
 * @Override public void onItemClick(AdapterView<?> arg0, View v, int pos, long
 *           id) { switch (pos) { case 0: ShowDialog(); break;
 * 
 *           default: break; }
 * 
 *           } });
 * 
 *           }
 * 
 *           private void ShowDialog() { cp_dial = new ColorPickerDialog(this,
 *           dcsms.getTraficStateColor()); cp_dial.setAlphaSliderVisible(true);
 *           cp_dial.setOnColorChangedListener(new OnColorChangedListener() {
 * @Override public void onColorChanged(int color) {
 *           dcsms.saveIntSetting(unit.PREF_TRAFICSTATE_COLOR, color); new
 *           updateGUI(Hello.this, unit.GREPER_UPDATESTATUSBARTRAFFIC); } });
 *           cp_dial.show(); } }
 */
