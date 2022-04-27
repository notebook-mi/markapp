package com.example.markapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {
    TextView item_time;
    EditText item_title,item_content;
    MarkBean  index=null;
    Intent intent,intent2=new Intent();
    Button btn;
    Toolbar btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        item_title=(EditText) findViewById(R.id.item_title);
        item_time=(TextView) findViewById(R.id.item_time);
        item_content=(EditText) findViewById(R.id.item_content);
        btn1=(Toolbar)findViewById(R.id.btn) ;
        intent = getIntent();
        index= (MarkBean) intent.getSerializableExtra("index");
        //做标记，看是添加还是修改
        if(index==null){
            //添加笔记
            index=new MarkBean();
            item_time.setText(itemTime());
            //1为添加，2为改
            intent2.putExtra("t2g",1);
        }else {
            //展示数据，并保存之前的时间，为数据库修改提供条件
            intent2.putExtra("t2g",2);
            intent2.putExtra("time",index.getTime());
            item_title.setText(index.getTitle());
            item_time.setText(index.getTime());
            item_content.setText(index.getContent());
        }
//        Toast.makeText(MainActivity2.this, ""+index.getGetItemType()+""+index.getKey(), Toast.LENGTH_SHORT).show();
        //设置menu
        setSupportActionBar(btn1);
        btn1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        if(index.getGetItemType()!=2){
                            index.setGetItemType(2);
                            item.setIcon(R.drawable.ic_baseline_bookmark_2);
                            Toast.makeText(MainActivity2.this, "已标为重要事件", Toast.LENGTH_SHORT).show();
                        }else {
                            index.setGetItemType(1);
                            item.setIcon(R.drawable.ic_baseline_bookmark_1);
                            Toast.makeText(MainActivity2.this, "已取消重要事件", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });


        //添加数据到markBean
        btn1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index.setContent(item_content.getText().toString());
                index.setTime(itemTime());
                index.setTitle(item_title.getText().toString());
                intent2.putExtra("index_item",index);
                intent2.setClass(MainActivity2.this,MainActivity.class);
                startActivity(intent2);
            }
        });
    }
    //添加menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_up, menu);
        MenuItem upset =menu.findItem(R.id.action_settings);
        if(index.getGetItemType()==2) {
            upset.setIcon(R.drawable.ic_baseline_bookmark_2);
        }
        return true;
    }
    //按钮返回
    @Override
    public void onBackPressed(){
        index.setContent(item_content.getText().toString());
        index.setTime(itemTime());
        index.setTitle(item_title.getText().toString());
        intent2.putExtra("index_item",index);
        intent2.setClass(MainActivity2.this,MainActivity.class);
        startActivity(intent2);
    }
    //获取当期时间
    public String itemTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year+"年"+month+"月"+day+"日 "+hour+":"+minute+":"+second;
    }
}