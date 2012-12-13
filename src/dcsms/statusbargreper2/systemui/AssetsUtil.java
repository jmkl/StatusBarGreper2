package dcsms.statusbargreper2.systemui;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class AssetsUtil {
	public Bitmap GetImageFromAssets(Context mContext, String namaFile) {
		Bitmap s = null;
		try {
			InputStream ims = mContext.getAssets().open(namaFile);
			Drawable d = Drawable.createFromStream(ims, null);
			s = ((BitmapDrawable) d).getBitmap();
		} catch (IOException ex) {
			Log.e(getClass().getSimpleName(), "gagal load assets", ex);
		}
		return s;
	}

}
