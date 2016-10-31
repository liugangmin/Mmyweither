package com.example.a38633.mmyweither.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a38633.mmyweither.R;
import com.example.a38633.mmyweither.util.ParseXML;
import com.example.a38633.mmyweither.util.ParseXMLHandler;
import com.example.a38633.mmyweither.model.Weither;
import com.example.a38633.mmyweither.util.TextReder;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends FragmentActivity {
    private TextView city;
    private TextView updatetime;
    private TextView wendu;
    private GridView bottomMenuGridView;
    private ListView contentListView;
    private List<Weither> weithers;
    public Handler handler =  new Handler() {
       public void handleMessage(android.os.Message msg) {
           switch (msg.what) {
               case ParseXML.SHOW_LISTVIEW:
                    weithers = (List<Weither>) msg.obj;
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentListView = (ListView) findViewById(R.id.content_listview);
        city = (TextView)findViewById(R.id.city) ;
        updatetime = (TextView)findViewById(R.id.updatetime);
        wendu = (TextView)findViewById(R.id.wendu);
        bottomMenuGridView=(GridView)findViewById(R.id.gridView1);
        initBottomMenu();
    }


    void initBottomMenu() {
        String[] mMenuToolStringList = { "设置城市" ,"更新数据","退出"};
        int[] mMenuToolImageList = { R.drawable.menu_create,R.drawable.gengxin ,R.drawable.exit};

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        // 设置文字和图标
        for (int i = 0; i < mMenuToolStringList.length; i++) {
            Map<String, Object> line = new HashMap<String, Object>();

            line.put("text", mMenuToolStringList[i]);
            line.put("img", mMenuToolImageList[i]);

            data.add(line);
        }

        // 设置整个底部菜单的背景
        this.bottomMenuGridView.setBackgroundResource(R.drawable.menu_background);

        // 绑定适配器
        bottomMenuGridView.setAdapter(new SimpleAdapter(MainActivity.this,
                data, R.layout.bottom_menu, new String[] { "text", "img" },
                new int[] { R.id.gv_text, R.id.gv_img }));
        // 绑定点击事件
        bottomMenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                switch (arg2) {


                    case 0:

                            View v = LayoutInflater.from(MainActivity.this)
                                    .inflate(R.layout.creatlocation, null);
                            // 获取上面的文本框和单选按钮
                             final EditText et = (EditText) v.findViewById(R.id.et);


                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("输入你的城市名字：");
                            builder.setView(v);
                            // builder.setCancelable(false);//点击对话框外面 不消失

                            // 绑定按钮和点击事件
                            builder.setPositiveButton("取消", null);
                            builder.setNeutralButton("aaa",null);
                            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    // 获取用户选择的按钮和文本

                                    String newcityname = et.getText().toString();

                                    if (newcityname.equals("")) {
                                        alert("你怎么不输入城市的名字呢~");
                                    } else {
                                        ParseXMLHandler p = new ParseXMLHandler();
                                        InputStream inputStream = getResources().openRawResource(R.raw.weq);
                                        String city123 = newcityname;
                                        String string = TextReder.getString(inputStream ,city123);
                                        ParseXML parseXML = new ParseXML(
                                                    "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + string,
                                                    handler);
                                            parseXML.start();
                                            weithers = parseXML.getWeithers();
                                            String[] datalist = {"风力", "风向", "湿度", "日出", "日落"};
                                            Log.d("ujuj", "onCreate: " + p.getcontent("fengli"));
                                            String[] datainterlist = {p.getcontent("fengli"), p.getcontent("fengxiang"),
                                                    p.getcontent("shidu"), p.getcontent("sunrise_1"), p.getcontent("sunset_1")};
                                            List<Map<String, Object>> array = new ArrayList<Map<String, Object>>();
                                            for (int i = 0; i < datalist.length; i++) {
                                                Map<String, Object> line1 = new HashMap<String, Object>();

                                                line1.put("title", datalist[i]);
                                                line1.put("content", datainterlist[i]);

                                                array.add(line1);

                                            }
                                            contentListView.setAdapter(new SimpleAdapter(MainActivity.this,
                                                    array, R.layout.item, new String[]{"title", "content"},
                                                    new int[]{R.id.liu1, R.id.liu2}));

                                    }
                                }
                            });
                            builder.show();
                        break;
                    case 1:
                        ParseXMLHandler p = new ParseXMLHandler();
                        city.setText(p.getcontent("city"));
                        updatetime.setText(p.getcontent("updatetime")+"更新");
                        wendu.setText(p.getcontent("wendu")+"℃");
                        Log.d("hhh", "onClick: "+p.getcontent("sunset_1"));
                        String[] datalist = { "风力" ,"风向","湿度","日出","日落"};
                        Log.d("ujuj", "onCreate: "+p.getcontent("fengli"));
                        String[] datainterlist = { p.getcontent("fengli"),p.getcontent("fengxiang"),
                                p.getcontent("shidu"),p.getcontent("sunrise_1"),p.getcontent("sunset_1")};
                        List<Map<String, Object>> array = new ArrayList<Map<String, Object>>();
                        for (int i = 0; i < datalist.length; i++) {
                            Map<String, Object> line1 = new HashMap<String, Object>();

                            line1.put("title", datalist[i]);
                            line1.put("content", datainterlist[i]);

                            array.add(line1);
                        }
                        contentListView.setAdapter(new SimpleAdapter(MainActivity.this,
                                array, R.layout.item, new String[] { "title", "content" },
                                new int[] { R.id.liu1, R.id.liu2 }));
                        break;
                    case 3:
                        exitAlert();
                        break;
                }
            }
        });
    }
    void alert(Object message) {
        message = message == null ? "null" : message;
        final String text = message.toString();
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
    void exitAlert() {
        AlertDialog.Builder bexit = new AlertDialog.Builder(MainActivity.this);
        bexit.setTitle("提示");
        bexit.setMessage("确定退出吗?");
        bexit.setPositiveButton("取消", null);
        bexit.setNegativeButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // 结束MainActivity
                MainActivity.this.finish();
            }
        });
        bexit.show();
    }


}