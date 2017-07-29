package com.example.heyukun.taobaovlayoutlearn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by heyukun on 2017/7/28.
 */

public class MyDelegateAdapter extends DelegateAdapter.Adapter<MyDelegateAdapter.MainViewHolder> {
    private ArrayList<HashMap<String,Object>>   listItem;//用于存放数据列表

    private Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count = 0;

    private MyItemClickListener myItemClickListener;

    public MyDelegateAdapter(ArrayList<HashMap<String, Object>> listItem, Context context, LayoutHelper layoutHelper, int count) {
       this(listItem,context,layoutHelper,new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300),count);
    }

    public MyDelegateAdapter(ArrayList<HashMap<String, Object>> listItem, Context context, LayoutHelper layoutHelper, RecyclerView.LayoutParams layoutParams, int count) {
        this.listItem = listItem;
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.layoutParams = layoutParams;
        this.count = count;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
      holder.Text.setText((String)listItem.get(position).get("ItemTitle"));
        holder.image.setImageResource((Integer) listItem.get(position).get("ItemImage"));
    }

    @Override
    public int getItemCount() {
        return count;
    }


    public void setOnItemClickListener(MyItemClickListener listener){
       myItemClickListener = listener;
    }


    class MainViewHolder extends RecyclerView.ViewHolder{
        public TextView Text;
        public ImageView image;

        public MainViewHolder(View itemView) {
            super(itemView);
            //绑定视图
            Text = (TextView) itemView.findViewById(R.id.Item);
            image = (ImageView) itemView.findViewById(R.id.Image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myItemClickListener != null){
                        myItemClickListener.onItemClick(v,getPosition());
                    }

                }
            });
        }
        public TextView getText(){
            return Text;
        }
    }
}
