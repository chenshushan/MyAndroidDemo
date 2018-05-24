package com.example.chen.myapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.GoodsAdapter;
import com.example.chen.myapplication.app.adapter.ShopTypeAdapter;
import com.example.chen.myapplication.app.bean.GoodsItem;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.view.TitleView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import java.util.List;

public class ShopDetailActivity extends AppCompatActivity {
	private ImageView imgCart; // 购物车图片
	private ViewGroup containerLayout; // 顶层布局
	private RecyclerView rvType; // 左侧类别列表
	private RecyclerView rvSelected; // 底部弹出菜品列表
	// 已选数量 总价 结算 起送价格
	private TextView tvCount, tvCost, tvSubmit, tvTips;
	// 弹出控件布局里面包含类别和菜品
	private BottomSheetLayout bottomSheetLayout;
	// 底部弹出购物车弹出页面的顶层布局
	private View bottomSheet;
	// 右侧菜品显示控件
	private StickyListHeadersListView listViewFoods;

	private TitleView titleView;

	// 类别数据和菜品数据
	private List<GoodsItem> dataList, typeList;

	//购买的商品（类似Map）
	private SparseArray<GoodsItem> selectedList;
	private SparseIntArray groupSelect;

	//一级界面的店铺id
	private String id;
	//一级界面的店铺配送费
	private String lowPrice;
	private String shopName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_detail);
		Intent intent = getIntent();
		Shop shop = (Shop) intent.getSerializableExtra("shop");
		id = String.valueOf(shop.getId());
		shopName = shop.getName();
		lowPrice = String.format("%.0f", shop.getMinPrice()) ;

		GoodsItem.initData(id);


		try {
			typeList = GoodsItem.getTypeList(id);
			dataList = GoodsItem.getGoodsList(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		selectedList = new SparseArray<>();
		groupSelect = new SparseIntArray();

		initView();// 初始化元素
	}

	private void initView() {
		tvCount = (TextView) findViewById(R.id.tvCount);
		tvCost = (TextView) findViewById(R.id.tvCost);
		tvTips = (TextView) findViewById(R.id.tvTips);

		tvTips.setText("￥"+lowPrice+"元起送"); // 设置底部起送价格
		tvSubmit = (TextView) findViewById(R.id.tvSubmit);
		rvType = (RecyclerView) findViewById(R.id.typeRecyclerView);
		titleView = (TitleView) findViewById(R.id.tv_shop_title);
		titleView.setTitleText(shopName);
		imgCart = (ImageView) findViewById(R.id.imgCart);

		containerLayout = (RelativeLayout) findViewById(R.id.shrop_containerLayout);
		bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomSheetLayout);

		listViewFoods = (StickyListHeadersListView) findViewById(R.id.itemListView);

		rvType.setLayoutManager(new LinearLayoutManager(this));
		rvType.setAdapter(new ShopTypeAdapter(this, typeList));

		GoodsAdapter goodsAdapter = new GoodsAdapter(this, dataList);
		listViewFoods.setAdapter(goodsAdapter);
	}
}
