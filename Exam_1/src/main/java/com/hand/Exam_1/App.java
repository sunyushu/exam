package com.hand.Exam_1;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class ReadByGet extends Thread
{
	public void run()
	{
		try {
			/*
			 * url链接中的doctype应修改为你想要获得数据的返回形式，如json|jsonp|xml|text等
			 * &=q后面接你想要翻译的词汇，直接加入即可，不需要增加任何形式的格式
			 */
			//URL url =new URL("http://fanyi.youdao.com/openapi.do?keyfrom=testsunyu&key=1161613034&type=data&doctype=<doctype>&version=1.1&q=hello world");
			URL url =new URL("http://www.manning.com/gsmith/SampleChapter1.pdf");
			//打开链接
			URLConnection connection=url.openConnection();
            //获取输入流 
			InputStream is =connection.getInputStream();
			//包装is数据并读取输入流，并制定数据编码(可选)
			InputStreamReader isr = new InputStreamReader(is,"UTF-8");
			//创建读取缓冲区
			BufferedReader br =new BufferedReader(isr);
			
			File new_text =new File("new_sample.pdf");
			FileOutputStream fos = new FileOutputStream(new_text);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw =new BufferedWriter(osw);
			
			while (br!=null)
			{
				bw.write(br.read());
			}
			
			
		    //关闭数据流
			bw.close();
			osw.close();
			fos.close();
		    br.close();
		    isr.close();
		    is.close();
		    
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class App 
{
    public static void main( String[] args )
    {
		ReadByGet rbg =new ReadByGet();
		rbg.start();
    }
}
