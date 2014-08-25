package com.july.dodgeball;

import net.youmi.android.AdManager;
import net.youmi.android.diy.DiyManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import cn.domob.android.ads.DomobAdView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
	static RelativeLayout layout;
	public static final String PUBLISHER_ID = "56OJwoVIuNCY1uXQpj";
	public static final String InlinePPID = "16TLuUEoApvzHNU0xTeOWCKi";
	public static final String ChaPinID = "16TLuUEoApvzHNU05bS9ORsk";

	static DomobAdView MyAdview320x50;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置窗口无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 去除强制屏幕装饰（如状态条）弹出设置
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		mContext = this;

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;

		// initialize(new MainGame(this), cfg);

		View gameView = initializeForView(new MainGame(this), cfg);
		layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		MyAdview320x50 = new DomobAdView(this, PUBLISHER_ID, InlinePPID);
		MyAdview320x50.setAdSize(DomobAdView.INLINE_SIZE_320X50);

		layout.addView(gameView);
		layout.addView(MyAdview320x50, adParams);

		setContentView(layout);
		initAd();
	}

	public void initAd() {
		// 初始化应用的发布 ID 和密钥，以及设置测试模式
		AdManager.getInstance(this).init("9528976fa670d826", "f3c831a44ab303ee", false);
		SpotManager.getInstance(this).loadSpotAds();
		AdManager.getInstance(this).setUserDataCollect(true);
	}

	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(final Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constants.CHAPIN:
				// 显示插屏广告
				// 判断插屏广告是否已初始化完成，用于确定是否能成功调用插屏广告
				SpotManager.getInstance(mContext).showSpotAds(mContext,
						new SpotDialogListener() {
							@Override
							public void onShowSuccess() {
								Log.i("Youmi", "onShowSuccess");
							}

							@Override
							public void onShowFailed() {
								Log.i("Youmi", "onShowFailed");
							}
						});
				break;
			case Constants.TUIGUANG:
				DiyManager.showRecommendWall(mContext);
				break;

			case Constants.EXIT: // 退出
				// 创建退出对话框
				AlertDialog isExit = new AlertDialog.Builder(mContext).create();
				// 设置对话框标题
				isExit.setTitle("系统提示");
				// 设置对话框消息
				isExit.setMessage("确定要退出吗");
				// 添加选择按钮并注册监听
				isExit.setButton("确定", listener);
				isExit.setButton2("取消", listener);
				// 显示对话框
				isExit.show();
				break;
			case Constants.SHARE:
				// 设置分享内容
				break;
			case Constants.CLOSE_BANNER:
				layout.removeView(MyAdview320x50);
				break;
			case Constants.SHOW_BANNER:
				layout.addView(MyAdview320x50);
				break;
			}

		}
	};

	private static Context mContext;

	public void showAdStatic(int adTag) {
		Message msg = handler.obtainMessage();
		msg.what = adTag; // 私有静态的整型变量，开发者请自行定义值
		handler.sendMessage(msg);
	}

	/** 监听对话框里面的button点击事件 */
	static DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				Gdx.app.exit();
				((Activity) mContext).finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onDestroy() {
		SpotManager.getInstance(this).unregisterSceenReceiver();
		Gdx.app.exit();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {

	}
}