package com.example.heyukun.taobaovlayoutlearn;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyItemClickListener{
    private RecyclerView recyclerView ;
    private MyDelegateAdapter Adapter_linearLayout,Adapter_GridLayout,Adapter_FixLayout,Adapter_ScrollFixLayout,
            Adapter_FloatLayout,Adapter_ColumnLayout,Adapter_SingleLayout,Adapter_onePlusNLayout,Adapter_StickyLayout,
            Adapter_StaggeredGridLayout;
    private ArrayList<HashMap<String,Object>> listItem;//用于存放数据列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycle_view);

        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //设置回收复用池
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(recycledViewPool);
        recycledViewPool.setMaxRecycledViews(0,10);

        /**
         * 设置需要存放的数据
         */

         listItem = new ArrayList<>();
        for (int i=0;i<100;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("ItemTitle","第"+i+"行");
            map.put("ItemImage",R.mipmap.ic_launcher);
            listItem.add(map);
        }
        //设置适配器adapter
        /**
         * 有两种方式：
         * 方式1： 继承 自 DelegateAdapter
         * 方式2： 继承 自 VirtualLayoutAdapter
         */


        /**
         * layouthelper - Adapter
         */
        Adapter_linearLayout = new MyDelegateAdapter(listItem,this,setLinearLayout(),4){
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if(position ==0){
                    holder.Text.setText("Linear");
                }

                if(position<2){
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position -6) *128);
                }else if (position % 2 ==0){
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                }else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }
            }
        };
        Adapter_linearLayout.setOnItemClickListener(this);


        Adapter_GridLayout = new MyDelegateAdapter(listItem,this,setGridLayout(),20){
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if(position ==0){
                    holder.Text.setText("Grid");
                }

                if(position<2){
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position -6) *128);
                }else if (position % 2 ==0){
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                }else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }
            }
        };
        Adapter_GridLayout.setOnItemClickListener(this);

        Adapter_FixLayout = new MyDelegateAdapter(listItem,this,setFixLayout(),1);
        Adapter_FixLayout.setOnItemClickListener(this);


        Adapter_ScrollFixLayout = new MyDelegateAdapter(listItem,this,setScrollFixLayoutHelper(),1);
        Adapter_ScrollFixLayout.setOnItemClickListener(this);

        Adapter_FloatLayout = new MyDelegateAdapter(listItem,this,setFloatLayoutHelper(),1);
        Adapter_FloatLayout.setOnItemClickListener(this);

        Adapter_ColumnLayout = new MyDelegateAdapter(listItem,this,SetColumnLayoutHelper(),3);
        Adapter_ColumnLayout.setOnItemClickListener(this);


        Adapter_SingleLayout = new MyDelegateAdapter(listItem,this,SetSingleLayoutHelper(),1);
        Adapter_SingleLayout.setOnItemClickListener(this);


        Adapter_onePlusNLayout = new MyDelegateAdapter(listItem,this,setOnePlusNlayoutHelper(),5){
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if(position<2){
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position -6) *128);
                }else if (position % 2 ==0){
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                }else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }
            }
        };

        Adapter_onePlusNLayout.setOnItemClickListener(this);

        Adapter_StickyLayout = new MyDelegateAdapter(listItem,this,setStickyLayoutHelper(),3);
        Adapter_StickyLayout.setOnItemClickListener(this);

        Adapter_StaggeredGridLayout = new MyDelegateAdapter(listItem,this,setStaggeredGridLayoutHelper(),20){
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                layoutParams.height = 260 + (position % 7) * 20;
                holder.itemView.setLayoutParams(layoutParams);
                if(position<2){
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position -6) *128);
                }else if (position % 2 ==0){
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                }else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }
            }
        };

        Adapter_StaggeredGridLayout.setOnItemClickListener(this);

        /**
         * 将生成的LayoutHelp 交给Adapter 并绑定到RecycleView 对象
         */
         //   设置Adapter列表（同时也是设置LayoutHelper列表）
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
//        将上述创建的Adapter对象放入到DelegateAdapter.Adapter列表里
        adapters.add(Adapter_linearLayout);
//        adapters.add(Adapter_StickyLayout);
//        adapters.add(Adapter_ScrollFixLayout);
        adapters.add(Adapter_GridLayout);
//        adapters.add(Adapter_FixLayout);
//        adapters.add(Adapter_FloatLayout);
        adapters.add(Adapter_ColumnLayout);
        adapters.add(Adapter_SingleLayout);
        adapters.add(Adapter_onePlusNLayout);
        adapters.add(Adapter_StaggeredGridLayout);
//        创建DelegateAdapter对象 & 将layoutManager 绑定到 DelegateAdapter
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
//        将DelegateAdapter.Adapter  列表绑定到DelegateAdapter
        delegateAdapter.setAdapters(adapters);
//        将delegateAdapter绑定到recyclerView
        recyclerView.setAdapter(delegateAdapter);

        /**
         * Item之间的间隙
         */
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(5,5,5,5);
            }
        });



    }

    /**
     * 线性布局
     */
    private LinearLayoutHelper setLinearLayout(){
        LinearLayoutHelper linearLayoutHelper =  new LinearLayoutHelper();

        //创建对应的LayoutHelper对象
        linearLayoutHelper.setItemCount(4);
        linearLayoutHelper.setPadding(10,10,10,10);
        linearLayoutHelper.setMargin(10,10,10,10);
//        linearLayoutHelper.setBgColor(Color.GRAY);
        linearLayoutHelper.setAspectRatio(6);//设置布局内宽高比

        //linearLayoutHelper特有属性
        linearLayoutHelper.setDividerHeight(10);//设置每行item距离

        linearLayoutHelper.setMarginBottom(100);
        return linearLayoutHelper;
    }



    /**
     *网格布局
     */

    private GridLayoutHelper setGridLayout(){
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        //        在构造函数中设置每行的网格个数

        //公共属性
        gridLayoutHelper.setItemCount(20);
        gridLayoutHelper.setPadding(20,20,20,20);
        gridLayoutHelper.setMargin(20,20,20,20);
        gridLayoutHelper.setBgColor(Color.GRAY);
//        gridLayoutHelper.setAspectRatio(6);//设置布局内宽高比

        //特有属性
        gridLayoutHelper.setWeights(new float[]{40,30,30});//设置每行中 每个网格宽度 占行总宽度 比例
        gridLayoutHelper.setVGap(20);//控制子元素之间的垂直距离
        gridLayoutHelper.setHGap(20);//水平
        gridLayoutHelper.setAutoExpand(false);//是否自动填充空白区域
        gridLayoutHelper.setSpanCount(3);//设置每行多少个网格

        //通过自定义SpanSizeLookUp来控制某个item的占网格个数
//        gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if(position >2){
//                    return 2;
//                }else if(position >4){
//                    return  4;
//                }else {
//                    return 1;
//                }
//            }
//        });


        return  gridLayoutHelper;
    }

    /**
     * 固定布局  固定位置 不可拖拽 不随滚动
     */
    private FixLayoutHelper setFixLayout(){
        /**
         *  参数说明：参数1--alignType 吸边基准位置 （TOP_LEFT，TOP_RIGHT，BOTTOM_LEFT，BOTTOM_RIGHT）
         *  参数2--x轴偏移量
         *  参数3--y轴偏移量
         */
        FixLayoutHelper fixLayoutHelper = new FixLayoutHelper(FixLayoutHelper.TOP_LEFT,40,100);

        //公共属性
        fixLayoutHelper.setItemCount(1);

        fixLayoutHelper.setPadding(20,20,20,20);
        fixLayoutHelper.setMargin(20,20,20,20);
        fixLayoutHelper.setBgColor(Color.GRAY);
        fixLayoutHelper.setAspectRatio(6);


//        fixLayoutHelper.setAlignType(FixLayoutHelper.TOP_LEFT);
//        fixLayoutHelper.setX(40);
//        fixLayoutHelper.setY(100);
        return fixLayoutHelper;
    }


    /**
     * 可选显示固定布局
     * 1.固定位置 不可拖拽 不随滚动
     * 2.可自由设置该item显示时机（到顶部／到底部）
     */
    private ScrollFixLayoutHelper setScrollFixLayoutHelper(){
        ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(ScrollFixLayoutHelper.TOP_RIGHT,0,0);
        /**
         *  参数说明：参数1--alignType 吸边基准位置 （TOP_LEFT，TOP_RIGHT，BOTTOM_LEFT，BOTTOM_RIGHT）
         *  参数2--x轴偏移量
         *  参数3--y轴偏移量
         */
        scrollFixLayoutHelper.setItemCount(1);//设置布局里item个数
        scrollFixLayoutHelper.setPadding(20,20,20,20);
        scrollFixLayoutHelper.setMargin(20,20,20,20);
        scrollFixLayoutHelper.setBgColor(Color.GRAY);
        scrollFixLayoutHelper.setAspectRatio(6);

        //scrollFixLayoutHelper 特有属性
        scrollFixLayoutHelper.setAlignType(FixLayoutHelper.TOP_LEFT);
        scrollFixLayoutHelper.setX(30);
        scrollFixLayoutHelper.setY(50);

        scrollFixLayoutHelper.setShowType(ScrollFixLayoutHelper.SHOW_ON_ENTER);
        return scrollFixLayoutHelper;
    }


    /**
     * 浮动布局
     * 1。可随意拖动，但最后终会被吸边
     * 2。不随页面滚动而滚动
     */

    private FloatLayoutHelper setFloatLayoutHelper(){
        FloatLayoutHelper floatLayoutHelper = new FloatLayoutHelper();

        //公共属性
        floatLayoutHelper.setItemCount(1);
        floatLayoutHelper.setPadding(20,20,20,20);
        floatLayoutHelper.setMargin(20,20,20,20);
        floatLayoutHelper.setBgColor(Color.GRAY);
        floatLayoutHelper.setAspectRatio(6);
        //特有属性
        floatLayoutHelper.setDefaultLocation(300,300);//设置布局里Item的初始位置

        return  floatLayoutHelper;
    }

    /**
     * 栏格布局
     * 可理解为只有一行的线性布局
     */

    private ColumnLayoutHelper SetColumnLayoutHelper(){
        ColumnLayoutHelper columnLayoutHelper = new ColumnLayoutHelper();

        //公共属性
        columnLayoutHelper.setItemCount(3);
        columnLayoutHelper.setPadding(20,20,20,20);
        columnLayoutHelper.setMargin(20,20,20,20);
        columnLayoutHelper.setBgColor(Color.GRAY);
        columnLayoutHelper.setAspectRatio(6);

        //特有属性
        columnLayoutHelper.setWeights(new float[]{30,40,30});//设置每行中 每个item宽度 占该行总宽度 比例

        return columnLayoutHelper;
    }


    /**
     * 通拦布局
     * 布局只有一栏 该栏只有一个item
     */

    private SingleLayoutHelper SetSingleLayoutHelper(){
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();

        //公共属性
        singleLayoutHelper.setItemCount(3);
        singleLayoutHelper.setPadding(20,20,20,20);
        singleLayoutHelper.setMargin(20,20,20,20);
        singleLayoutHelper.setBgColor(Color.GRAY);
        singleLayoutHelper.setAspectRatio(6);

        //特有属性
        singleLayoutHelper.setWeights(new float[]{30,40,30});//设置每行中 每个item宽度 占该行总宽度 比例

        return singleLayoutHelper;
    }

    /**
     * 一拖N布局
     * 将布局分为不同比例，最多一拖四
     * 1t1:
     * 1 2
     * -----
     * 1t2:
     * 1 2
     * 1 3
     * -----
     * 1t3:
     * 1 2 2
     * 1 3 4
     * -----
     * 1t4:
     * 1 2 2 2
     * 1 3 4 5
     */

    private OnePlusNLayoutHelper setOnePlusNlayoutHelper(){
        /**
         * 在构造函数里传入显示的Item数
         * 最多1拖4 即5个
         */
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper(5);
        //公共属性
        onePlusNLayoutHelper.setItemCount(3);
        onePlusNLayoutHelper.setPadding(20,20,20,20);
        onePlusNLayoutHelper.setMargin(20,20,20,20);
        onePlusNLayoutHelper.setBgColor(Color.GRAY);
        onePlusNLayoutHelper.setAspectRatio(3);
        return onePlusNLayoutHelper;
    }


    /**
     * 吸边布局
     * 1。布局只有一个item 显示逻辑如下：
     * a。当它包含的组件处于屏幕可见范围是，像正常的组件一样随页面滚动而滚动
     * b。当组件将要被滑出屏幕返回的时候，可以洗到吸到屏幕的顶部或者底部，实现一种吸住效果
     */

    private StickyLayoutHelper setStickyLayoutHelper(){
        StickyLayoutHelper stickyLayoutHelper  = new StickyLayoutHelper();
        //公共属性
        stickyLayoutHelper.setItemCount(3);
        stickyLayoutHelper.setPadding(20,20,20,20);
        stickyLayoutHelper.setMargin(20,20,20,20);
        stickyLayoutHelper.setBgColor(Color.GRAY);
        stickyLayoutHelper.setAspectRatio(3);

        //特有属性
        //true 吸在顶部；false 吸在底部
        stickyLayoutHelper.setStickyStart(true);

        stickyLayoutHelper.setOffset(100);//设置吸边位置的偏移量


        return stickyLayoutHelper;
    }

    /**
     * 瀑布流
     * 网格布局每一栏的item高度相等
     * 瀑布流布局每一栏的item高度可以不等
     */

    private StaggeredGridLayoutHelper setStaggeredGridLayoutHelper(){
        StaggeredGridLayoutHelper staggeredGridLayoutHelper = new StaggeredGridLayoutHelper();

        //公有属性
        staggeredGridLayoutHelper.setItemCount(20);
        staggeredGridLayoutHelper.setPadding(20,20,20,20);
        staggeredGridLayoutHelper.setMargin(20,20,20,20);

        staggeredGridLayoutHelper.setBgColor(Color.GRAY);
        staggeredGridLayoutHelper.setAspectRatio(3);


        //特有属性
        staggeredGridLayoutHelper.setLane(3);//设置控制瀑布流每行的Item数
        staggeredGridLayoutHelper.setHGap(20);//设置子元素之间的水平间距
        staggeredGridLayoutHelper.setVGap(15);//垂直;

     return staggeredGridLayoutHelper;
    }


    @Override
    public void onItemClick(View view, int pos) {
        Toast.makeText(this,(String)listItem.get(pos).get("ItemTitle"),Toast.LENGTH_SHORT).show();
    }
}
