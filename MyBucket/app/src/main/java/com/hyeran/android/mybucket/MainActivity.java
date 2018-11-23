package com.hyeran.android.mybucket;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MypageFragment mypageFragment;
    CategoryFragment categoryFragment;
    AddFragment addFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mypageFragment = new MypageFragment();
        categoryFragment = new CategoryFragment();
        addFragment = new AddFragment();

        replaceFragment(mypageFragment);
//        replaceFragment(categoryFragment);

        findViewById(R.id.mypage_tab_main).setOnClickListener(this);
        findViewById(R.id.list_tab_main).setOnClickListener(this);
        findViewById(R.id.add_tab_main).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mypage_tab_main:
                replaceFragment(mypageFragment);
                break;
            case R.id.list_tab_main:
                replaceFragment(categoryFragment);
                break;
            case R.id.add_tab_main:
                replaceFragment(addFragment);
                break;
        }
    }

    // 프래그먼트 교체
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framge_main, fragment);
        fragmentTransaction.commit();
    }
}
