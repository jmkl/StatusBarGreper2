package dcsms.statusbargreper2.systemui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;

public class getReferensi {
	private Unit unit = new Unit();
	private SharedPreferences pref;
	public getReferensi(Context mContext,String key){
	Context cont = null;
	try {
		cont = mContext.createPackageContext(unit.GREPER_PAKETNAME, 0);
	} catch (NameNotFoundException e) {
	}
	pref = cont.getSharedPreferences(key,	Context.MODE_WORLD_READABLE);
	}
	public SharedPreferences getPref(){
		return pref;
	}
}
