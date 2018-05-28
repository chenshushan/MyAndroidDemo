package com.example.chen.myapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.adapter.GoodsAdapter;
import com.example.chen.myapplication.app.adapter.GoodsSelectedAdapter;
import com.example.chen.myapplication.app.adapter.ShopTypeAdapter;
import com.example.chen.myapplication.app.bean.GoodsItem;
import com.example.chen.myapplication.app.bean.Shop;
import com.example.chen.myapplication.app.view.TitleView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.gson.Gson;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import java.util.List;
// 店铺详情页
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
	private String lowPrice; // 起送价
	private String shopName; // 店铺名称
	GoodsAdapter goodsAdapter; // 右侧菜品adapter
	ShopTypeAdapter shopTypeAdapter; // 左侧类别adapter
	GoodsSelectedAdapter selectedAdapter; // 底部购物车已选菜品adapter
	Shop shop; // 店铺信息
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_detail);
		Intent intent = getIntent();
		shop = (Shop) intent.getSerializableExtra("shop");
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
		tvSubmit.setOnClickListener(this);
		containerLayout = (RelativeLayout) findViewById(R.id.shrop_containerLayout);
		bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomSheetLayout);

		listViewFoods = (StickyListHeadersListView) findViewById(R.id.itemListView);
		// 左侧类别初始化
		rvType.setLayoutManager(new LinearLayoutManager(this));
		shopTypeAdapter = new ShopTypeAdapter(this, typeList);
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

	//根据类别Id获取属于当前类别的数量
	public int getSelectedGroupCountByTypeId(int typeId) {
		return groupSelect.get(typeId);
	}

	//根据商品id获取当前商品的采购数量
	public int getSelectedItemCountById(int id) {
		GoodsItem temp = selectedList.get(id);
		if (temp == null) {
			return 0;
		}
		return temp.count;
	}

	/**
	 * @param item
	 * @param refresh false:点击商品添加时不用刷新商品item  true:点击购物车添加减少时需要刷新商品item
	 */
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

	// 移除商品
	public void removeFood(GoodsItem item, boolean refresh){

		int groupCount = groupSelect.get(item.typeId);
		if(groupCount == 1) {
			groupSelect.delete(item.typeId);
		} else {
			groupSelect.append(item.typeId, --groupCount);
		}

		GoodsItem temp = selectedList.get(item.id);
		if(temp != null) {
			if(temp.count < 2) {
				selectedList.remove(item.id);
			} else {
				item.count--;
			}
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

		//购物车 配送费判断 结算按钮显示隐藏
		if (cost >= Double.valueOf(lowPrice)) {
			tvTips.setVisibility(View.GONE);
			tvSubmit.setVisibility(View.VISIBLE);
		} else {
			tvSubmit.setVisibility(View.GONE);
			tvTips.setVisibility(View.VISIBLE);
		}

		tvCost.setText(String.format("%.2f",cost));

		// 刷新菜品列表的数量显示
		if (goodsAdapter != null && refresh) {
			goodsAdapter.notifyDataSetChanged();
		}
		// 刷新底部购物车
		if (selectedAdapter != null) {
			selectedAdapter.notifyDataSetChanged();
		}
		// 刷新左侧分类列表的数量显示
		if (shopTypeAdapter != null) {
			shopTypeAdapter.notifyDataSetChanged();
		}
		// 如果没有选中的菜品了 隐藏弹出的购物车
		if (bottomSheetLayout.isSheetShowing() && selectedList.size() < 1) {
			bottomSheetLayout.dismissSheet();
		}

	}


	@Override
	public void onClick(View view) { // 页面元素绑定了点击事件
		switch (view.getId()) {
			case R.id.shopcar_bottom:  // 点击底部 弹出购物车
				showBottomSheet();
			break;
			case R.id.clear:
				clearCart();
				break;
			case R.id.tvSubmit:{ //结算
				Intent intent = new Intent(this, SettlementActivity.class);
				Gson gson = new Gson();

				String json = gson.toJson(selectedList);
				String shopJson = gson.toJson(shop);

				intent.putExtra("data", json);
				intent.putExtra("shopJson", shopJson);
				startActivity(intent);
			}
			break;

		}
	}
	// 弹出底部购物车
	private void showBottomSheet() {
		if(bottomSheet == null) {
			bottomSheet = createBottomSheetView();
		}
		if(bottomSheetLayout.isSheetShowing()){ // 如果是显示的 点击之后隐藏
			bottomSheetLayout.dismissSheet();
		}else {
			if(selectedList.size() != 0) { // 如果是隐藏的 点击之后需要有选中菜品才弹出
				bottomSheetLayout.showWithSheetView(bottomSheet);
			}
		}
	}

	// 创建底部购物车View
	private View createBottomSheetView() {

		View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet, (ViewGroup) getWindow().getDecorView(), false);
		rvSelected = (RecyclerView) view.findViewById(R.id.selectRecyclerView); // 购物车商品列表
		rvSelected.setLayoutManager(new LinearLayoutManager(this));
		selectedAdapter = new GoodsSelectedAdapter(this, selectedList);
		rvSelected.setAdapter(selectedAdapter);


		TextView clear = (TextView) view.findViewById(R.id.clear);
		clear.setOnClickListener(this);
		return view;
	}

	//清空购物车
	public void clearCart() {
		selectedList.clear();
		groupSelect.clear();
		update(true);
	}
}
