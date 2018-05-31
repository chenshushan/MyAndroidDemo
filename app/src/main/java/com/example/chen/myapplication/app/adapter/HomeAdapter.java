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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.fragment.ClassoneFragment;
import com.example.chen.myapplication.app.fragment.ClasstwoFragment;
import com.example.chen.myapplication.app.service.ShopService;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnBannerListener, View.OnClickListener {


	private Context context;

	private LayoutInflater mInflater;

	private List<Fragment> fragments;

	private FragmentManager manager;

	private RecyclerView mRv;

	private ShopService shopService;
	private OnItemClickListener mOnItemClickListener;

	private BDLocation bdLocation;

	private int type;

	public static final int CLASS_TYPE = 2;
	public HomeAdapter(Context context, FragmentManager manager, int type) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.manager = manager;
		shopService = new ShopService();
		list_shops = shopService.getShops();
		this.type = type;
	}

	public HomeAdapter(Context context, FragmentManager manager) {
		this(context, manager, 0);
	}
	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		mRv = recyclerView;
	}
	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		}
		if (position == 1) {
			return 1;
		} else {
			return 2;
		}
	}
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		if(type == CLASS_TYPE){
			return new ShopHolder(mInflater.inflate(R.layout.fg_shop, parent, false));
		}

		switch (viewType) {// 根据自己覆写的getItemViewType得到
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

	private List<Shop> list_shops;

	public List<Shop> getList_shops() {
		return list_shops;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		int itemViewType = getItemViewType(position);
		if(type == CLASS_TYPE) {
			itemViewType = 2;
		}
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
				final ShopHolder shopHolder = (ShopHolder) holder;
				initShopList(shopHolder, position, list_shops);
				// 设置店铺的点击事件 触发实现的onClick  在其中调用mOnItemClickListener的click 在其他View中实现OnItemClickListener进行跳转
				shopHolder.item.setOnClickListener(this);
				break;
		}
	}

	@Override
	public void onClick(View view) {
		if(mRv == null){
			return;
		}
		int position = mRv.getChildAdapterPosition(view);
		if(mOnItemClickListener != null) {
			if(type != CLASS_TYPE){
				position = position - 2;
			}
			mOnItemClickListener.onItemClick(position);
		}
	}

	// recyclerview的点击事件 在ClassShopActivity中实现 在接口方法中使用Intent跳转
	// 在holder的点击事件中 回调该接口
	public interface OnItemClickListener {
		void onItemClick(int position);
	}

	//点击的方法
	public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
		this.mOnItemClickListener = mOnItemClickListener;
	}



	@Override
	public int getItemCount() {
		if(type == CLASS_TYPE) {
			return list_shops == null ? 0 : list_shops.size();
		}
		return list_shops == null ? 2 : list_shops.size() + 2;
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
	public static class ShopHolder extends RecyclerView.ViewHolder{

		ImageView ivShopIcon; //店铺图片
		TextView tvShopTitle; // 店铺名称
		TextView tvShopSell;// 店铺已售份数
		TextView tvShopDistance;// 距离
		TextView tvShopFirst;// 首单立减
		LinearLayout llShopShou;// 首单文字布局
		TextView tvShopJian;// 满减
		LinearLayout llShopJian; // 满减文字布局
		TextView tvShopLowest;// 起送价格
		TextView tvShopSend;// 配送费
		View item;
		public ShopHolder(View itemView) {
			super(itemView);
			ivShopIcon = (ImageView)itemView.findViewById(R.id.iv_shop_icon);
			tvShopTitle = (TextView)itemView.findViewById(R.id.tv_shop_title);
			tvShopSell = (TextView)itemView.findViewById(R.id.tv_shop_sell);
			tvShopDistance = (TextView)itemView.findViewById(R.id.tv_shop_distance);
			tvShopFirst = (TextView)itemView.findViewById(R.id.tv_shop_first);
			llShopShou = (LinearLayout)itemView.findViewById(R.id.ll_shop_shoudan);
			tvShopJian = (TextView)itemView.findViewById(R.id.tv_shop_jian);
			llShopJian = (LinearLayout)itemView.findViewById(R.id.ll_shop_jian);
			tvShopLowest = (TextView)itemView.findViewById(R.id.tv_shop_lowest);
			tvShopSend = (TextView)itemView.findViewById(R.id.tv_shop_send);
			item = itemView;
		}

		public View getItem() {
			return item;
		}
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

	public void initShopList(ShopHolder shopHolder, int position, List<Shop> shops){
		if(type != CLASS_TYPE) {
			position = position - 2;
		}
		Shop shop = shops.get(position);
		Picasso.with(context).load(shop.getPicUrl()).error(R.mipmap.log).placeholder(R.mipmap.log).into(shopHolder.ivShopIcon);
		shopHolder.tvShopTitle.setText(shop.getName() + "饭店");
		shopHolder.tvShopSell.setText("已售" + shop.getHasSell() + "份");
		shopHolder.tvShopDistance.setText(shop.getDistance() + "km");
		shopHolder.tvShopFirst.setText("首单立减" + String.format("%.0f", shop.getShoudan()));
		String text = "满" + String.format("%.0f", shop.getFull()) + "减" + String.format("%.0f", shop.getJian());
		shopHolder.tvShopJian.setText(text);

		shopHolder.tvShopLowest.setText(String.format("%.0f", shop.getMinPrice()));
		shopHolder.tvShopSend.setText(String.valueOf(shop.getPeisong()));

	}
	//店铺数据
	public void setShopData(List<Shop> data, boolean isRefresh) {
		if (this.list_shops == null) {
			this.list_shops = new ArrayList<>();
		}
		if (isRefresh) {
			this.list_shops.clear();
		}
		this.list_shops.addAll(data);
		notifyDataSetChanged();
	}

	public void setBdLocation(BDLocation bdLocation) {
		this.bdLocation = bdLocation;
	}
}
