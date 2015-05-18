package com.atet.multicast.client.activity;

import javax.jmdns.ServiceInfo;

import com.atet.multicast.adapter.ServerAdapter;
import com.jmdns.multicast.app.JmdnsApp;
import com.jmdns.multicast.connect.ConnectionWrapper.OnCreatedListener;
import com.jmdns.multicast.connect.NetworkDiscovery.OnFoundListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Handler mHandler = new Handler();
	private String Tag = MainActivity.class.getName();
	private ListView lv_device = null;
	private ServerAdapter serverAdapter = null;
	private EditText etSend = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getApplications().createConnectionWrapper(new onConnectWrapper());
		widget_init();
	}

	private void widget_init() {
		lv_device = (ListView) findViewById(R.id.lv_device_name);
		etSend = (EditText) findViewById(R.id.etSend);
		
	}

	private class onConnectWrapper implements OnCreatedListener {
		@Override
		public void onCreated() {
			// TODO Auto-generated method stub
			getApplications().getConnectionWrapper().findServers(
					new findServersListener());
		}
	}

	private class findServersListener implements OnFoundListener {

		@Override
		public void onFound(ServiceInfo info) {
			// TODO Auto-generated method stub
			Log.e(Tag, info.getName() + " is find!");

		}
	}

	/**
	 * @description:
	 * 
	 * @return 获取jmdns对象
	 * @throws:
	 * @author: HuJun
	 * @date: 2015年5月13日 下午6:35:29
	 */
	private JmdnsApp getApplications() {
		return (JmdnsApp) getApplication();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
