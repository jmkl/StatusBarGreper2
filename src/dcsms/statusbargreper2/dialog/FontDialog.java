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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import dcsms.statusbargreper2.R;
import dcsms.statusbargreper2.adapter.FontAdapter;

public class FontDialog extends Dialog implements OnItemClickListener {
	private ListView lv;
	private List<String> namafont;
	private List<String> dirfont;
	private String fontPath = Environment.getExternalStorageDirectory()
			+ "/fonts";
	private onFontListener mListen;

	public interface onFontListener {
		public void onFontdiPilih(String fontdir);
	}

	@SuppressWarnings("deprecation")
	public FontDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.dimAmount = 0.0f;
		getWindow().setAttributes(lp);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.font);
		lv = (ListView) findViewById(R.id.fontlist);
		lv.setOnItemClickListener(this);

		ListFont();
	}

	private void ListFont() {
		namafont = new ArrayList<String>();
		dirfont = new ArrayList<String>();
		File f = new File(fontPath);
		File[] files = f.listFiles();
		for (File file : files) {
			String ttf = ".ttf";
			String otf = ".otf";
			if (file.isFile() && file.getName().endsWith(ttf)
					| file.getName().endsWith(otf) && file.length() > 1) {
				namafont.add(file.getName());
				dirfont.add(file.getAbsolutePath());
			}
		}
//		ListAdapter adap = new ArrayAdapter<String>(getContext(),
//				android.R.layout.simple_list_item_1, namafont);
		FontAdapter adap = new FontAdapter(getContext(), dirfont, namafont);
		lv.setAdapter(adap);

	}

	public void setPilihFont(onFontListener listener) {
		mListen = listener;

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int pos, long id) {
		if (mListen != null) {
			mListen.onFontdiPilih(dirfont.get(pos));
		}
		dismiss();
	}

}
