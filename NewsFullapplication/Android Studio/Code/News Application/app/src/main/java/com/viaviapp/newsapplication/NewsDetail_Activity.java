package com.viaviapp.newsapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.favorite.DatabaseHandler;
import com.example.favorite.Pojo;
import com.example.imageloader.ImageLoader;
import com.example.util.AnimatorUtils;
import com.example.util.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ogaclejapan.arclayout.ArcLayout;
import com.startapp.android.publish.StartAppAd;

import java.util.ArrayList;
import java.util.List;

public class NewsDetail_Activity extends ActionBarActivity implements View.OnClickListener{

	int position;
	String[] allArraynews,allArraynewsCatName;
	String[] allArrayNewsCId,allArrayNewsCatId,allArrayNewsCatImage,allArrayNewsCatName,allArrayNewsHeading,allArrayNewsImage,allArrayNewsDes,allArrayNewsDate;
	ImageView vp_imageview;
	ViewPager viewpager;
	public ImageLoader imageLoader; 
	int TOTAL_IMAGE;
	public DatabaseHandler db;
	private Menu menu;
	private AdView mAdView;
	String newscid,newscat_id,newscatimage,newscatname,newsheading,newsimage,newsdes,newsdate;
	Toolbar toolbar;

	private static final String KEY_DEMO = "demo";
	Toast toast = null;
	View fab;
	View menuLayout;
	ArcLayout arcLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StartAppAd.init(this, getString(R.string.startapp_dev_id), getString(R.string.startapp_app_id,false));
		setContentView(R.layout.newsdetail);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		toolbar.setTitle("");
		this.setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		StartAppAd.showSlider(this);
		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		db = new DatabaseHandler(this);
		setTitle("");
		Intent i=getIntent();


		fab = findViewById(R.id.fab);
		menuLayout = findViewById(R.id.menu_layout);
		arcLayout = (ArcLayout) findViewById(R.id.arc_layout);


		for (int k = 0, size = arcLayout.getChildCount(); k < size; k++) {
			arcLayout.getChildAt(k).setOnClickListener(this);
		}
		fab.setOnClickListener(this);


		position=i.getIntExtra("POSITION", 0);
		allArrayNewsCId=i.getStringArrayExtra("CATEGORY_ITEM_CID");
		allArrayNewsCatName=i.getStringArrayExtra("CATEGORY_ITEM_NAME");
		allArrayNewsCatImage=i.getStringArrayExtra("CATEGORY_ITEM_IMAGE");
		allArrayNewsCatId=i.getStringArrayExtra("CATEGORY_ITEM_CAT_ID");
		allArrayNewsImage=i.getStringArrayExtra("CATEGORY_ITEM_NEWSIMAGE");
		allArrayNewsHeading=i.getStringArrayExtra("CATEGORY_ITEM_NEWSHEADING");
		allArrayNewsDes=i.getStringArrayExtra("CATEGORY_ITEM_NEWSDESCRI");
		allArrayNewsDate=i.getStringArrayExtra("CATEGORY_ITEM_NEWSDATE");


		//TOTAL_IMAGE=allArraynews.length-1;
		viewpager=(ViewPager)findViewById(R.id.news_slider);
		imageLoader=new ImageLoader(getApplicationContext());

		ImagePagerAdapter adapter = new ImagePagerAdapter();
		viewpager.setAdapter(adapter);

		boolean found = false;
		int j1=0;
		for(int i1=0;i1<allArrayNewsCatId.length;i1++)
		{
			if(allArrayNewsCatId[i1].contains(String.valueOf(position)))
			{
				found=true;
				j1=i1;
				break; 
			}
		}
		if(found)
		{
			viewpager.setCurrentItem(j1);
		}

		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub

				position=viewpager.getCurrentItem();
				newscat_id=allArrayNewsCatId[position];

				List<Pojo> pojolist=db.getFavRow(newscat_id);
				if(pojolist.size()==0)
				{

					findViewById(R.id.btnb).setBackgroundResource(R.drawable.fav);
				}
				else
				{	
					if(pojolist.get(0).getCatId().equals(newscat_id))
					{
						findViewById(R.id.btnb).setBackgroundResource(R.drawable.fav_active);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int position) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int position) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.news_menu, menu);
		this.menu = menu;
		FirstFav();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) 
		{
		case android.R.id.home: 
			onBackPressed();
			return true;

		case R.id.menu_back:
			position=viewpager.getCurrentItem();
			position--;
			if (position < 0) {
				position = 0;
			}
			viewpager.setCurrentItem(position);
			return true;  

		case R.id.menu_next:

			position=viewpager.getCurrentItem();
			position++;
			if (position == TOTAL_IMAGE) {
				position = TOTAL_IMAGE;
			}
			viewpager.setCurrentItem(position);
			return true;



		default:
			return super.onOptionsItemSelected(menuItem);
		}

	}
	public  String RemoveTag(String html){

		html=html.replaceAll("<br/>","");

		return html;
	}
	public void AddtoFav(int position)
	{
		newscat_id=allArrayNewsCatId[position];
		newscid=allArrayNewsCId[position];
		newscatname=allArrayNewsCatName[position];
		//newscatimage=allArrayNewsCatImage[position];
		newsheading=allArrayNewsHeading[position];
		newsimage=allArrayNewsImage[position];
		newsdes=allArrayNewsDes[position];
		newsdate=allArrayNewsDate[position];

		db.AddtoFavorite(new Pojo(newscat_id,newscid,newscatname,newsheading,newsimage,newsdes,newsdate));
		Toast.makeText(getApplicationContext(), "Added to Favorite", Toast.LENGTH_SHORT).show();
		findViewById(R.id.btnb).setBackgroundResource(R.drawable.fav_active);
	}

	//remove from favorite
	public void RemoveFav(int position)
	{
		newscat_id=allArrayNewsCatId[position];
		db.RemoveFav(new Pojo(newscat_id));
		Toast.makeText(getApplicationContext(), "Removed from Favorite", Toast.LENGTH_SHORT).show();
		findViewById(R.id.btnb).setBackgroundResource(R.drawable.fav);

	}

	public void FirstFav()
	{
		int first=viewpager.getCurrentItem();
		String Image_id=allArrayNewsCatId[first];

		List<Pojo> pojolist=db.getFavRow(Image_id);
		if(pojolist.size()==0)
		{
			findViewById(R.id.btnb).setBackgroundResource(R.drawable.fav);


		}
		else
		{	
			if(pojolist.get(0).getCatId().equals(Image_id))
			{
				findViewById(R.id.btnb).setBackgroundResource(R.drawable.fav_active);

			}

		}
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private LayoutInflater inflater;

		public ImagePagerAdapter() {
			// TODO Auto-generated constructor stub

			inflater = getLayoutInflater();
		}

		@Override
		public int getCount() {
			return allArrayNewsCatId.length;

		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {

			View imageLayout = inflater.inflate(R.layout.newpager_item, container, false);
			assert imageLayout != null;

			ImageView news_imageview=(ImageView)imageLayout.findViewById(R.id.image_news);
			TextView txt_newstitle=(TextView)imageLayout.findViewById(R.id.text_newstitle);
			TextView txt_newsdate=(TextView)imageLayout.findViewById(R.id.text_newsdate);
			//TextView txt_newsdes=(TextView)imageLayout.findViewById(R.id.text_newsdes);
			WebView webnewsdes=(WebView)imageLayout.findViewById(R.id.webView_newsdes);

			imageLoader.DisplayImage(Constant.SERVER_IMAGE_NEWSLISTDETAILS+allArrayNewsImage[position], news_imageview);

			txt_newstitle.setText(allArrayNewsHeading[position]);
			txt_newsdate.setText(allArrayNewsDate[position]);
			//txt_newsdes.setText(allArrayNewsDes[position]);
			webnewsdes.setBackgroundColor(Color.parseColor(getString(R.color.background_color)));
			webnewsdes.setFocusableInTouchMode(false);
			webnewsdes.setFocusable(false);
			webnewsdes.getSettings().setDefaultTextEncodingName("UTF-8");

			String mimeType = "text/html;charset=UTF-8";
			String encoding = "utf-8";
			String htmlText = allArrayNewsDes[position];

			String text = "<html><head>"
					+ "<style type=\"text/css\">body{color: #525252;text-align:justify}"
					+ "</style></head>"
					+ "<body>"                          
					+  htmlText
					+ "</body></html>";

			webnewsdes.loadData(text, mimeType, encoding);

			container.addView(imageLayout, 0);
			return imageLayout;

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}
	}
	@Override
	protected void onPause() {
		//mAdView.pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//mAdView.resume();
	}

	@Override
	protected void onDestroy() {
		//mAdView.destroy();
		super.onDestroy();
	}



	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.fab) {
			onFabClick(v);
			return;
		}


		if (v instanceof Button) {
			showToast((Button) v);
		}

	}

	private void showToast(Button btn) {
		if (toast != null) {
			toast.cancel();
		}
		if (btn.getId() == R.id.btna) {

			position=viewpager.getCurrentItem();
			newsheading=allArrayNewsHeading[position];
			newsdes=allArrayNewsDes[position];
			String formattedString=android.text.Html.fromHtml(newsdes).toString();
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, newsheading+"\n"+formattedString+"\n"+" I Would like to share this with you. Here You Can Download This Application from PlayStore "+"https://play.google.com/store/apps/details?id="+getPackageName());
			sendIntent.setType("text/plain");
			startActivity(sendIntent); 
			hideMenu(); 
			 

		}

		if (btn.getId() == R.id.btnb) {
			position=viewpager.getCurrentItem();
			newscat_id=allArrayNewsCatId[position];

			List<Pojo> pojolist=db.getFavRow(newscat_id);
			if(pojolist.size()==0)
			{
				AddtoFav(position);//if size is zero i.e means that record not in database show add to favorite 
			}
			else
			{	
				if(pojolist.get(0).getCatId().equals(newscat_id))
				{
					RemoveFav(position);
				}
			}
			hideMenu(); 
		}

		if (btn.getId() == R.id.btnc)
		{
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT," I Would like to share this with you. Here You Can Download This Application from PlayStore "+"https://play.google.com/store/apps/details?id="+getPackageName());
			sendIntent.setType("text/plain");
			startActivity(sendIntent); 
			hideMenu();
		}
		if (btn.getId() == R.id.btnd)
		{
			final String appName = getPackageName();//your application package name i.e play store application url
			try {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("market://details?id="
								+ appName)));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://play.google.com/store/apps/details?id="
								+ appName)));
			}
			 
		}
		hideMenu();

	}

	private void onFabClick(View v) {
		if (v.isSelected()) {
			hideMenu();
		} else {
			showMenu();
		}
		v.setSelected(!v.isSelected());
	}

	@SuppressWarnings("NewApi")
	private void showMenu() {
		menuLayout.setVisibility(View.VISIBLE);

		List<Animator> animList = new ArrayList<Animator>();

		for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
			animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
		}

		AnimatorSet animSet = new AnimatorSet();
		animSet.setDuration(400);
		animSet.setInterpolator(new OvershootInterpolator());
		animSet.playTogether(animList);
		animSet.start();
	}

	@SuppressWarnings("NewApi")
	private void hideMenu() {

		List<Animator> animList = new ArrayList<Animator>();

		for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
			animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
		}

		AnimatorSet animSet = new AnimatorSet();
		animSet.setDuration(400);
		animSet.setInterpolator(new AnticipateInterpolator());
		animSet.playTogether(animList);
		animSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				menuLayout.setVisibility(View.INVISIBLE);
			}
		});
		animSet.start();

	}

	private Animator createShowItemAnimator(View item) {

		float dx = fab.getX() - item.getX();
		float dy = fab.getY() - item.getY();

		item.setRotation(0f);
		item.setTranslationX(dx);
		item.setTranslationY(dy);

		Animator anim = ObjectAnimator.ofPropertyValuesHolder(
				item,
				AnimatorUtils.rotation(0f, 720f),
				AnimatorUtils.translationX(dx, 0f),
				AnimatorUtils.translationY(dy, 0f)
				);

		return anim;
	}

	private Animator createHideItemAnimator(final View item) {
		float dx = fab.getX() - item.getX();
		float dy = fab.getY() - item.getY();

		Animator anim = ObjectAnimator.ofPropertyValuesHolder(
				item,
				AnimatorUtils.rotation(720f, 0f),
				AnimatorUtils.translationX(0f, dx),
				AnimatorUtils.translationY(0f, dy)
				);

		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				item.setTranslationX(0f);
				item.setTranslationY(0f);
			}
		});

		return anim;
	}

}
