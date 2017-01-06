package freaktemplate.singlerestau.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import freaktemplate.singlerestau.R;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	String[] data;

	public ImageAdapter(Context c, String[] d) {
		mContext = c;
		data = d;

	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		return data[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// Create a new ImageView for each item referenced by the Adapter
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;

		if (convertView == null) { // If it's not recycled, initialize some
			// attributes
			DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
			int screenWidth = metrics.widthPixels;
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(320, 250));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setPaddingRelative(5, 5, 5, 5);
			// imageView.setPadding(8, 8, 8, 8);
			imageView.setBackgroundResource(R.drawable.view_default);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageResource(R.drawable.home_preloadimg);
		String imageurl = mContext.getString(R.string.liveurl)+"uploads/slide/"
				+ data[position].replace("[", "").replace("]", "").replace("\"", "");
		// imageView.setImageReso(imageurl);
		ImageLoader imgLoader = new ImageLoader(mContext);
		imgLoader.DisplayImage(imageurl.replace(" ", "%20"), imageView);

		return imageView;
	}

	// References to our images in res > drawable
	

}
