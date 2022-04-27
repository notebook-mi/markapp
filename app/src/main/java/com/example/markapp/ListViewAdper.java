package com.example.markapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListViewAdper extends BaseAdapter {
    private List<MarkBean> datas;
    private Context context;

    public ListViewAdper(Context context,List<MarkBean> datas){
        this.context=context;
        this.datas=datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        int type=getItemViewType(i);
        if(view ==null){
            holder =new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_listview,viewGroup,false);
            holder.title=(TextView) view.findViewById(R.id.title1);
            holder.content=(TextView) view.findViewById(R.id.text_content1);
            holder.time=(TextView) view.findViewById(R.id.time1);
            holder.lineback=(LinearLayout) view.findViewById(R.id.linback);
           view.setTag(holder);
        }else {
            holder=(ViewHolder) view.getTag();
        }
        holder.title.setText(datas.get(i).getTitle());
        holder.time.setText(datas.get(i).getTime());
        holder.content.setText(datas.get(i).getContent());
        if(datas.get(i).getGetItemType()==2){
            holder.lineback.setBackgroundColor(Color.parseColor("#CCFFFF"));
        }else{
            holder.lineback.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
//        Log.i("aaa",datas.get(i).getGetItemType()+"");
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getGetItemType();
    }


    class  ViewHolder{//做缓存
        TextView title;
        TextView content;
        TextView time;
        LinearLayout lineback;
    }
}
