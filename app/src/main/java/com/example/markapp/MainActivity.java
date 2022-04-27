package com.example.markapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private ListView listView;//列表展示操作容器
    private ListViewAdper listViewAdper;//数据添加容器
    private List<MarkBean> datas= new ArrayList<>();//列表数据
    FloatingActionButton fab;//浮动按钮
    SqlOpt sqlOpt ;//数据库操作
    Intent intent=new Intent();;//数据传输用
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView) findViewById(R.id.list_view);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fabPosition();
        sqlOpt=new SqlOpt(this);
        datas=sqlOpt.listSize();//获取数据库中所有列表数据

        listViewAdper=new ListViewAdper(this,datas);
        listView.setAdapter(listViewAdper);
        listViewAdper.notifyDataSetChanged();//显示数据
        //数据修改
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MarkBean markBean=datas.get(i);
                markBean.setKey(i);
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(MainActivity.this,MainActivity2.class);
                intent.putExtra("index",markBean);
                startActivity(intent);
            }

        });
        //数据删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int position=i;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("确认")
                        .setMessage("确定要删除"+datas.get(i).getTitle()+"吗？")
                        .setPositiveButton("是",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MarkBean flag= datas.remove(position);
                                sqlOpt.delete(flag);
                                Toast.makeText(MainActivity.this,"已删除",Toast.LENGTH_SHORT).show();
                                listViewAdper.notifyDataSetChanged();//刷新数据
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
                return true;
            }
        });
        //浮动按钮添加
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });

    }
    //悬浮按扭位置设置
    public void fabPosition(){
        WindowManager mWindowManager  = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;//获取到的是px，像素，绝对像素，需要转化为dpi
        int height = metrics.heightPixels;
        fab.setX( width-300);
        fab.setY(height-600);
        //
    }
    //2的传给1
    @Override
    protected void onResume() {
        super.onResume();
        Intent index= getIntent();
        int i=index.getIntExtra("t2g",0);
        if (i==1) {
            //添加
            MarkBean markBean2 = (MarkBean) index.getSerializableExtra("index_item");
            sqlOpt.insertDate(markBean2);
            datas.add(markBean2);
            listViewAdper.notifyDataSetChanged();//刷新数据
        }else if(i==2){
            //修改
            MarkBean markBean3 = (MarkBean) index.getSerializableExtra("index_item");
            String time=index.getStringExtra("time");//之前的时间
            sqlOpt.update(markBean3,time);
//            Log.i("aaa",""+markBean3.getKey()+""+markBean3.getGetItemType());
            datas.set(markBean3.getKey(),markBean3);
            listViewAdper=new ListViewAdper(this,datas);
            listView.setAdapter(listViewAdper);
            listViewAdper.notifyDataSetChanged();//刷新数据
        }

    }

}