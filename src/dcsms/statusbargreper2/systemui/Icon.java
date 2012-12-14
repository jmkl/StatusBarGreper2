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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Icon extends LinearLayout {
	private boolean mAttached;
	private Unit unit = new Unit();
	private Context mContext;
	private int battLevel;
	private Animation anim;
	private boolean animasi = false;
	private getReferensi ref;
	// Baterai
	private Bitmap batterai;
	private int statusBaterai;
	private int x, y, bx, by;
	private int i;
	// sinyal
	private TelephonyManager Tel;
	private DcsmsPhoneStateListener MyListener;
	private int dataX;
	private int dataY;
	private int networkType;
	private Bitmap io;
	private int sinyal = 0;
	private int inout = 0;

	private ImageView imgBaterai, imgSinyal, imgData;
	private TextView tvBaterai;
	private RelativeLayout overlapiconLayout;

	public Icon(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		Inisial();
	}

	public Icon(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		Inisial();
	}

	public Icon(Context context) {
		super(context);
		mContext = context;
		Inisial();
	}

	@SuppressWarnings("deprecation")
	private void Inisial() {
		anim = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
		anim.setDuration(1000);
		anim.setRepeatCount(Animation.INFINITE);

		setGravity(Gravity.RIGHT);
		imgBaterai = new ImageView(mContext);
		imgData = new ImageView(mContext);
		imgSinyal = new ImageView(mContext);
		tvBaterai = new TextView(mContext);
		overlapiconLayout = new RelativeLayout(mContext);
		overlapiconLayout.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		imgData.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT));
		imgSinyal.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT));
		imgBaterai.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT));
		tvBaterai.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT));
		imgData.setAdjustViewBounds(true);
		imgSinyal.setAdjustViewBounds(true);
		imgBaterai.setAdjustViewBounds(true);
		
		tvBaterai.setGravity(Gravity.CENTER_VERTICAL);
		tvBaterai.setPadding(1, 0, 1, 0);
		imgData.setPadding(1, 0, 1, 0);
		imgSinyal.setPadding(1, 0, 1, 0);
		imgBaterai.setPadding(1, 0, 1, 0);
		overlapiconLayout.addView(imgData);
		overlapiconLayout.addView(imgSinyal);
		this.addView(overlapiconLayout);
		this.addView(imgBaterai);
		this.addView(tvBaterai);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (!mAttached) {
			mAttached = true;
			IntentFilter filter = new IntentFilter();
			filter.addAction(unit.GREPER_UPDATESTATUSBAR);
			filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
			filter.addAction(Intent.ACTION_BATTERY_CHANGED);
			getContext().registerReceiver(mIntentReceiver, filter, null,
					getHandler());
			MyListener = new DcsmsPhoneStateListener();
			Tel = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			Tel.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
					| PhoneStateListener.LISTEN_DATA_ACTIVITY);

		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mAttached) {
			getContext().unregisterReceiver(mIntentReceiver);
			mAttached = false;
			Tel.listen(MyListener, PhoneStateListener.LISTEN_NONE);
		}
	}

	private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (action.equals(Intent.ACTION_BATTERY_CHANGED)
					|| action.equals(unit.GREPER_UPDATESTATUSBAR)) {
				statusBaterai = intent.getIntExtra(BatteryManager.EXTRA_STATUS,
						BatteryManager.BATTERY_STATUS_UNKNOWN);
				int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,
						-1);
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
				battLevel = -1;
				if (rawlevel >= 0 && scale > 0) {
					battLevel = (rawlevel * 100) / scale;
				}
				reUpdateBaterai();

			}

		}

	};

	private void reUpdateBaterai() {
		ref = new getReferensi(mContext, "LAYOUT");
		SharedPreferences pref = ref.getPref();
		if (pref.contains(unit.ICON_BATERAI)) {
			// XXX something custom
		} else {
			InitDefaultBateraiIcon();
		}

	}

	private void InitDefaultBateraiIcon() {
		if (statusBaterai == BatteryManager.BATTERY_STATUS_CHARGING) {

			animasi = true;
			battAnimation();
		} else {
			animasi = false;
			battAnimation();
			Toast.makeText(mContext, "Full", 5).show();
		}
		AssetsUtil util = new AssetsUtil();
		batterai = util.GetImageFromAssets(mContext,
				"dcsms_default_baterai.png");
		x = batterai.getWidth();
		y = batterai.getHeight();
		bx = -(x / 4);
		by = -(y / 5);

		int e = battLevel;
		if (e <= 5) {
			updateBatrai(0, 0);
		} else if (e >= 6 && e <= 10) {
			updateBatrai(bx, 0);
		} else if (e >= 11 && e <= 15) {
			updateBatrai(bx * 2, 0);
		} else if (e >= 16 && e <= 20) {
			updateBatrai(bx * 3, 0);
		} else if (e >= 21 && e <= 25) {
			updateBatrai(0, by);
		} else if (e >= 26 && e <= 30) {
			updateBatrai(bx, by);
		} else if (e >= 31 && e <= 35) {
			updateBatrai(bx * 2, by);
		} else if (e >= 36 && e <= 40) {
			updateBatrai(bx * 3, by);
		} else if (e >= 41 && e <= 45) {
			updateBatrai(0, by * 2);
		} else if (e >= 46 && e <= 50) {
			updateBatrai(bx, by * 2);
		} else if (e >= 51 && e <= 55) {
			updateBatrai(bx * 2, by * 2);
		} else if (e >= 56 && e <= 60) {
			updateBatrai(bx * 3, by * 2);
		} else if (e >= 61 && e <= 65) {
			updateBatrai(0, by * 3);
		} else if (e >= 66 && e <= 70) {
			updateBatrai(bx, by * 3);
		} else if (e >= 71 && e <= 75) {
			updateBatrai(bx * 2, by * 3);
		} else if (e >= 76 && e <= 80) {
			updateBatrai(bx * 3, by * 3);
		} else if (e >= 81 && e <= 85) {
			updateBatrai(0, by * 4);
		} else if (e >= 86 && e <= 90) {
			updateBatrai(bx, by * 4);
		} else if (e >= 91 && e <= 95) {
			updateBatrai(bx * 2, by * 4);
		} else if (e >= 95) {
			updateBatrai(bx * 3, by * 4);
		} else {
			updateBatrai(0, 0);
		}

	}

	private void updateBatrai(int i, int j) {
		Bitmap bat = Bitmap.createBitmap(x / 4, y / 5, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bat);
		c.drawBitmap(batterai, i, j, null);
		imgBaterai.setImageBitmap(bat);

	}

	private void battAnimation() {
		if (animasi) {
			imgBaterai.startAnimation(anim);
		} else {
			imgBaterai.clearAnimation();
		}

	}

	private void defaultInOut(int xx) {
		AssetsUtil util = new AssetsUtil();
		io = util.GetImageFromAssets(mContext, "dcsms_default_inout.png");

		int x = io.getWidth();
		int y = io.getHeight();
		dataX = -(x / 5);
		dataY = -(y / 6);
		Bitmap bat = Bitmap.createBitmap(x / 5, y / 6, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bat);
		if (networkType == unit.NET_NN)
			c.drawBitmap(io, 0, dataY * networkType, null);
		else
			c.drawBitmap(io, xx, dataY * networkType, null);
		imgData.setImageBitmap(bat);

	}

	private void defaultSignalIcon(int sinyalstatus) {
		AssetsUtil util = new AssetsUtil();
		Bitmap bmp_signalIcon = util.GetImageFromAssets(mContext,
				"dcsms_default_sinyal.png");

		int sx = bmp_signalIcon.getWidth();
		int sy = bmp_signalIcon.getHeight();
		i = -(sx / 5);
		Bitmap sinyal = Bitmap
				.createBitmap(sx / 5, sy, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(sinyal);
		c.drawBitmap(bmp_signalIcon, sinyalstatus, 0, null);
		imgSinyal.setImageBitmap(sinyal);

	}

	private class DcsmsPhoneStateListener extends PhoneStateListener {
		private int s;

		@Override
		public void onDataActivity(int direction) {
			super.onDataActivity(direction);
			// Log.e("DIRECTION", Integer.toString(direction));
			// XXX INOUTTYPE
			String type = get_network();
			if (type.contains("EDGE")) {
				networkType = unit.NET_EDGE;
			} else if (type.contains("GPRS")) {
				networkType = unit.NET_GPRS;
			} else if (type.contains("HSDPA")) {
				networkType = unit.NET_HSDPA;
			} else if (type.contains("HSPA")) {
				networkType = unit.NET_HSPA;
			} else if (type.contains("UNKNOWN")) {
				networkType = unit.NET_NN;
			} else if (type.contains("WIFI")) {
				networkType = unit.NET_WIFI;
			} else {
				networkType = 5;
			}
			// XXX INOUT
			if (inout == 1) {
				// if (direction == TelephonyManager.DATA_ACTIVITY_IN) {
				//
				// //customInOut(dataX);
				// } else if (direction == TelephonyManager.DATA_ACTIVITY_OUT) {
				//
				// customInOut(dataX * 2);
				// } else if (direction == TelephonyManager.DATA_ACTIVITY_INOUT)
				// {
				//
				// customInOut(dataX * 3);
				// } else if (direction == TelephonyManager.DATA_ACTIVITY_NONE)
				// {
				//
				// customInOut(dataX * 4);
				// } else {
				//
				// customInOut(0);
				// }

			} else {
				if (direction == TelephonyManager.DATA_ACTIVITY_IN) {

					defaultInOut(dataX);
				} else if (direction == TelephonyManager.DATA_ACTIVITY_OUT) {
					defaultInOut(dataX * 2);
				} else if (direction == TelephonyManager.DATA_ACTIVITY_INOUT) {
					defaultInOut(dataX * 3);
				} else if (direction == TelephonyManager.DATA_ACTIVITY_NONE) {
					defaultInOut(dataX * 4);
				} else {
					defaultInOut(0);
				}
			}

		}

		@Override
		public void onSignalStrengthsChanged(SignalStrength signalStrength) {
			super.onSignalStrengthsChanged(signalStrength);
			s = signalStrength.getGsmSignalStrength();
			// Log.w("SINYALINFLATE", Integer.toString(s));
			if (sinyal == 1) {
				// custom
				// if (s <= 0) {
				// customSignalIcon(0);
				//
				// } else if (s >= 1 && s <= 4) {
				// // 1
				// customSignalIcon(i);
				// } else if (s >= 5 && s <= 7) {
				// // 2
				// customSignalIcon(i * 2);
				// } else if (s >= 8 && s <= 11) {
				// // 3
				// customSignalIcon(i * 3);
				// } else if (s >= 12) {
				// // 4
				// customSignalIcon(i * 4);
				// } else {
				// customSignalIcon(0);
				// }

			} else {
				// XXX DEFAULT SINYAL
				if (s <= 0) {

					defaultSignalIcon(0);

				} else if (s >= 1 && s <= 4) {
					// 1
					defaultSignalIcon(i);
				} else if (s >= 5 && s <= 7) {
					// 2
					defaultSignalIcon(i * 2);
				} else if (s >= 8 && s <= 11) {
					// 3
					defaultSignalIcon(i * 3);
				} else if (s >= 12) {
					// 4
					defaultSignalIcon(i * 4);
				} else {
					defaultSignalIcon(0);
				}
			}
		}
	}

	private String get_network() {
		String network_type = "UNKNOWN";// maybe usb reverse tethering
		NetworkInfo active_network = ((ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if (active_network != null && active_network.isConnectedOrConnecting()) {
			if (active_network.getType() == ConnectivityManager.TYPE_WIFI) {
				network_type = "WIFI";
			} else if (active_network.getType() == ConnectivityManager.TYPE_MOBILE) {
				network_type = ((ConnectivityManager) mContext
						.getSystemService(Context.CONNECTIVITY_SERVICE))
						.getActiveNetworkInfo().getSubtypeName();
			}
		}
		// Log.e("NETWORK_TYPE", network_type);
		return network_type;
	}

}
