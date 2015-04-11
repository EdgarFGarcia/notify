package com.pasig.thesis.notify.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ph.com.pasig.thesis.notify.Notify;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Notify(), config);
        config.width = 640;
        config.height = 400;
//        config.width = 640;
//        config.height = 400;
	}
}
