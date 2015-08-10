package com.hand.chatClient;

import java.awt.EventQueue;

public class StartClient {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//创建一个新的窗口
					com.hand.chatView.MainWindow frame= new com.hand.chatView.MainWindow();
					frame.setVisible(true);
					ChatManager.getCM().setWindow(frame);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
