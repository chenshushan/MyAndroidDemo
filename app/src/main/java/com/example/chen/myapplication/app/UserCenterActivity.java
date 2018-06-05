package com.example.chen.myapplication.app;

import android.content.Intent;
import android.graphics.*;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import com.example.chen.myapplication.R;
import com.example.chen.myapplication.app.bean.User;
import com.example.chen.myapplication.app.util.PermissionUtils;
import com.example.chen.myapplication.app.util.PreferenceUtil;
import com.example.chen.myapplication.app.util.ToastUtil;
import com.example.chen.myapplication.app.view.TitleView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;
import com.vondear.rxtools.view.dialog.RxDialogScaleView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;
import static com.example.chen.myapplication.app.AppActivity.HOME_PAGE;
import static com.example.chen.myapplication.app.bean.User.USER_INFO;
import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

// 个人中心
public class UserCenterActivity extends BaseActivity {
	@BindView(R.id.iv_avatar)
	ImageView ivAvatar;
	@BindView(R.id.tv_username)
	TextView username;
	@BindView(R.id.tv_sex)
	TextView sex;
	@BindView(R.id.tv_phone)
	TextView phone;


	@BindView(R.id.rl_accountdetail_top)
	TitleView titleView;
	private Uri resultUri;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkLogin();

		setContentView(R.layout.activity_user_center);
		ButterKnife.bind(this);
		titleView.setTitleText("个人中心");

		User user = PreferenceUtil.getObject(USER_INFO, User.class);
		username.setText(user.getUsername());
		phone.setText(user.getPhone());
		sex.setText(user.getSex());


	}
	@OnClick({R.id.iv_avatar})
	public void showType(View view){

		PermissionUtils.checkAndRequestPermission(this, CAMERA, 111,
				new PermissionUtils.PermissionRequestSuccessCallBack() {
					@Override
					public void onHasPermission() {
						// 权限已被授予
						RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(UserCenterActivity.this, TITLE);
						dialogChooseImage.show();
					}
				});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == 111) {
			if (PermissionUtils.isPermissionRequestSuccess(grantResults)) {
				// 权限已被授予
				RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(UserCenterActivity.this, TITLE);
				dialogChooseImage.show();
			}
		}

	}
	@OnLongClick({R.id.iv_avatar})
	public boolean showBigImg(View view){
		if(resultUri == null) {
			return false;
		}
		RxDialogScaleView rxDialogScaleView = new RxDialogScaleView(this);
		rxDialogScaleView.setImageUri(resultUri);
		rxDialogScaleView.show();
		return false;
	}

	@OnClick({R.id.bt_loginactivity_exit})
	public void logout(View view) {
		PreferenceUtil.set(USER_INFO, "");
		ToastUtil.showToast("退出成功");
		Intent intent = new Intent(this, AppActivity.class);
		intent.putExtra(HOME_PAGE, 2);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
				if (resultCode == RESULT_OK) {
//                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
					initUCrop(data.getData());
				}

				break;
			case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
				if (resultCode == RESULT_OK) {
					/* data.getExtras().get("data");*/
//                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
					initUCrop(RxPhotoTool.imageUriFromCamera);
				}

				break;
			case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
				Picasso.with(this).load(RxPhotoTool.cropImageUri).error(R.mipmap.log).placeholder(R.mipmap.log).transform(new CircleTransform()).into(ivAvatar);
//                RequestUpdateAvatar(new File(RxPhotoTool.getRealFilePath(mContext, RxPhotoTool.cropImageUri)));
				break;

			case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
				if (resultCode == RESULT_OK) {
					resultUri = UCrop.getOutput(data);
					roadImageView(resultUri, ivAvatar);
					RxSPTool.putContent(this, "AVATAR", resultUri.toString());
				} else if (resultCode == UCrop.RESULT_ERROR) {
					final Throwable cropError = UCrop.getError(data);
				}
				break;
			case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
				final Throwable cropError = UCrop.getError(data);
				break;
			default:
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	//从Uri中加载图片 并将其转化成File文件返回
	private File roadImageView(Uri uri, ImageView imageView) {
		Picasso.with(this).load(uri).error(R.mipmap.log).placeholder(R.mipmap.log).transform(new CircleTransform()).into(ivAvatar);
		return (new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
	}


	private void initUCrop(Uri uri) {
		//Uri destinationUri = RxPhotoTool.createImagePathUri(this);

		SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
		long time = System.currentTimeMillis();
		String imageName = timeFormatter.format(new Date(time));

		Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

		UCrop.Options options = new UCrop.Options();
		//设置裁剪图片可操作的手势
		options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
		//设置隐藏底部容器，默认显示
		//options.setHideBottomControls(true);
		//设置toolbar颜色
		options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
		//设置状态栏颜色
		options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));

		//开始设置
		//设置最大缩放比例
		options.setMaxScaleMultiplier(5);
		//设置图片在切换比例时的动画
		options.setImageToCropBoundsAnimDuration(666);
		//设置裁剪窗口是否为椭圆
		//options.setOvalDimmedLayer(true);
		//设置是否展示矩形裁剪框
		// options.setShowCropFrame(false);
		//设置裁剪框横竖线的宽度
		//options.setCropGridStrokeWidth(20);
		//设置裁剪框横竖线的颜色
		//options.setCropGridColor(Color.GREEN);
		//设置竖线的数量
		//options.setCropGridColumnCount(2);
		//设置横线的数量
		//options.setCropGridRowCount(1);

		UCrop.of(uri, destinationUri)
				.withAspectRatio(1, 1)
				.withMaxResultSize(1000, 1000)
				.withOptions(options)
				.start(this);
	}
	// 圆角显示图片-Picasso
	class CircleTransform implements Transformation {

		@Override
		public Bitmap transform(Bitmap source) {
			int size = Math.min(source.getWidth(), source.getHeight());

			int x = (source.getWidth() - size) / 2;
			int y = (source.getHeight() - size) / 2;

			Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
			if (squaredBitmap != source) {
				source.recycle();
			}

			Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig() != null
					? source.getConfig() : Bitmap.Config.ARGB_8888);

			Canvas canvas = new Canvas(bitmap);
			Paint paint = new Paint();
			BitmapShader shader = new BitmapShader(squaredBitmap,
					BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
			paint.setShader(shader);
			paint.setAntiAlias(true);

			float r = size / 2f;
			canvas.drawCircle(r, r, r, paint);

			squaredBitmap.recycle();
			return bitmap;
		}

		@Override
		public String key() {
			return "circle";
		}
	}

}
