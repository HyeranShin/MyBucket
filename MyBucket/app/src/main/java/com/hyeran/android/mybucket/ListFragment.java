package com.hyeran.android.mybucket;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import io.realm.Realm;

public class ListFragment extends Fragment {

    View v;
    Realm realm;
    ArrayList<BucketlistVO> bucketlist;

    // CATEGORY_INTEX
    // All:0, Goal:1, Learning:2, Travel:3, Wishlist:4, Sharing:5, Etc:6

    RecyclerView rv_bucketlist;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_list, container, false);

        init();

//        int category_index = getArguments().getInt("CATEGORY_INDEX");
        // 해당 category_index에 맞는 데이터 꺼내기

        return v;
    }

    // Realm 초기화
    private void init() {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance(); // 쓰레드의 Realm 인스턴스 얻기

        // RecyclerView에 Realm 데이터 가져오기
        rv_bucketlist = v.findViewById(R.id.rv_bucketlist);
        rv_bucketlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rv_bucketlist.setLayoutManager(layoutManager);
        bucketlist = new ArrayList<>(realm.where(BucketlistVO.class).findAll());
        BucketlistAdapter bucketlistAdapter = new BucketlistAdapter(bucketlist);
        rv_bucketlist.setAdapter(bucketlistAdapter);
    }
}
