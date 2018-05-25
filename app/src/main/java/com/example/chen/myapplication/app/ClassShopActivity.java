package com.example.chen.myapplication.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.HomeAdapter;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.service.ShopService;
import com.example.chen.myapplication.app.view.TitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// 店铺分类的列表页
public class ClassShopActivity extends AppCompatActivity implements HomeAdapter.OnItemClickListener {

	SearchView searchView;

	RecyclerView recyclerView;

	TitleView titleView;
	ClassTypeAdatper classTypeAdatperTemp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_shops);

		searchView = (SearchView) findViewById(R.id.sv_class_type);
		recyclerView = (RecyclerView) findViewById(R.id.rv_class_type_list);
		titleView = (TitleView) findViewById(R.id.tv_type_title);

		// 得到类型 设置title
		Intent intent = getIntent();
		String type = intent.getStringExtra("type");
		titleView.setTitleText(type);
		// 初始化recyclerView
		final ClassTypeAdatper classTypeAdatper = new ClassTypeAdatper(this, getSupportFragmentManager());
		classTypeAdatperTemp = classTypeAdatper;
		classTypeAdatper.setOnItemClickListener(this);
		final ShopService shopService = new ShopService();
		classTypeAdatper.setList_shops(shopService.getShops());
		recyclerView.setAdapter(classTypeAdatper);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		// 进入时隐藏键盘
		searchView.setIconifiedByDefault(false);
		searchView.setFocusable(false);
		searchView.setIconified(true);


		// 设置搜索框文本监听
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
				// 得到输入管理对象
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					// 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
					imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
				}

				List<Shop> shops = shopService.getShops();
				List<Shop> shopList = shops.subList(0, new Random().nextInt(9) + 1);
				classTypeAdatper.setShopData(shopList, true);
				searchView.clearFocus(); // 不获取焦点
				return true;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				if(!"".equals(s)) {
					return false;
				}
				List<Shop> shops = shopService.getShops();
				classTypeAdatper.setShopData(shops, true);
				searchView.clearFocus(); // 不获取焦点
				return true;

			}
		});
		// 下拉加载更多
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView rv, int newState) {
				super.onScrollStateChanged(rv, newState);
				LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
				//不滚动时
				if (newState == RecyclerView.SCROLL_STATE_IDLE){
					//最后一个item
					int lastItem = manager.findLastVisibleItemPosition();
					//所有的item
					int totalItemCount = manager.getItemCount();
					String s = searchView.getQuery().toString();
					if (lastItem == totalItemCount -1 && s.equals("")){ // 滚动到最后一项时加载数据 并且搜索框没有数据
						List<Shop> shops = new ShopService().getShops();
						classTypeAdatper.setShopData(shops, false);
					}

				}
			}
		});


	}

	@Override
	public void onItemClick(int position) {
		Intent intent = new Intent(this, ShopDetailActivity.class);

		Shop shop = classTypeAdatperTemp.list_shops.get(position);
		intent.putExtra("shop",shop);
		startActivity(intent);
	}


	class ClassTypeAdatper extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements   View.OnClickListener{
		private LayoutInflater mInflater;

		private Context context;
		private FragmentManager manager;
		private List<Shop> list_shops;
		private HomeAdapter homeAdapter;
		private HomeAdapter.OnItemClickListener mOnItemClickListener;

		public ClassTypeAdatper(Context context, FragmentManager manager) {
			this.context = context;
			mInflater = LayoutInflater.from(context);
			this.manager = manager;
			this.homeAdapter = new HomeAdapter(context, manager);
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new HomeAdapter.ShopHolder(mInflater.inflate(R.layout.fg_shop, parent, false));
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			final HomeAdapter.ShopHolder shopHolder = (HomeAdapter.ShopHolder) holder;
			homeAdapter.initShopList(shopHolder, position + 2, list_shops);
			shopHolder.getItem().setOnClickListener(this);
		}

		public void setList_shops(List<Shop> list_shops) {
			setShopData(list_shops, false);
		}

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

		//点击的方法
		public void setOnItemClickListener(HomeAdapter.OnItemClickListener mOnItemClickListener) {
			this.mOnItemClickListener = mOnItemClickListener;
		}


		@Override
		public int getItemCount() {
			return list_shops == null ? 0 : list_shops.size();
		}

		@Override
		public void onClick(View view) {
			if(recyclerView == null){
				return;
			}
			int position = recyclerView.getChildAdapterPosition(view);
			if(mOnItemClickListener != null) {
				mOnItemClickListener.onItemClick(position);
			}
		}
	}

}
