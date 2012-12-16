package dcsms.statusbargreper2.sherlock.Unit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import net.margaritov.preference.colorpicker.ColorPickerDialog.OnColorChangedListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import dcsms.statusbargreper2.DcsmsPreference;
import dcsms.statusbargreper2.R;
import dcsms.statusbargreper2.adapter.GridAdapter;
import dcsms.statusbargreper2.dialog.FontDialog;
import dcsms.statusbargreper2.dialog.FontDialog.onFontListener;
import dcsms.statusbargreper2.dialog.SliderDialog;
import dcsms.statusbargreper2.dialog.SliderDialog.setOnValueSetListener;
import dcsms.statusbargreper2.qa.ActionItem;
import dcsms.statusbargreper2.qa.QuickAction;
import dcsms.statusbargreper2.qa.QuickAction.OnActionItemClickListener;
import dcsms.statusbargreper2.sherlock.BaseSherlockFragment;
import dcsms.statusbargreper2.systemui.Unit;
import dcsms.statusbargreper2.util.PindahinFile;
import dcsms.statusbargreper2.util.updateGUI;

public class TStateConfig extends BaseSherlockFragment {
	private Unit unit = new Unit();
	private GridView gdv;
	private ColorPickerDialog cp_dial;
	private DcsmsPreference dcsms;
	private FontDialog fDial;
	private SliderDialog slideDialog;
	private String[] menu = { "Color", "Fonts", "Size", "Hide/Show" };
	private List<String> namafont;
	private List<String> dirfont;
	private String fontPath = Environment.getExternalStorageDirectory()
			+ "/fonts";
	private QuickAction quickAction;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.hello, container, false);

		dcsms = new DcsmsPreference(getSherlockActivity());
		gdv = (GridView) v.findViewById(R.id.gdv);
		GridAdapter adap = new GridAdapter(getSherlockActivity(), menu);
		gdv.setAdapter(adap);
		// ListFonttoQuickAction();
		gdv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long id) {
				switch (pos) {
				case 0:
					ShowDialog();
					break;

				case 1:
					ShowFontDialog();
					// ShowQuickActionFont(v);
					break;
				case 2:
					ShowSliderDialog();
					break;
				case 3:
					ShownHide(v);
					break;

				}

			}
		});

		return v;
	}

	protected void ShowSliderDialog() {
		slideDialog = new SliderDialog(getSherlockActivity());
		slideDialog.setSetValue(new setOnValueSetListener() {

			@Override
			public void onValueSet(int value) {
				dcsms.saveIntSetting(unit.PREF_TRAFICSTATE_FONTSIZE, value);
				new updateGUI(getSherlockActivity(),
						unit.GREPER_UPDATESTATUSBARTRAFFIC);

			}
		});
		slideDialog.show();

	}

	protected void ShownHide(View v) {
		quickAction = new QuickAction(getSherlockActivity(),
				QuickAction.HORIZONTAL);
		String sh;
		boolean isShow = dcsms.getTraficStateisShow();
		if(!isShow)
			sh="Show";
		else
			sh="Hide";
		
		ActionItem a = new ActionItem(1, sh, getResources()
				.getDrawable(R.drawable.ic_tstate));
		quickAction.addActionItem(a);
		quickAction.show(v);
		quickAction
				.setOnActionItemClickListener(new OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction source, int pos,
							int actionId) {
						if (actionId == 1) {
							boolean SnH = dcsms.getTraficStateisShow();
							if (!SnH)
								dcsms.saveBoolSetting(
										unit.PREF_TRAFICSTATE_SHOWHIDE, true);
							else
								dcsms.saveBoolSetting(
										unit.PREF_TRAFICSTATE_SHOWHIDE, false);

							new updateGUI(getSherlockActivity(),
									unit.GREPER_UPDATESTATUSBARTRAFFIC);
						}

					}
				});

	}

	protected void ShowQuickActionFont(View v) {

		quickAction.show(v);
		quickAction
				.setOnActionItemClickListener(new OnActionItemClickListener() {

					@Override
					public void onItemClick(QuickAction source, int pos,
							int actionId) {
						Toast.makeText(getSherlockActivity(), dirfont.get(pos),
								5).show();

					}
				});

	}

	private void ListFonttoQuickAction() {
		namafont = new ArrayList<String>();
		dirfont = new ArrayList<String>();
		File f = new File(fontPath);
		File[] files = f.listFiles();
		for (File file : files) {
			namafont.add(file.getName());
			dirfont.add(file.getAbsolutePath());
		}
		quickAction = new QuickAction(getSherlockActivity(),
				QuickAction.VERTICAL);
		for (int i = 0; i < namafont.size(); i++) {
			ActionItem a = new ActionItem(i, namafont.get(i));
			quickAction.addActionItem(a);

		}

	}

	private void ShowFontDialog() {
		fDial = new FontDialog(getSherlockActivity());
		fDial.setPilihFont(new onFontListener() {

			@Override
			public void onFontdiPilih(String fontdir) {
				new PindahinFile(getSherlockActivity(), fontdir,
						unit.TYPE_FONT, unit.PREF_TRAFICSTATE_FONT + ".ttf");
				dcsms.saveStringSetting(unit.PREF_TRAFICSTATE_FONT,
						getSherlockActivity().getFilesDir() + unit.TYPE_FONT
								+ unit.PREF_TRAFICSTATE_FONT + ".ttf");
				new updateGUI(getSherlockActivity(),
						unit.GREPER_UPDATESTATUSBARTRAFFIC);

			}
		});
		fDial.show();

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
