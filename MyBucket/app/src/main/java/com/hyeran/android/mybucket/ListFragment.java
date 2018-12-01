package com.hyeran.android.mybucket;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class ListFragment extends Fragment {

    Realm realm;

    int category_index;
    // All:0, Goal:1, Learning:2, Travel:3, Wishlist:4, Sharing:5, Etc:6

    RecyclerView rv_bucketlist;
    RecyclerView.LayoutManager layoutManager;
    ImageView filter_run, filter_medal, filter_waiting;
    TextView filter_all;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_list, container, false);
        category_index = getArguments().getInt("CATEGORY_INDEX");

        rv_bucketlist = v.findViewById(R.id.rv_bucketlist);
        filter_run = v.findViewById(R.id.iv_filtering_run);
        filter_medal = v.findViewById(R.id.iv_filtering_medal);
        filter_all = v.findViewById(R.id.tv_filtering_all);
        filter_waiting = v.findViewById(R.id.filtering_waiting);

        filter_waiting.setColorFilter(Color.parseColor("#FFEEEC"));
        filter_run.setColorFilter(Color.parseColor("#3669CF"));
        filter_medal.setColorFilter(Color.parseColor("#FFCD12"));

        init();

        filtering();

        return v;
    }

    public void filtering() {
        filter_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BucketlistVO> bucketlist_all;
                if(category_index==0) bucketlist_all = new ArrayList<>(realm.where(BucketlistVO.class).findAll());
                else bucketlist_all = new ArrayList<>(realm.where(BucketlistVO.class).equalTo("category_index", category_index).findAll());
                BucketlistAdapter bucketlistAdapter = new BucketlistAdapter(bucketlist_all);
                rv_bucketlist.setAdapter(bucketlistAdapter);
            }
        });
        filter_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BucketlistVO> bucketlist_run;
                if(category_index==0) bucketlist_run = new ArrayList<>(realm.where(BucketlistVO.class).equalTo("state", 1).findAll());
                else bucketlist_run = new ArrayList<>(realm.where(BucketlistVO.class).equalTo("category_index", category_index).equalTo("state", 1).findAll());
                BucketlistAdapter bucketlistAdapter = new BucketlistAdapter(bucketlist_run);
                rv_bucketlist.setAdapter(bucketlistAdapter);
            }
        });
        filter_medal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BucketlistVO> bucketlist_medal;
                if(category_index==0) bucketlist_medal = new ArrayList<>(realm.where(BucketlistVO.class).equalTo("state", 2).findAll());
                else bucketlist_medal = new ArrayList<>(realm.where(BucketlistVO.class).equalTo("category_index", category_index).equalTo("state", 2).findAll());
                BucketlistAdapter bucketlistAdapter = new BucketlistAdapter(bucketlist_medal);
                rv_bucketlist.setAdapter(bucketlistAdapter);
            }
        });
        filter_waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BucketlistVO> bucketlist_waiting;
                if(category_index==0) bucketlist_waiting = new ArrayList<>(realm.where(BucketlistVO.class).equalTo("state", 0).findAll());
                else bucketlist_waiting = new ArrayList<>(realm.where(BucketlistVO.class).equalTo("category_index", category_index).equalTo("state", 0).findAll());
                BucketlistAdapter bucketlistAdapter = new BucketlistAdapter(bucketlist_waiting);
                rv_bucketlist.setAdapter(bucketlistAdapter);
            }
        });
    }

    // Realm 초기화
    private void init() {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance(); // 쓰레드의 Realm 인스턴스 얻기

        // RecyclerView에 Realm 데이터 가져오기
        rv_bucketlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rv_bucketlist.setLayoutManager(layoutManager);

        // 해당 category_index에 맞는 데이터 꺼내기
        ArrayList<BucketlistVO> bucketlist;
        if(category_index==0) bucketlist = new ArrayList<>(realm.where(BucketlistVO.class).findAll());
        else bucketlist = new ArrayList<>(realm.where(BucketlistVO.class).equalTo("category_index", category_index).findAll());
        BucketlistAdapter bucketlistAdapter = new BucketlistAdapter(bucketlist);
        rv_bucketlist.setAdapter(bucketlistAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<BucketlistVO> bucketlist;
        if(category_index==0) bucketlist = new ArrayList<>(realm.where(BucketlistVO.class).findAll());
        else bucketlist = new ArrayList<>(realm.where(BucketlistVO.class).equalTo("category_index", category_index).findAll());
        BucketlistAdapter bucketlistAdapter = new BucketlistAdapter(bucketlist);
        rv_bucketlist.setAdapter(bucketlistAdapter);
    }
}
