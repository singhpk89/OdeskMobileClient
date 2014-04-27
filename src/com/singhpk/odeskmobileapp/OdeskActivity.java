package com.singhpk.odeskmobileapp;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class OdeskActivity extends Activity {

	private WebView wView ;
	private boolean appLaunch = true;
	private TextView blankView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_score_board);
		wView = (WebView)findViewById(R.id.webview);
		wView.loadUrl(AppConstant.STARTUP_LINK);
		//wView.setBackgroundColor(getResources().getColor(R.color.background_odesk));
		wView.getSettings().setJavaScriptEnabled(true);
		wView.getSettings().setBuiltInZoomControls(true);
		wView.getSettings().setAllowFileAccess(true);
		wView.requestFocus(View.FOCUS_DOWN);
		wView.getSettings().setDatabaseEnabled(true);
		wView.getSettings().setDomStorageEnabled(true);
		wView.getSettings().setRenderPriority(RenderPriority.HIGH);
		wView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		blankView = (TextView)findViewById(R.id.blankview);
	//	setBlankViewHidden(true);



		wView.setWebViewClient(new SSLTolerentWebViewClient());

	}


	/*private void setBlankViewHidden(boolean doHide) {
		if(doHide)
		{
		blankView.setVisibility(View.INVISIBLE);
		wView.setVisibility(View.VISIBLE);
		}else
		{
			blankView.setVisibility(View.VISIBLE);
			wView.setVisibility(View.INVISIBLE);
		}
		
	}*/


	private class SSLTolerentWebViewClient extends WebViewClient {

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			//setBlankViewHidden(false);
			
		}
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed(); // Ignore SSL certificate errors
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			Progresss.stop();
		}
		public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
			//setBlankViewHidden(false);
			if(appLaunch){Progresss.start(OdeskActivity.this);appLaunch = false;}
			if(!isNetworkAvailable(OdeskActivity.this))
			{
				Toast.makeText(getApplicationContext(), "Oops!Check your network connection", Toast.LENGTH_LONG);
			}

		};
		
		

	}


	public boolean onCreateOptionsMenuDisable(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add("LiveScores");//0
		menu.add("Results");//1
		menu.add("Point Table");//2
		menu.add("Rankings");//3
		menu.add("Records");//4
		menu.add("Photos");//5
		menu.add("Videos");//6
		menu.add("Players");//7
		menu.add("Countries");//8
		menu.add("News");//9
		//menu.add("LiveScores");//10

		getMenuInflater().inflate(R.menu.score_board, menu);

		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Progresss.start(OdeskActivity.this);
		
		if(item.getTitle().equals("LiveScores"))
		{
			wView.loadUrl(AppConstant.STARTUP_LINK);
		}else if(item.getTitle().equals("Results"))
		{
			wView.loadUrl(AppConstant.RESULTS_LINK);
		}else if(item.getTitle().equals("Point Table"))
		{
			wView.loadUrl(AppConstant.POINT_TABLE_LINK);
		}else if(item.getTitle().equals("Rankings"))
		{
			wView.loadUrl(AppConstant.RANKINGS_LINK);
		}else if(item.getTitle().equals("Records"))
		{
			wView.loadUrl(AppConstant.RECORDS_LINK);
		}else if(item.getTitle().equals("Photos"))
		{
			wView.loadUrl(AppConstant.PHOTOS_LINK);
		}else if(item.getTitle().equals("Videos"))
		{
			wView.loadUrl(AppConstant.VIDEOS_LINK);
		}else if(item.getTitle().equals("Players"))
		{
			wView.loadUrl(AppConstant.PLAYERS_LINK);
		}else if(item.getTitle().equals("Countries"))
		{
			wView.loadUrl(AppConstant.COUNTRIES_LINK);
		}else if(item.getTitle().equals("News"))
		{
			wView.loadUrl(AppConstant.NEWS_LINK);
		}
		else
		{
			Progresss.stop();
			return super.onOptionsItemSelected(item);
		}
		return true;
		
		
		/*
		switch(item.getItemId())
		{
		case 0:

			wView.loadUrl(AppConstant.STARTUP_LINK);
			return true;
		case 1:

			wView.loadUrl(AppConstant.RESULTS_LINK);
			return true;
		case 2:

			wView.loadUrl(AppConstant.POINT_TABLE_LINK);
			return true;
		case 3:

			wView.loadUrl(AppConstant.RANKINGS_LINK);
			return true;
		case 4:

			wView.loadUrl(AppConstant.RECORDS_LINK);
			return true;
		case 5:

			wView.loadUrl(AppConstant.PHOTOS_LINK);
			return true;
		case 6:

			wView.loadUrl(AppConstant.VIDEOS_LINK);
			return true;
		case 7:

			wView.loadUrl(AppConstant.PLAYERS_LINK);
			return true;
		case 8:

			wView.loadUrl(AppConstant.COUNTRIES_LINK);
			return true;
		case 9:

			wView.loadUrl(AppConstant.NEWS_LINK);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		*/
		
	}


	//checkNetwork Connection
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public void onBackPressed() {
		if(wView.canGoBack()){
			wView.goBack();
		}else{
			finish();
		}
	}

}
