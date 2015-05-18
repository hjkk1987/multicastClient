package com.jmdns.multicast.connect;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;

/**
 * @author alwx
 * @version 1.0
 */
public class NetworkDiscovery implements ServiceTypeListener, ServiceListener {
	private final String DEBUG_TAG = NetworkDiscovery.class.getName();
	private final String TYPE = "_teleplus._tcp.local.";
	private final String SERVICE_NAME = "LocalCommunication";

	private Context mContext;
	private JmDNS mJmDNS;
	private ServiceInfo mServiceInfo;
	private ServiceListener mServiceListener;
	private WifiManager.MulticastLock mMulticastLock;
	private OnFoundListener onFoundListener = null;

	public NetworkDiscovery(Context context) {
		mContext = context;
		try {
			WifiManager wifi = (WifiManager) mContext
					.getSystemService(android.content.Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifi.getConnectionInfo();
			int intaddr = wifiInfo.getIpAddress();

			byte[] byteaddr = new byte[] { (byte) (intaddr & 0xff),
					(byte) (intaddr >> 8 & 0xff),
					(byte) (intaddr >> 16 & 0xff),
					(byte) (intaddr >> 24 & 0xff) };
			InetAddress addr = InetAddress.getByAddress(byteaddr);
			mJmDNS = JmDNS.create(addr);
		} catch (IOException e) {
			Log.d(DEBUG_TAG, "Error in JmDNS creation: " + e);
		}
	}

	public void startServer(int port) {
		try {
			wifiLock();
			mServiceInfo = ServiceInfo.create(TYPE, SERVICE_NAME, port,
					SERVICE_NAME);
			mJmDNS.registerService(mServiceInfo);
		} catch (IOException e) {
			Log.d(DEBUG_TAG, "Error in JmDNS initialization: " + e);
		}
	}

	public void findServers(final OnFoundListener listener) {
		this.onFoundListener = listener;
		try {
			mJmDNS.addServiceTypeListener(NetworkDiscovery.this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void reset() {
		if (mJmDNS != null) {
			if (mServiceListener != null) {
				mJmDNS.removeServiceListener(TYPE, mServiceListener);
				mServiceListener = null;
			}
			mJmDNS.unregisterAllServices();
		}
		if (mMulticastLock != null && mMulticastLock.isHeld()) {
			mMulticastLock.release();
		}
	}

	private void wifiLock() {
		WifiManager wifiManager = (WifiManager) mContext
				.getSystemService(android.content.Context.WIFI_SERVICE);
		mMulticastLock = wifiManager.createMulticastLock(SERVICE_NAME);
		mMulticastLock.setReferenceCounted(true);
		mMulticastLock.acquire();
	}

	public interface OnFoundListener {
		void onFound(ServiceInfo info);
	}

	@Override
	public void serviceAdded(ServiceEvent arg0) {
		// TODO Auto-generated method stub
		ServiceInfo serviceInfo = mJmDNS.getServiceInfo(arg0.getType(),
				arg0.getName());
		if (onFoundListener != null) {
			onFoundListener.onFound(serviceInfo);
		}
	}

	@Override
	public void serviceRemoved(ServiceEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serviceResolved(ServiceEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serviceTypeAdded(ServiceEvent arg0) {
		// TODO Auto-generated method stub
		String type = arg0.getType();
		if (type.equals(TYPE)) {
			mJmDNS.addServiceListener(type, NetworkDiscovery.this);
		}
	}

	@Override
	public void subTypeForServiceTypeAdded(ServiceEvent arg0) {
		// TODO Auto-generated method stub

	}
}
