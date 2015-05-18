package com.jmdns.multicast.connect;

import android.content.Context;
import android.os.AsyncTask;

/**
 * easy-to-use wrapper for clients
 * 
 * @author 
 * @version 1.0
 */
public class ConnectionWrapper {
	private Context mContext;
	private NetworkDiscovery mNetworkDiscovery;

	/**
	 * wrapper constructor see example of usage in
	 * {@link me.alwx.localcommunication.MainActivity}
	 * 
	 * @param context
	 *            application context
	 * @param listener
	 *            listener, that will be called after all preparation finished
	 *            (see
	 *            {@link me.alwx.localcommunication.connection.ConnectionWrapper.OnCreatedListener}
	 *            )
	 */
	public ConnectionWrapper(final Context context,
			final OnCreatedListener listener) {
		mContext = context;
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				mNetworkDiscovery = new NetworkDiscovery(mContext);
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				if (listener != null) {
					listener.onCreated();
				}
			}
		}.execute((Void) null);
	}

	/**
	 * @description: 开始搜索服务
	 * 
	 * @param port
	 * @throws:
	 * @author: HuJun
	 * @date: 2015年5月18日 下午6:27:21
	 */
	public void startServer(final int port) {

		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				mNetworkDiscovery.startServer(port);
				return null;
			}
		}.execute((Void) null);
	}

	/**
	 * @description: 发现服务
	 * 
	 * @param listener
	 * @throws:
	 * @author: HuJun
	 * @date: 2015年5月18日 下午6:26:55
	 */
	public void findServers(final NetworkDiscovery.OnFoundListener listener) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				mNetworkDiscovery.findServers(listener);

				return null;
			}
		}.execute((Void) null);
	}

	/**
	 * stops network discovery
	 */
	public void stopNetworkDiscovery() {
		if (mNetworkDiscovery != null) {
			mNetworkDiscovery.reset();
		}
	}

	public interface OnCreatedListener {
		void onCreated();
	}
}