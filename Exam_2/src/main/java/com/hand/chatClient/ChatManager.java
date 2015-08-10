package com.hand.chatClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.hand.chatView.MainWindow;

public class ChatManager {

	private ChatManager(){}
	private static final ChatManager instance =new ChatManager();
	public static ChatManager getCM()
	{
		return instance;
	}

	MainWindow window;
	Socket socket;
	String IP;
	BufferedReader br;
	PrintWriter pw;
	Boolean is_success;

	public void setWindow(MainWindow window) {
		this.window = window;
		window.setText("文本框锁定");
	}

	//建立连接，接收IP地址
	public void connect(String ip)
	{
		this.IP=ip;
		new Thread(){

			@Override
			public void run() {
				try {
					socket=new Socket(IP,12345);
					//创建输入流
					File file= new File("new_sample.pdf");
					FileOutputStream fos =new FileOutputStream(file) ;
					OutputStream os =socket.getOutputStream();
	                OutputStreamWriter osw =new OutputStreamWriter(os, IP) ;
					PrintWriter pw = new PrintWriter(osw);		
					File file_accept =new File("receive_sample.pdf");
					FileInputStream fis =new FileInputStream(file_accept);
					InputStream is =socket.getInputStream();
					InputStreamReader isr =new InputStreamReader(is);
					BufferedReader br =new BufferedReader(isr);
					
					while(br.readLine()!=null)
					{
						pw.write(br.read());
					}
					
					//关闭输出和输入流
					br.close();
					isr.close();
					fis.close();
					pw.close();
					fos.close();
					
					is_success = true;


				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		.start();
	}

	//发送消息
	public void send(String out)
	{
		if(pw!=null)
		{
			pw.write(out+"\n");
			pw.flush();
		}
		else
		{
			window.setText("当前连接已经中断！");
		}
	}


}
