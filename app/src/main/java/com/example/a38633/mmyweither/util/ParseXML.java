package com.example.a38633.mmyweither.util;


import android.os.Message;
import android.os.Handler;

import com.example.a38633.mmyweither.model.Weither;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;


/**
 * Created by 38633 on 2016/9/30.
 */

public class ParseXML extends Thread {
    public static final int SHOW_LISTVIEW=0x11;
    private String path;
    private Handler handler;
    private List<Weither> weithers;
    public ParseXML(String path) {
        this.path=path;
    }

    public ParseXML(String path, Handler handler) {
        this.path = path;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            //获取服务器返回的输入流
            String date = getXMLInputStream(conn.getInputStream());

            SAXParserFactory factory=SAXParserFactory.newInstance();
            XMLReader xmlreader=factory.newSAXParser().getXMLReader();
            ParseXMLHandler parse=new ParseXMLHandler();
            xmlreader.setContentHandler(parse);
            xmlreader.parse(new InputSource(new StringReader(date.toString())));
            weithers= parse.getAllweithers();

            Message message= Message.obtain();
            message.what=SHOW_LISTVIEW;
            message.obj=weithers;
            handler.sendMessage(message);


            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public String getXMLInputStream(InputStream inputStream) {
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        String str;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream,
                    "utf-8"));
            if (reader != null) {
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public List<Weither> getWeithers(){
        return weithers;
    }

}