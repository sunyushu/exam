package com.hand.Exam_3;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.JsonObject;

public class App {
	public static void main(String[] args){
		try {
			URL url = new URL("http://hq.sinajs.cn/list=sz300170");
			URLConnection conn = url.openConnection();
			
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			br.close();
			isr.close();
			is.close();
			String[] arr = sb.toString().split("\"");
			List list = Arrays.asList(arr);
			String[] arrs = new String[list.size()];
			arrs = list.get(1).toString().split(",");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			Element root = document.createElement("xml");
			
			Element stock = document.createElement("stock");
			
			Element name = document.createElement("name");
			name.setTextContent(arrs[0]);
			Element open = document.createElement("open");
			open.setTextContent(arrs[1]);
			Element close = document.createElement("close");
			close.setTextContent(arrs[2]);
			Element current = document.createElement("current");
			current.setTextContent(arrs[3]);
			Element high = document.createElement("high");
			high.setTextContent(arrs[4]);
			Element low = document.createElement("low");
			low.setTextContent(arrs[5]);
			stock.appendChild(name);
			stock.appendChild(open);
			stock.appendChild(close);
			stock.appendChild(current);
			stock.appendChild(high);
			stock.appendChild(low);
			root.appendChild(stock);
			document.appendChild(root);
			
			TransformerFactory f = TransformerFactory.newInstance();
			Transformer tf = f.newTransformer();
			tf.transform(new DOMSource(document), new StreamResult(new File("myExam.xml")));
			System.out.println("成功创建XML文件。");
			
			
			JsonObject jo = new JsonObject();
			jo.addProperty("name", arrs[0]);
			jo.addProperty("open", arrs[1]);
			jo.addProperty("close", arrs[2]);
			jo.addProperty("current", arrs[3]);
			jo.addProperty("high", arrs[4]);
			jo.addProperty("low", arrs[5]);
			FileOutputStream fos = new FileOutputStream(new File("myExam.json"));
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter writer = new BufferedWriter(osw);
			writer.write(jo.toString());
			writer.flush();
			System.out.println("成功创建JSON文件。");
			writer.close();
			osw.close();
			fos.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}