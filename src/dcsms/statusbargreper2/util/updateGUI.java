package dcsms.statusbargreper2.util;

import android.content.Context;
import android.content.Intent;

public class updateGUI {
	public updateGUI(Context c,String Action) {
		Intent i = new Intent();
		i.setAction(Action);
		c.sendBroadcast(i);
	}
}
