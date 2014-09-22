package com.jd.a6i.common;

import java.io.InputStream;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

/**
 * 
 * @author liuruiqiang
 * 
 */
public class ImageCache {
	private static LruCache<Object, Object> mMemoryCache;
	private static Context mContext;

	/**
	 * 
	 * @param key
	 *            key
	 * @param bitmap
	 *            bitmap
	 */
	
	private static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null && bitmap != null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 
	 * @param key
	 * @return getBitmapFromMemCache
	 */
	
	private static Bitmap getBitmapFromMemCache(String key) {
		return (Bitmap) mMemoryCache.get(key);
	}

	/**
	 * 
	 * @param context
	 *            context
	 * @param resId
	 *            resId
	 * @param imageView
	 *            resId
	 */
	public static void loadBitmap(Context context, int keyword,
			ImageView imageView) {
		initCache(context);

		final String imageKey = String.valueOf(keyword);

		final Bitmap bitmap = getBitmapFromMemCache(imageKey);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			BitmapWorkerTask task = new BitmapWorkerTask(imageView);
			task.execute(keyword);
		}
	}

	/**
	 * 
	 * @param context
	 *            context
	 */
	
	private static void initCache(Context context) {
		mContext = context;
		final int memClass = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();

		final int cacheSize = 1024 * 1024 * memClass / 8;

		mMemoryCache = new LruCache<Object, Object>(cacheSize) {
			
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}

	/**
	 * 
	 * @param context
	 *            context
	 * @param bitmap
	 *            bitmap
	 * @param Bitmapid
	 *            this id is a key to storage ,if you like, you can set a random
	 *            number.
	 */
	public static void addBitmapToCache(Context context, Bitmap bitmap,
			int keyword) {
		initCache(context);
		final String imageKey = String.valueOf(keyword);
		addBitmapToMemoryCache(imageKey, bitmap);
	}

	/**
	 * 
	 * @param context
	 *            context
	 * @param resId
	 *            resId
	 * @return this Bitmap is storge at Cache
	 */
	public static Bitmap getBitmap(Context context, int keyword) {

		initCache(context);
		final String imageKey = String.valueOf(keyword);
		Bitmap bitmap = getBitmapFromMemCache(imageKey);
		if (bitmap != null) {
			return bitmap;
		} else {
			bitmap = decodeSampledBitmapFromResource(mContext, keyword);
			addBitmapToMemoryCache(String.valueOf(keyword), bitmap);
			return bitmap;
		}
	}

	private static Bitmap decodeSampledBitmapFromResource(Context context,
			int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	private static class BitmapWorkerTask extends
			AsyncTask<Integer, Void, Bitmap> {
		ImageView imageViewTask = null;

		/**
		 * 
		 * @param imageView
		 */
		BitmapWorkerTask(ImageView imageView) {
			imageViewTask = imageView;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			imageViewTask.setImageBitmap(result);
			super.onPostExecute(result);
		}

		// Decode image in background.
		protected Bitmap doInBackground(Integer... params) {
			final Bitmap bitmap = decodeSampledBitmapFromResource(mContext,
					params[0]);
			addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
			return bitmap;
		}

	}

}
