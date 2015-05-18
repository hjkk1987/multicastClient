package com.jmdns.multicast.app;

import com.jmdns.multicast.connect.ConnectionWrapper;

import android.app.Application;

/*
 * File：JmdnsApp.java
 *
 * Copyright (C) 2015 multiCastSoC Project
 * Date：2015年5月13日 下午5:32:27
 * All Rights SXHL(New Space) Corporation Reserved.
 * http://www.at-et.cn
 *
 */

/**
 * @description:
 * 
 * @author: HuJun
 * @date: 2015年5月13日 下午5:32:27
 */

public class JmdnsApp extends Application {
	private ConnectionWrapper mConnectionWrapper = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public void createConnectionWrapper(
			ConnectionWrapper.OnCreatedListener listener) {
		mConnectionWrapper = new ConnectionWrapper(getApplicationContext(),
				listener);
	}

	public ConnectionWrapper getConnectionWrapper() {
		return mConnectionWrapper;
	}
}
