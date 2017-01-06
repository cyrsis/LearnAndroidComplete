package freaktemplate.singlerestau.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import freaktemplate.singlerestau.R;

public class ImageLoader {

	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	Context context;
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;

	public ImageLoader(Context context) {
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
	}

	final int stub_id = R.drawable.ic_launcher;

	public void DisplayImage(String url, ImageView imageView) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		Log.d("bitmap", "" + bitmap);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);

		} else {
			queuePhoto(url, imageView);

			// imageView.setImageResource(stub_id);
		}
	}

	public void DisplayImage1(String url, ImageView imageView, ProgressBar progressBar) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		Log.d("bitmap", "" + bitmap);
		if (url == null || url.equals("")) {
			imageView.setVisibility(View.VISIBLE);
			// imageView.setImageBitmap(getDefaultBitmap());

			progressBar.setVisibility(View.INVISIBLE);

		} else if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.INVISIBLE);
		} else {
			queuePhoto1(url, imageView, progressBar);
			progressBar.setVisibility(View.VISIBLE);
			// imageView.setVisibility(View.INVISIBLE);
			// imageView.setImageResource(stub_id);
		}
	}

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	private void queuePhoto1(String url, ImageView imageView, ProgressBar progressBar) {
		PhotoToLoad1 p = new PhotoToLoad1(url, imageView, progressBar);
		executorService.submit(new PhotosLoader1(p));
		
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// from SD cache
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;

		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();

			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		}

	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 100;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 1.5 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize

			// InputStream in = new java.net.URL(urldisplay).openStream();
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			// o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	private class PhotoToLoad1 {
		public String url;
		public ImageView imageView;
		public ProgressBar progress;

		public PhotoToLoad1(String u, ImageView i, ProgressBar p) {
			url = u;
			imageView = i;
			progress = p;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	class PhotosLoader1 implements Runnable {
		PhotoToLoad1 photoToLoad;

		PhotosLoader1(PhotoToLoad1 photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			if (imageViewReused1(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused1(photoToLoad))
				return;
			BitmapDisplayer1 bd = new BitmapDisplayer1(bmp, photoToLoad, photoToLoad.progress);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);

		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	boolean imageViewReused1(PhotoToLoad1 photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
			// else
			// photoToLoad.imageView.setImageResource(stub_id);
		}
	}

	class BitmapDisplayer1 implements Runnable {
		Bitmap bitmap;
		PhotoToLoad1 photoToLoad;

		ProgressBar progress;

		public BitmapDisplayer1(Bitmap b, PhotoToLoad1 p, ProgressBar pro) {
			bitmap = b;
			photoToLoad = p;
			progress = pro;
		}

		@Override
		public void run() {
			if (imageViewReused1(photoToLoad))
				return;
			if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
				photoToLoad.progress.setVisibility(View.INVISIBLE);
			// else
			// photoToLoad.imageView.setImageResource(stub_id);
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

}
