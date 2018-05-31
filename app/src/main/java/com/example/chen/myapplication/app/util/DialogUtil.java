package com.example.chen.myapplication.app.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogUtil {


	/**
	 * 提问框的 Listener
	 *
	 */
	// 因为本类不是activity所以通过继承接口的方法获取到点击的事件
	public interface OnClickYesListener {
		abstract void onClickYes();
	}

	/**
	 * 提问框的 Listener
	 *
	 */
	public interface OnClickNoListener {
		abstract void onClickNo();
	}

	public static void showQuestionDialog(Context context, String title,
										  String text, final OnClickYesListener listenerYes){
		showQuestionDialog(context, title, text,  listenerYes, null);
	}

	public static void showQuestionDialog(Context context, String title,
										  String text, final OnClickYesListener listenerYes,
										  final OnClickNoListener listenerNo) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

//		if (text != null && text.trim().length() > 0) {
//			// 此方法为dialog写布局
//			final TextView textView = new TextView(context);
//			textView.setText(text);
//			LinearLayout layout = new LinearLayout(context);
//			layout.setPadding(10, 0, 10, 0);
//			layout.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
//					LinearLayout.LayoutParams.WRAP_CONTENT));
//			builder.setView(layout);
//		}
		// 设置title
		builder.setMessage(text).setTitle(title);
		// 设置确定按钮，固定用法声明一个按钮用这个setPositiveButton
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// 如果确定被电击
						if (listenerYes != null) {
							listenerYes.onClickYes();
						}
					}
				});
		// 设置取消按钮，固定用法声明第二个按钮要用setNegativeButton
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// 如果取消被点击
						if (listenerNo != null) {
							listenerNo.onClickNo();
						}
					}
				});

		// 控制这个dialog可不可以按返回键，true为可以，false为不可以
		builder.setCancelable(false);
		// 显示dialog
		builder.create().show();

	}

}
