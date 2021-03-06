package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import controller.controller;

public class MyKeyAdapter extends KeyAdapter {
	controller currentController;
	public MyKeyAdapter(controller Controller){
		this.currentController = Controller;
	}
	public void keyPressed(KeyEvent e) {
		if(!currentController.isStarted() || currentController.isCurrentPieceNoShaped()) {
			return;
		}
		
		int Key = e.getKeyCode();
		
		/*
		 * 'P'/'p': pause
		 */
		if(Key == 'p' || Key == 'P') {
			currentController.pause();
			return;
		}
		
		if(currentController.isPaused()) {
			return;
		}
		
		/*
		 * <-: move left
		 * ->: move right
		 * ↓: one line down
		 * ↑: rotate
		 * 'A'/'a': direct down
		 */
		if (Key == KeyEvent.VK_LEFT) {
			currentController.moveLeft();
		}
		else if(Key == KeyEvent.VK_RIGHT) {
			currentController.moveRight();
		}
		else if(Key == KeyEvent.VK_DOWN) {
			currentController.oneLineDown();
		}
		else if(Key == KeyEvent.VK_UP) {
			currentController.rotateLeft();
		}
		else if(Key == 'D' || Key == 'd') {
			currentController.directDown();
		}
	}
}
