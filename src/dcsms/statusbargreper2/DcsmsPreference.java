package dcsms.statusbargreper2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import dcsms.statusbargreper2.systemui.Unit;

public class DcsmsPreference {
	private final SharedPreferences pref;
	private Unit unit = new Unit();

	@SuppressWarnings("deprecation")
	public DcsmsPreference(Context c) {
		this.pref = c.getSharedPreferences(unit.GREPER_PAKETNAME,
				Context.MODE_WORLD_READABLE);
		loadPrefs(pref);
	}

	private void loadPrefs(SharedPreferences _pref) {
		unit.TS_COLOR = _pref.getInt(unit.PREF_TRAFICSTATE_COLOR, Color.WHITE);

	}

	public String saveStringSetting(String key, String value) {
		getEditor().putString(key, value).commit();
		return value;
	}

	public void saveIntSetting(String key, int value) {
		getEditor().putInt(key, value).commit();
	}

	public void saveBoolSetting(String key, boolean value) {
		getEditor().putBoolean(key, value).commit();
	}
	
	public int getFontSize(){
		return pref.getInt(unit.PREF_TRAFICSTATE_FONTSIZE, 10);
	}
public String getFontFace(){
	return pref.getString(unit.PREF_TRAFICSTATE_FONT, null);
}
	public boolean getTraficStateisShow() {
		return pref.getBoolean(unit.PREF_TRAFICSTATE_SHOWHIDE, false);

	}

	public int getTraficStateColor() {
		return pref.getInt(unit.PREF_TRAFICSTATE_COLOR, Color.WHITE);
	}

	public SharedPreferences.Editor getEditor() {
		return pref.edit();
	}

}
