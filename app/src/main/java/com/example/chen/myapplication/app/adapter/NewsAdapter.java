package com.example.chen.myapplication.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.WebViewActivity;
import com.example.chen.myapplication.app.bean.News;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
	Context context;
	List<News> newsList;
	LayoutInflater inflater;

	public NewsAdapter(Context context, List<News> newsList) {
		this.context = context;
		this.newsList = newsList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.list_news_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		News news = newsList.get(position);
		holder.bind(news);
	}


	@Override
	public int getItemCount() {
		return newsList == null ? 0 : newsList.size();
	}



	class ViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.news_image)
		ImageView newsImage;
		@BindView(R.id.txt_title)
		TextView txtTitle;
		@BindView(R.id.tv_source)
		TextView tvSource;
		@BindView(R.id.txt_content)
		TextView txtContent;
		News news;
		ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}

		public void bind(News news) {
			this.news = news;
			txtTitle.setText(news.getTitle());
			tvSource.setText(news.getSource());
			txtContent.setText(news.getContent());
			List<String> imageUrls = news.getImageUrls();
			String imageUrl = imageUrls == null || imageUrls.size() < 1 ? "" : imageUrls.get(0);
			if(imageUrl !=null && !"".equals(imageUrl.trim())) {
				System.out.println(imageUrl);
				Picasso.with(context).load(imageUrl).error(R.mipmap.log).placeholder(R.mipmap.log).into(this.newsImage);
			}
		}

		@OnClick(R.id.view_content)
		public void onViewClicked(View view) {
			String url = news.getUrl();
			// 打开网址
			Intent intent = new Intent(context, WebViewActivity.class);
			intent.putExtra("url", url);
			context.startActivity(intent);
		}

	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList, boolean isAppend) {
		if(isAppend) {
			this.newsList.addAll(newsList);
		} else {
			this.newsList = newsList;
		}
		notifyDataSetChanged();
	}
	private int page = 1;

	public int getPage() {
		return page++;
	}
}
