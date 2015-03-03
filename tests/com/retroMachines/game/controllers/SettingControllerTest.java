package com.retroMachines.game.controllers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.retroMachines.data.models.Setting;
import com.retroMachines.data.models.SettingsChangeListener;

public class SettingControllerTest {

	private SettingController settingController;
	
	private Setting setting;
	
	@Before
	public void setUp() throws Exception {
		settingController = new SettingController(null);
		setting = new Setting(1);
		try {
			settingController.initialize();
		} catch (NullPointerException e) {
			// game is not initialized. calls to it are bad 
		}
		settingController.setSetting(setting);
	}

	@After
	public void tearDown() throws Exception {
		setting.destroy();
		settingController = null;
	}
	
	@Test
	public void testToggle() {
		int oldId = settingController.getCurrentCharacterId();
		settingController.toggleCharacter();
		assertFalse("immer noch selbe id", settingController.getCurrentCharacterId() == oldId);
	}
	
	@Test
	public void testVolumeDisabled() {
		settingController.setVolume(0.0f);
		assertFalse("ton ist immer noch an", settingController.soundEnabled());
	}
	
	@Test
	public void testSoundEnabled() {
		settingController.setVolume(0.5f);
		assertTrue("ton ist aus", settingController.soundEnabled());
	}

	@Test
	public void testListener() {
		settingController.setSetting(setting);
		MockListener mockListener = new MockListener();
		settingController.add(mockListener);
		settingController.setVolume(0.9f);
		assertTrue("methode wurde nicht aufgerufen", mockListener.callHappened);
		mockListener.callHappened = false;
		settingController.setLeftMode(true);
		assertTrue("methode wurde nicht aufgerufen", mockListener.callHappened);
		settingController.removeListener(mockListener);
		mockListener.callHappened = false;
		settingController.setVolume(0.5f);
		assertFalse("methode des mock listeners wurde aufgerufen", mockListener.callHappened);
	}
	
	private class MockListener implements SettingsChangeListener {
		
		public boolean callHappened;
		
		public void onSettingsChanged() {
			callHappened = true;
		}
		
	}

}
