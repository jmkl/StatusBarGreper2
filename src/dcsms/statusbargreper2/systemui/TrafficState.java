/*
 * Copyright (C) 2012 dcsms
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

package dcsms.statusbargreper2.systemui;

import java.text.DecimalFormat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.TrafficStats;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TrafficState extends TextView {
	private Context context;
	private boolean mAttached;
	private boolean mTrafik;
	private long mLastTime;
	private Unit unit = new Unit();
	private Handler mHandler = new Handler();
	private getReferensi ref;

	public TrafficState(Context context) {
		super(context);
		this.context = context;
	}

	public TrafficState(Context context, AttributeSet att) {
		super(context, att);
		this.context = context;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		if (!mAttached) {
			mAttached = true;
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_BOOT_COMPLETED);
			filter.addAction(unit.GREPER_UPDATESTATUSBARTRAFFIC);
			getContext().registerReceiver(mIntentReceiver, filter, null,
					getHandler());
			mLastTime = 0;
			modifInterface();
		}

	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mAttached) {
			getContext().unregisterReceiver(mIntentReceiver);
			mAttached = false;
		}
	}

	private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_BOOT_COMPLETED)
					|| action.equals(unit.GREPER_UPDATESTATUSBARTRAFFIC)) {
				modifInterface();
			}
		}

	};

	private void updateState() {
		long mStartRX = TrafficStats.getTotalRxBytes();

		long mStartTX = TrafficStats.getTotalTxBytes();

		if (mStartRX == TrafficStats.UNSUPPORTED
				|| mStartTX == TrafficStats.UNSUPPORTED) {
			Log.w("DCsmsTraffic", "Not Support");
		} else {
			if (mTrafik == true) {
				mHandler.removeCallbacks(mRunnable);
			} else {
				mHandler.postDelayed(mRunnable, 1000);
			}
		}

		{

		}

	}

	protected void modifInterface() {
		ref = new getReferensi(context, unit.GREPER_PAKETNAME);
		SharedPreferences pref = ref.getPref();
		if (pref.contains(unit.PREF_TRAFICSTATE_COLOR)) {
			setTextColor(pref.getInt(unit.PREF_TRAFICSTATE_COLOR, Color.WHITE));
			updateState();
		}
		if (pref.contains(unit.PREF_TRAFICSTATE_FONT)) {
			Typeface tf = Typeface.createFromFile(pref.getString(
					unit.PREF_TRAFICSTATE_FONT, null));
			setTypeface(tf);
			updateState();
		}
		if (pref.contains(unit.PREF_TRAFICSTATE_SHOWHIDE)) {
			boolean isShow = pref.getBoolean(unit.PREF_TRAFICSTATE_SHOWHIDE,
					false);
			if (isShow)
				setVisibility(View.VISIBLE);
			else
				setVisibility(View.GONE);
		}
		if (pref.contains(unit.PREF_TRAFICSTATE_FONTSIZE)) {
			int size = pref.getInt(unit.PREF_TRAFICSTATE_FONTSIZE, 10);
			setTextSize(size);
			updateState();
		}

	}

	private final Runnable mRunnable = new Runnable() {

		public void run() {

			long total = TrafficStats.getTotalRxBytes()
					+ TrafficStats.getTotalTxBytes();
			long lok = total - mLastTime;

			setText(Count(lok, true));
			mLastTime = total;
			Log.w("TRAFFIC", Count(lok, true));
			mHandler.postDelayed(mRunnable, 1000);

		}

	};

	public static String Count(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return new DecimalFormat("00.0").format(bytes / 1000) + " K/s";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1)
				+ (si ? "" : "i");
		return String.format("%.1f %S/s", bytes / Math.pow(unit, exp), pre);
	}
}
