package dcsms.statusbargreper2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.ImageView;

public class Hello extends Activity {
	private DraggableGridView dgv;
	private int[] icon = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g,
			R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k,
			R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Wallpaper_NoTitleBar_Fullscreen);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello);
		dgv = (DraggableGridView) findViewById(R.id.dgv);
		dgv.isInEditMode();
		for (int i = 0; i < 12; i++) {
			ImageView iv = new ImageView(this);
			iv.setImageResource(icon[i]);
			dgv.addView(iv);
		}
	}

	private Bitmap getTiles() {
		Bitmap bmp = Bitmap.createBitmap(150, 150, Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmp);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(0X44309AA7);
		c.drawRect(new Rect(0, 0, 150, 150), p);

		return bmp;
	}

}
