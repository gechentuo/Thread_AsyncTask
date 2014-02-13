package com.example.thread_asynctask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView textview;
	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		textview = new TextView(MainActivity.this);
		dialog = builder.setTitle("www.baidu.com").setView(textview).create();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void loadData(View view) {
		new LoadTask().execute("http://baidu.com");
	}

	private class LoadTask extends AsyncTask<String, Void, byte[]> {
		@Override
		protected byte[] doInBackground(String... params) {
			return getContent(params[0]);
		}

		@Override
		protected void onPostExecute(byte[] result) {
			textview.setText(String.valueOf(result.length));
			dialog.show();
		}

		private byte[] getContent(String url1) {
			URL url;
			String content = "";
			try {
				url = new URL(url1);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				content = readStream(con.getInputStream());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return content.getBytes();
		}

		private String readStream(InputStream in) {
			BufferedReader reader = null;
			StringBuffer all = new StringBuffer();

			try {
				reader = new BufferedReader(new InputStreamReader(in));
				String data = "";
				while ((data = reader.readLine()) != null) {
					all.append(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return all.toString();

		}
	}

}
