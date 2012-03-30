/**
 * Copyright (C) 2011 Digital Primates
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/

package com.adobe.nativeextension.volume.functions;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings.System;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.nativeextension.volume.VolumeExtension;
import com.adobe.nativeextension.volume.monitor.SettingsContentObserver;

public class InitFunction implements FREFunction {
	public static final String TAG = "InitFunction";
	
	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		VolumeExtension.extensionContext = context;
		
		Context appContext = context.getActivity().getApplicationContext();
		VolumeExtension.appContext = appContext;
		
		// Start watching settings for volume changes.
		VolumeExtension.mSettingsWatcher = new SettingsContentObserver( new Handler() );
		VolumeExtension.appContext.getContentResolver().registerContentObserver(System.CONTENT_URI, true, VolumeExtension.mSettingsWatcher);
		
		Log.i(TAG, "in init");
		
		// Tell AIR what the volume is right now.
		VolumeExtension.notifyVolumeChange();
		
		return null;
	}
}
