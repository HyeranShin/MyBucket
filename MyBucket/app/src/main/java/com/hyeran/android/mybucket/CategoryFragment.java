package com.hyeran.android.mybucket;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryFragment extends Fragment implements View.OnClickListener {

    // CATEGORY_INTEX
    // All:0, Goal:1, Learning:2, Travel:3, Wishlist:4, Sharing:5, Etc:6
    int category_index;

    ListFragment listFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        listFragment = new ListFragment();

        v.findViewById(R.id.iv_all_category).setOnClickListener(this);
        v.findViewById(R.id.iv_goal_category).setOnClickListener(this);
        v.findViewById(R.id.iv_learning_category).setOnClickListener(this);
        v.findViewById(R.id.iv_travel_category).setOnClickListener(this);
        v.findViewById(R.id.iv_wishlist_category).setOnClickListener(this);
        v.findViewById(R.id.iv_sharing_category).setOnClickListener(this);
        v.findViewById(R.id.iv_etc_category).setOnClickListener(this);

        return(v);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_all_category:
                category_index = 0;
                break;
            case R.id.iv_goal_category:
                category_index = 1;
                break;
            case R.id.iv_learning_category:
                category_index = 2;
                break;
            case R.id.iv_travel_category:
                category_index = 3;
                break;
            case R.id.iv_wishlist_category:
                category_index = 4;
                break;
            case R.id.iv_sharing_category:
                category_index = 5;
                break;
            case R.id.iv_etc_category:
                category_index = 6;
                break;
        }
        Bundle args = new Bundle();
        args.putInt("CATEGORY_INDEX", category_index);
        listFragment.setArguments(args);
        replaceFragment(listFragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framge_main, fragment);
        fragmentTransaction.commit();
    }
}
