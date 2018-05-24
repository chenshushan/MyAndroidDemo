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
import android.widget.AbsListView;
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

public class ShopDetailActivity extends AppCompatActivity implements View.OnClickListener{
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
	private SparseArray<GoodsItem> selectedList; // 每种商品的数量
	private SparseIntArray groupSelect; // 每种类别的数量

	//一级界面的店铺id
	private String id;
	//一级界面的店铺配送费
	private String lowPrice;
	private String shopName;
	GoodsAdapter goodsAdapter;
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
		// 左侧类别初始化
		rvType.setLayoutManager(new LinearLayoutManager(this));
		final ShopTypeAdapter shopTypeAdapter = new ShopTypeAdapter(this, typeList);
		rvType.setAdapter(shopTypeAdapter);

		goodsAdapter = new GoodsAdapter(this, dataList);
		listViewFoods.setAdapter(goodsAdapter);

		listViewFoods.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int i) {

			}
			// 点击左侧滑动也会调用此方法
			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(dataList == null || dataList.size() == 0) {
					return;
				}
				GoodsItem goodsItem = dataList.get(firstVisibleItem);
				if (shopTypeAdapter.selectTypeId != goodsItem.typeId) {
					shopTypeAdapter.selectTypeId = goodsItem.typeId;
					shopTypeAdapter.notifyDataSetChanged(); // 触发onBindViewHolder 会设置点击项的背景色
					rvType.smoothScrollToPosition(getSelectedGroupPosition(goodsItem.typeId));
				}

			}
		});
	}

	/** 得到选中左侧选中类别的位置
	 * @param typeId
	 * @return
	 */
	private int getSelectedGroupPosition(int typeId) {
		for (int i = 0; i < typeList.size(); i++) {
			if (typeId == typeList.get(i).typeId) {
				return i;
			}
		}
		return 0;
	}

	public void onTypeClicked(int typeId) {
		// 点击类别之后触发滚动onScroll事件
		listViewFoods.setSelection(getSelectedPosition(typeId));
	}

	/** 得到左侧选中的是哪个类别
	 * @param typeId
	 * @return
	 */
	private int getSelectedPosition(int typeId) {
		int position = 0;
		for (int i = 0; i < dataList.size(); i++) {
			if (dataList.get(i).typeId == typeId) {
				position = i;
				break;
			}
		}
		return position;
	}

	//根据商品id获取当前商品的采购数量
	public int getSelectedItemCountById(int id) {
		GoodsItem temp = selectedList.get(id);
		if (temp == null) {
			return 0;
		}
		return temp.count;
	}

	public void addFood(GoodsItem item, boolean refresh){
		int groupCount = groupSelect.get(item.typeId);
		if(groupCount == 0) {
			groupSelect.append(item.typeId, 1);
		}else {
			groupSelect.append(item.typeId, ++groupCount);
		}

		GoodsItem temp = selectedList.get(item.id);
		if(temp == null){
			item.count = 1;
			selectedList.append(item.id, item);
		}else {
			temp.count++;
		}
		update(refresh);
	}
	//刷新布局 总价、购买数量等
	private void update(boolean refresh) {
		int size = selectedList.size();
		int count = 0;
		double cost = 0;
		// 计算总价
		for (int i = 0; i < size; i++) {
			GoodsItem item = selectedList.valueAt(i);
			count += item.count;
			cost += item.count * item.price;
		}

		if (count < 1) { // 隐藏数量
			tvCount.setVisibility(View.GONE);
		} else {
			tvCount.setVisibility(View.VISIBLE);
		}
		tvCount.setText(String.valueOf(count));

		//购物车 配送费判断
		if (cost >= Double.valueOf(lowPrice)) {
			tvTips.setVisibility(View.GONE);
			tvSubmit.setVisibility(View.VISIBLE);
		} else {
			tvSubmit.setVisibility(View.GONE);
			tvTips.setVisibility(View.VISIBLE);
		}

		tvCost.setText(String.format("%.2f",cost));

		if (goodsAdapter != null && refresh) {
			goodsAdapter.notifyDataSetChanged();
		}

//		if (selectAdapter != null) {
//			selectAdapter.notifyDataSetChanged();
//		}
//		if (typeAdapter != null) {
//			typeAdapter.notifyDataSetChanged();
//		}
//		if (bottomSheetLayout.isSheetShowing() && selectedList.size() < 1) {
//			bottomSheetLayout.dismissSheet();
//		}
//

	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		}
	}
}
