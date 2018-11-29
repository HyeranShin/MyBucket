package com.hyeran.android.mybucket;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class ListFragment extends Fragment {

    Realm realm;

    int category_index;
    // All:0, Goal:1, Learning:2, Travel:3, Wishlist:4, Sharing:5, Etc:6

    RecyclerView rv_bucketlist;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_list, container, false);
        category_index = getArguments().getInt("CATEGORY_INDEX");

        rv_bucketlist = v.findViewById(R.id.rv_bucketlist);

        init();

        return v;
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
