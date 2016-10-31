package com.example.a38633.mmyweither.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 38633 on 2016/10/4.
 */
public class TextReder {

   static Map<String, String> map = new HashMap<String, String>();

    public static String getString(InputStream inputStream ,String cityname) {

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {

                sb.append(line);
                sb.append("\n");
                for(String s :sb.toString().trim().split(",")){
                    String[] ss = s.trim().split(":");
                    map.put(ss[0].trim(), ss[1].trim());
                }
                for(Map.Entry<String,String> e:map.entrySet()){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map.get(cityname);

    }

      
}
