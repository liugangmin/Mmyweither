package com.example.a38633.mmyweither.util;

import android.util.Log;

import com.example.a38633.mmyweither.model.Weither;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 38633 on 2016/10/3.
 */

public class ParseXMLHandler extends DefaultHandler {
    private static List<Weither> weithers;
    private Weither weither;
    private String qname;
    String data = null;
    private static HashMap<String,String> map1 = new HashMap<String, String>();



    public ParseXMLHandler() {

        weithers = new ArrayList<Weither>();

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        if(qname!=null){
            data = new String(ch, start, length);
            if ("city".equals(qname)) {
                weither.setCity(data);
                map1.put("city",data);

            } else if ("fengli".equals(qname)) {
                weither.setFengli(data);
                map1.put(qname,data);
            } else if ("fengxiang".equals(qname)) {
                weither.setFengxiang(data);
                map1.put(qname,data);
            } else if ("shidu".equals(qname)) {
                weither.setShidu(data);
                map1.put(qname,data);
            } else if ("updatetime".equals(qname)) {
                weither.setUpdatetime(data);
                map1.put(qname,data);
            }else if ("sunrise_1".equals(qname)) {
                weither.setSunrise(data);
                map1.put(qname,data);
            }else if ("sunset_1".equals(qname)) {
                weither.setSunset(data);
                map1.put(qname,data);
            }else if ("wendu".equals(qname)) {
                weither.setWendu(data);
                map1.put(qname,data);
            }


        }


    }

    public String getcontent(String s){
        return map1.get(s);

    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
//      Log.i("TAG", "stop parse xml");
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);

        //此处用qName而不用qname！！！！！！！！！
        if ("resp".equals(qName)) {
            weithers.add(weither);
            weither = null;

        }
        qname=null;


    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
//      Log.i("TAG", "start parse xml");
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("resp")) {
            weither = new Weither();
            int num = attributes.getLength();
            // 遍历所有的属性
            for (int i = 0; i < num; i++) {
                String name = attributes.getQName(i);
                String value = attributes.getValue(i);
                if ("city".equals(name)) {
                    weither.setCity(value);
                }
            }
        }
        this.qname = qName;
    }

    public  List<Weither> getAllweithers() {
        return weithers;
    }

}