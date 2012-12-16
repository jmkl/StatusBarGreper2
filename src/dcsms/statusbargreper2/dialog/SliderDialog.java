/*
 * Copyright (C) 2012 DCSMS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dcsms.statusbargreper2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import dcsms.statusbargreper2.DcsmsPreference;
import dcsms.statusbargreper2.R;

public class SliderDialog extends Dialog implements OnSeekBarChangeListener,
		android.view.View.OnClickListener {
	private SeekBar sb;
	private TextView tv;
	private DcsmsPreference dcsms;
	private Button btn;
	private setOnValueSetListener mListen;
	private int value = 10;
	private int Max = 50;
	private int Min = 10;

	public interface setOnValueSetListener {
		public void onValueSet(int value);
	}

	@SuppressWarnings("deprecation")
	public SliderDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.dimAmount = 0.0f;
		getWindow().setAttributes(lp);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slider);
		dcsms = new DcsmsPreference(context);
		sb = (SeekBar) findViewById(R.id.sb);
		tv = (TextView) findViewById(R.id.txt_value);
		btn = (Button) findViewById(R.id.apply);
		sb.setMax(getMax());
		sb.setOnSeekBarChangeListener(this);
		btn.setOnClickListener(this);
		value = dcsms.getFontSize();
		sb.setProgress(value - Min);
		tv.setText("WTF, i'm " + Integer.toString(value) + " px!!");
		tv.setTextSize(value);
		if (dcsms.getFontFace() != null) {
			Typeface tf = Typeface.createFromFile(dcsms.getFontFace());
			tv.setTypeface(tf);
		}

	}

	public int getMax() {
		return Max;
	}

	public void setMax(int max) {
		Max = max;
	}

	public void setMin(int min) {
		Min = min;
	}

	public void setSetValue(setOnValueSetListener listener) {
		mListen = listener;

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		value = progress + Min;
		tv.setTextSize(value);
		tv.setText("WTF, i'm " + Integer.toString(value) + " px!!");

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.apply) {
			if (mListen != null)
				mListen.onValueSet(value);
		}
		dismiss();

	}

}
