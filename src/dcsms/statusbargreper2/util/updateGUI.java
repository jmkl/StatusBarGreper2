package dcsms.statusbargreper2.util;

import android.content.Context;
import android.content.Intent;
import dcsms.statusbargreper2.systemui.Unit;

public class updateGUI {
	private Unit unit = new Unit();

	public updateGUI(Context c) {
		Intent i = new Intent();
		i.setAction(unit.GREPER_UPDATESTATUSBAR);
		c.sendBroadcast(i);
	}
}
