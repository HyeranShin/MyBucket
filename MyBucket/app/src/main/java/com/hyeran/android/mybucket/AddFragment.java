package com.hyeran.android.mybucket;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AddFragment extends Fragment{

    View view;
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    int count4 = 0;
    int count5 = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);

        final ImageButton star1 = (ImageButton) view.findViewById(R.id.Star1);
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count1 % 2 == 0) {star1.setColorFilter(getResources().getColor(R.color.staryellow)); count1++;}
                else {star1.setColorFilter(null); count1++;}
            }
        });

        final ImageButton star2 = (ImageButton) view.findViewById(R.id.Star2);
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count2 % 2 == 0) {star2.setColorFilter(getResources().getColor(R.color.staryellow)); count2++;}
                else {star2.setColorFilter(null); count2++;}
            }
        });

        final ImageButton star3 = (ImageButton) view.findViewById(R.id.Star3);
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count3 % 2 == 0) {star3.setColorFilter(getResources().getColor(R.color.staryellow));count3++;}
                else {star3.setColorFilter(null); count3++;}
            }
        });

        final ImageButton star4 = (ImageButton) view.findViewById(R.id.Star4);
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count4 % 2 == 0) {star4.setColorFilter(getResources().getColor(R.color.staryellow)); count4++;}
                else {star4.setColorFilter(null); count4++;}
            }
        });

        final ImageButton star5 = (ImageButton) view.findViewById(R.id.Star5);
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count5 % 2 == 0) {star5.setColorFilter(getResources().getColor(R.color.staryellow)); count5++;}
                else {star5.setColorFilter(null); count5++;}
            }
        });

        String[] category = {"Goal", "Learning", "Travel", "WishList", "Sharing", "Etc"};

        Spinner categorySpinner = (Spinner) view.findViewById(R.id.CategorySpinner);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, category);
        categorySpinner.setAdapter(adapter);

        return view;
    }
}
