package dcsms.statusbargreper2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.util.Log;

public class PindahinFile {
	public PindahinFile(Context c, String inputPath, String type,
			String namafile) {
		String outputPath = c.getFilesDir() + type;
		InputStream in = null;
		OutputStream out = null;
		try {

			// create output directory if it doesn't exist
			File dir = new File(outputPath);
			if (!dir.exists()) {
				dir.mkdirs();
				dir.setReadable(true, false);
				dir.setWritable(true, false);
				dir.setExecutable(true, false);

			} else {
				dir.setReadable(true, false);
				dir.setWritable(true, false);
				dir.setExecutable(true, false);
				;
			}

			in = new FileInputStream(inputPath);
			out = new FileOutputStream(outputPath + namafile);

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.close();
			in = null;

			// write the output file
			out.flush();
			out.close();
			out = null;

			File f = new File(outputPath + namafile);
			if (f.exists()) {
				f.setReadable(true, false);
				f.setWritable(true, false);
				f.setExecutable(true, false);
			}

		}

		catch (FileNotFoundException fnfe1) {
			Log.e("tag", fnfe1.getMessage());
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}

	}
}
