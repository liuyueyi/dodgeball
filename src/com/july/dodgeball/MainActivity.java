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

		// ���ô����ޱ�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ȥ��ǿ����Ļװ�Σ���״̬������������
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
		// ��ʼ��Ӧ�õķ��� ID ����Կ���Լ����ò���ģʽ
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
				// ��ʾ�������
				// �жϲ�������Ƿ��ѳ�ʼ����ɣ�����ȷ���Ƿ��ܳɹ����ò������
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

			case Constants.EXIT: // �˳�
				// �����˳��Ի���
				AlertDialog isExit = new AlertDialog.Builder(mContext).create();
				// ���öԻ������
				isExit.setTitle("ϵͳ��ʾ");
				// ���öԻ�����Ϣ
				isExit.setMessage("ȷ��Ҫ�˳���");
				// ���ѡ��ť��ע�����
				isExit.setButton("ȷ��", listener);
				isExit.setButton2("ȡ��", listener);
				// ��ʾ�Ի���
				isExit.show();
				break;
			case Constants.SHARE:
				// ���÷�������
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
		msg.what = adTag; // ˽�о�̬�����ͱ����������������ж���ֵ
		handler.sendMessage(msg);
	}

	/** �����Ի��������button����¼� */
	static DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "ȷ��"��ť�˳�����
				Gdx.app.exit();
				((Activity) mContext).finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "ȡ��"�ڶ�����ťȡ���Ի���
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