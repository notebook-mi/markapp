package com.example.markapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SqlOpt {
    private final DataBaseHelp mCreate;
    private  final SQLiteDatabase mOperate;
    public Context that;
    public SqlOpt(Context context){
        that=context;
        mCreate=new DataBaseHelp(context);//创建数据库
        mOperate=mCreate.getWritableDatabase();//操作数据库
    }
    //增
    public void insertDate(MarkBean itemMarkBean){
        ContentValues values=new ContentValues();
        values.put("mesTitle",itemMarkBean.getTitle());
        values.put("mesTime",itemMarkBean.getTime());
        values.put("mesContent",itemMarkBean.getContent());
        values.put("type",itemMarkBean.getGetItemType());
        mOperate.insert("marklist",null,values);
    }
    //改
    public void update(MarkBean itemMarkBean,String time){
        ContentValues values = new ContentValues();
        values.put("mesTitle",itemMarkBean.getTitle());
        values.put("mesTime",itemMarkBean.getTime());
        values.put("mesContent",itemMarkBean.getContent());
        values.put("type",itemMarkBean.getGetItemType());
        mOperate.update("marklist",values,"mesTime=?",new String[]{""+time});
    }
    //删除
    public void delete(MarkBean itemMarkBean){
        Log.i("aaa",""+itemMarkBean.getTime());
        mOperate.delete("marklist","mesTime=?",new String[]{""+itemMarkBean.getTime()});
    }
    //查
    public MarkBean query(String time){
        MarkBean markBean= new MarkBean();
        Cursor cursor = mOperate.query("marklist",null,"mesTime=?",new String[]{time},null,null,null);
        cursor.moveToNext();
        markBean.setTitle("");
        markBean.setContent("");
        markBean.setTime("");
        markBean.setKey(-1);
//        int id_index=cursor.getColumnIndex("id");
//       if(id_index>0){
//            markBean.setKey(cursor.getInt(id_index));
//            Log.i("aaa",""+markBean.getKey());
//        }
        int mesTitle_index=cursor.getColumnIndex("mesTitle");
        if (mesTitle_index>0){
            markBean.setTitle(cursor.getString(mesTitle_index));
        }
        int mesTime_index=cursor.getColumnIndex("mesTime");
        if (mesTime_index>0){
            markBean.setTime(cursor.getString(mesTime_index));
        }
        int mesContent_index=cursor.getColumnIndex("mesContent");
        if (mesContent_index>0){
            markBean.setContent(cursor.getString(mesContent_index));
        }
        int type_index=cursor.getColumnIndex("type");
        if (mesContent_index>0){
            markBean.setGetItemType(cursor.getInt(type_index));
        }
        cursor.close();
        return markBean;
    }
    //获取总条数
    public List<MarkBean> listSize(){
        String sql="select * from marklist";
        Cursor cursor= mOperate.rawQuery(sql,null);
        List<MarkBean> markBeanList= new ArrayList<>();
        while (cursor.moveToNext()){
            MarkBean markBean=new MarkBean();
            int mesTitle_index=cursor.getColumnIndex("mesTitle");
            if (mesTitle_index>-1) {
                String mesTitle= cursor.getString(mesTitle_index);
                markBean.setTitle(mesTitle);
            }
            int mesTime_index=cursor.getColumnIndex("mesTime");
            if (mesTime_index>-1){
                String mesTime= cursor.getString(mesTime_index);
                markBean.setTime(mesTime);
            }
            int mesContent_index=cursor.getColumnIndex("mesContent");
            if(mesContent_index>-1){
                String mesContent=cursor.getString(mesContent_index);
                markBean.setContent(mesContent);
            }
            int type_index=cursor.getColumnIndex("type");
            if (mesContent_index>0){
                markBean.setGetItemType(cursor.getInt(type_index));
            }
            markBeanList.add(markBean);
        }
        cursor.close();
        return markBeanList;
    }
    //
}
