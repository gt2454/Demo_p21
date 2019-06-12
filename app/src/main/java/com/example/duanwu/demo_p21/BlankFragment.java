package com.example.duanwu.demo_p21;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<Bean.DataBean.DatasBean> list;
    private Adapater adapater;
    private LocalBroadcastManager localBroadcastManager;
    private itemClick itemClickReceiver;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_blank, container, false);
        list = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());

        recyclerView = v.findViewById(R.id.rew);
        LinearLayoutManager  linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapater = new Adapater(getContext(),list);
        recyclerView.setAdapter(adapater);

        init();
        adapater.setOnItemCLickListener(new Adapater.OnItemCLickListener() {
            @Override
            public void onItemClick(View v, int position) {
//设置意图过滤器  注册时的 Action 与 这里的 Action 要一致
                Intent intent = new Intent("itemClick");
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("image",list.get(position).getEnvelopePic());
                intent.putExtra("desc",list.get(position).getDesc());
                        //用广播管理器发送广播
                        localBroadcastManager.sendBroadcast(intent);
            }
        });


        return  v;
    }
    //在 onResume 中动态注册广播
    @Override
    public void onResume() {
        super.onResume();

        //获取广播对象（全局）
        itemClickReceiver = new itemClick();
        //设置意图过滤器，用与判断是哪个广播接收
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("itemClick");
        //用广播管理器注册广播
        localBroadcastManager.registerReceiver(itemClickReceiver,intentFilter);

    }

    private void init() {
        Retrofit  retrofit =new Retrofit.Builder()
                .baseUrl(MyServler.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyServler myServler = retrofit.create(MyServler.class);
        Map<String,Object> map=new HashMap<>();
        map.put("cid","294");
        Observable<Bean> image = myServler.getImage(map);
                     image.subscribeOn(Schedulers.io())
                             .observeOn(AndroidSchedulers.mainThread())
                             .subscribe(new Observer<Bean>() {
                                 @Override
                                 public void onSubscribe(Disposable d) {

                                 }

                                 @Override
                                 public void onNext(Bean bean) {
                                     List<Bean.DataBean.DatasBean> datas = bean.getData().getDatas();
                                     list.addAll(datas);
                                     adapater.notifyDataSetChanged();


                                 }

                                 @Override
                                 public void onError(Throwable e) {
                                     Log.i("------",e.getMessage());
                                 }

                                 @Override
                                 public void onComplete() {

                                 }
                             });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        //用广播管理器注销广播
        localBroadcastManager.unregisterReceiver(itemClickReceiver);
    }
}

