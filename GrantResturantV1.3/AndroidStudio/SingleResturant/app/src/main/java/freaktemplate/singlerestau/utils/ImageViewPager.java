package freaktemplate.singlerestau.utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import freaktemplate.singlerestau.R;


public class ImageViewPager extends Activity {

	int position;
	String[] pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {/*
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_view_pager);

		Intent p = getIntent();
		position = p.getExtras().getInt("id");

		ImageAdapter imageAdapter = new ImageAdapter(ImageViewPager.this);
		List<ImageView> images = new ArrayList<ImageView>();

		// Retrieve all the images
		for (int i = 0; i < imageAdapter.getCount(); i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imageAdapter.mThumbIds[i]);
			imageView.setScaleType(ImageView.ScaleType.CENTER);
			images.add(imageView);
		}

		// Set the images into ViewPager
		ImagePagerAdapter pageradapter = new ImagePagerAdapter(images);
		ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
		viewpager.setAdapter(pageradapter);
		// Show images following the position
		viewpager.setCurrentItem(position);
	*/}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_view_pager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
