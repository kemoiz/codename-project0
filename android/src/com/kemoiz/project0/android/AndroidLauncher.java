package com.kemoiz.project0.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kemoiz.project0.MotherClass;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;
		config.useGLSurfaceView20API18 = true;

		config.hideStatusBar = true;

		config.useImmersiveMode = true;
		initialize(new MotherClass(), config);
	}
}
