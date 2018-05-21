package com.example.chen.myapplication.app.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.fragment.ClassoneFragment;
import com.example.chen.myapplication.app.fragment.ClasstwoFragment;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnBannerListener {


	private Context context;

	private LayoutInflater mInflater;

	private List<Fragment> fragments;

	private FragmentManager manager;

	private RecyclerView mRv;


	public HomeAdapter(Context context, FragmentManager manager) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.manager = manager;
	}
	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		mRv = recyclerView;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case 0 :
				return new ADHolder(mInflater.inflate(R.layout.fg_ad, parent, false));
			case 1 :
				return new ClassHolder(mInflater.inflate(R.layout.fg_class, parent, false));
			case 2 :
				return new ShopHolder(mInflater.inflate(R.layout.fg_shop, parent, false));
		}
		return null;
	}

	private List<String> list_path;
	private List<String> list_title;

	private List listShop;

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		Log.i("position", "position:" + position);
		int itemViewType = getItemViewType(position);
		switch (itemViewType) {
			case 0 :
				Banner banner = ((ADHolder) holder).banner;
				// 图片轮播
				initBanner(banner);
				break;
			case 1 :
				ClassHolder classHolder = (ClassHolder) holder;
				// 设置各个分类
				initClass(classHolder);
				break;
			case 2 :
				if(listShop == null || listShop.size() == 0){
					return;
				}
				final ShopHolder holder3 = (ShopHolder) holder;
				break;
		}
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	@Override
	public int getItemCount() {
		return listShop == null || listShop.size() == 0 ? 2 : listShop.size() + 2;
	}

	@Override
	public void OnBannerClick(int position) {
		Log.i("tag", "你点了第"+position+"张轮播图");
	}

	class ADHolder extends RecyclerView.ViewHolder{
		Banner banner;
		public ADHolder(View itemView) {
			super(itemView);
			banner = (Banner) itemView.findViewById(R.id.banner);
		}
	}

	class ClassHolder extends RecyclerView.ViewHolder{

		ViewPager viewPager;
		ImageView ivClassOne;
		ImageView ivClassTwo;

		public ClassHolder(View itemView) {
			super(itemView);
			viewPager = (ViewPager) itemView.findViewById(R.id.vp_class_show);
			ivClassOne = (ImageView) itemView.findViewById(R.id.iv_class_one);
			ivClassTwo = (ImageView) itemView.findViewById(R.id.iv_class_two);
		}
	}
	class ShopHolder extends RecyclerView.ViewHolder{
		TextView textView;
		public ShopHolder(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.tx_shop);

		}
	}


	public void initClassFragment(ClassHolder holder){

	}

	private class MyLoader extends ImageLoader {

		@Override
		public void displayImage(Context context, Object path, ImageView imageView) {
			Picasso.with(context)
					.load((String)path)
					.into(imageView);
		}
	}
	// 图片轮播
	public void initBanner(Banner banner){
		//放图片地址的集合
		list_path = new ArrayList();
		//放标题的集合
		list_title = new ArrayList();


		list_path.add("http://wm.gou00.cn/uploads/images/howd0api/hm.mM4393.gif");
		list_path.add("http://wm.gou00.cn/uploads/images/howd0api/hm.CFGIte.gif");
		list_path.add("http://wm.gou00.cn/uploads/images/howd0api/hm.3xntyx.gif");
		list_title.add("万圣狂欢");
		list_title.add("火锅开涮");
		list_title.add("辣尚瘾");

		//设置内置样式，共有六种可以点入方法内逐一体验使用。
		banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
		//设置图片加载器，图片加载器在下方
		banner.setImageLoader(new MyLoader());
		//设置图片网址或地址的集合
		banner.setImages(list_path);
		//设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
		banner.setBannerAnimation(Transformer.Default);
		//设置轮播图的标题集合
		banner.setBannerTitles(list_title);
		//设置轮播间隔时间
		banner.setDelayTime(3000);
		//设置是否为自动轮播，默认是“是”。
		banner.isAutoPlay(true);
		//设置指示器的位置，小点点，左中右。
		banner.setIndicatorGravity(BannerConfig.CENTER)
				//以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
				.setOnBannerListener(this)
				//必须最后调用的方法，启动轮播图。
				.start();
	}
	// 初始化分类
	public void initClass(final ClassHolder holder){
		fragments = new ArrayList();
		ClassoneFragment one = new ClassoneFragment(context);
		ClasstwoFragment two = new ClasstwoFragment(context);
		fragments.add(one);
		fragments.add(two);

		// 设置分类的viewPager的页面
		ClassFragmentAdapter classFragmentAdapter = new ClassFragmentAdapter(manager, fragments);
		holder.viewPager.setAdapter(classFragmentAdapter);
		holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

			@Override
			public void onPageSelected(int position) {}

			@Override
			public void onPageScrollStateChanged(int state) {
				// 切换高亮小圆点
				int currentItem = holder.viewPager.getCurrentItem();
				switch (currentItem) {
					case 0:
						holder.ivClassOne.setImageResource(R.mipmap.big);
						holder.ivClassTwo.setImageResource(R.mipmap.small);
						break;
					case 1:
						holder.ivClassOne.setImageResource(R.mipmap.small);
						holder.ivClassTwo.setImageResource(R.mipmap.big);
						break;
				}
			}
		});
	}

}
