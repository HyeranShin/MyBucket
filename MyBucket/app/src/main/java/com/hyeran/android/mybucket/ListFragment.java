package com.hyeran.android.mybucket;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    // CATEGORY_INTEX
    // All:0, Goal:1, Learning:2, Travel:3, Wishlist:4, Sharing:5, Etc:6

    RecyclerView rv_bucketlist;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_list, container, false);

        int category_index = getArguments().getInt("CATEGORY_INDEX");
        // 해당 category_index에 맞는 데이터 꺼내기

        rv_bucketlist = v.findViewById(R.id.rv_bucketlist);
        rv_bucketlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rv_bucketlist.setLayoutManager(layoutManager);

        ArrayList<BucketlistData> bucketlistDataArrayList = new ArrayList<>();
        bucketlistDataArrayList.add(new BucketlistData(1, "유럽 여행 가기"));
        bucketlistDataArrayList.add(new BucketlistData(2, "버킷 리스트 어플 제작"));
        bucketlistDataArrayList.add(new BucketlistData(3, "해외 봉사"));
        bucketlistDataArrayList.add(new BucketlistData(4, "내 집 마련"));
        bucketlistDataArrayList.add(new BucketlistData(5, "수영 배우기"));

        BucketlistAdapter bucketlistAdapter = new BucketlistAdapter(bucketlistDataArrayList);
        rv_bucketlist.setAdapter(bucketlistAdapter);

        return v;
    }
}
