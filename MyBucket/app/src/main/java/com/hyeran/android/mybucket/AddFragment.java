package com.hyeran.android.mybucket;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;

public class AddFragment extends Fragment{

    Realm realm;

    View v;
    // 데이터 입력 위젯
    EditText title, content, companion, place, hashtag1, hashtag2, hashtag3;
    DatePicker start_datePicker, end_datePicker;
    Spinner categorySpinner;

    // 중요도(별)
    int total_count = 0;
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    int count4 = 0;
    int count5 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add, container, false);

        title = v.findViewById(R.id.et_title_add);
        content = v.findViewById(R.id.et_content_add);
        companion = v.findViewById(R.id.et_companion_add);
        place = v.findViewById(R.id.et_place_add);
        start_datePicker = v.findViewById(R.id.datepicker_start_add);
        end_datePicker = v.findViewById(R.id.datepicker_end_add);
        hashtag1 = v.findViewById(R.id.et_hashtag1_add);
        hashtag2 = v.findViewById(R.id.et_hashtag2_add);
        hashtag3 = v.findViewById(R.id.et_hashtag3_add);

        changeStarState();
        setSpinner();

        categorySpinner = v.findViewById(R.id.CategorySpinner);

        final CircleImageView imageView = (CircleImageView) v.findViewById(R.id.CategoryImageView);
        //final ImageView imageView = (ImageView) v.findViewById(R.id.CategoryImageViewImage);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(categorySpinner.getSelectedItemPosition() == 0) {

                    imageView.setImageResource(R.drawable.goal);
                    Drawable color = new ColorDrawable(getResources().getColor(R.color.yellow));
                    Drawable image = getResources().getDrawable(R.drawable.goal);
                    LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{color, image});
                    imageView.setImageDrawable(layerDrawable);

                }
                else if(categorySpinner.getSelectedItemPosition() == 1) {
                    imageView.setImageResource(R.drawable.learning);
                    Drawable color = new ColorDrawable(getResources().getColor(R.color.orange));
                    Drawable image = getResources().getDrawable(R.drawable.learning);
                    LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{color, image});
                    imageView.setImageDrawable(layerDrawable);
                }
                else if(categorySpinner.getSelectedItemPosition() == 2) {
                    imageView.setImageResource(R.drawable.travel);
                    Drawable color = new ColorDrawable(getResources().getColor(R.color.green));
                    Drawable image = getResources().getDrawable(R.drawable.travel);
                    LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{color, image});
                    imageView.setImageDrawable(layerDrawable);
                }
                else if(categorySpinner.getSelectedItemPosition() == 3) {
                    imageView.setImageResource(R.drawable.wishlist);
                    Drawable color = new ColorDrawable(getResources().getColor(R.color.blue));
                    Drawable image = getResources().getDrawable(R.drawable.wishlist);
                    LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{color, image});
                    imageView.setImageDrawable(layerDrawable);
                }
                else if(categorySpinner.getSelectedItemPosition() == 4) {
                    imageView.setImageResource(R.drawable.sharing);
                    Drawable color = new ColorDrawable(getResources().getColor(R.color.pink));
                    Drawable image = getResources().getDrawable(R.drawable.sharing);
                    LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{color, image});
                    imageView.setImageDrawable(layerDrawable);
                }
                else {
                    imageView.setImageResource(R.drawable.etc);
                    Drawable color = new ColorDrawable(getResources().getColor(R.color.purple));
                    Drawable image = getResources().getDrawable(R.drawable.etc);
                    LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{color, image});
                    imageView.setImageDrawable(layerDrawable);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        init(); // Realm 초기화

        v.findViewById(R.id.btn_add_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();

                Bundle args = new Bundle();
                args.putInt("CATEGORY_INDEX", 0);
                ListFragment listFragment = new ListFragment();
                listFragment.setArguments(args);
                replaceFragment(listFragment);
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        title.setText("");
        content.setText("");
        companion.setText("");
        place.setText("");
        categorySpinner.setSelection(0);
        hashtag1.setText("");
        hashtag2.setText("");
        hashtag3.setText("");
    }

    // Realm 초기화
    private void init() {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance(); // 쓰레드의 Realm 인스턴스 얻기
    }

    // Realm 데이터 삽입
    private void insertData() {
        BucketlistVO bucketlistVO = new BucketlistVO();
        realm.beginTransaction();

        // 제목
        bucketlistVO.title = title.getText().toString();
        // 내용
        bucketlistVO.content = content.getText().toString();
        // 함께 하는 사람
        bucketlistVO.companion = companion.getText().toString();
        // 장소
        bucketlistVO.place = place.getText().toString();
        // 기한
        // start
        bucketlistVO.start_year = start_datePicker.getYear();
        bucketlistVO.start_month = start_datePicker.getMonth()+1;   // getMonth(): 0이 1월을 뜻하므로 +1
        bucketlistVO.start_day = start_datePicker.getDayOfMonth();
        // end
        bucketlistVO.end_year = end_datePicker.getYear();
        bucketlistVO.end_month = end_datePicker.getMonth()+1;   // getMonth(): 0이 1월을 뜻하므로 +1
        bucketlistVO.end_day = end_datePicker.getDayOfMonth();
        // 카테고리
        bucketlistVO.category_index = categorySpinner.getSelectedItemPosition()+1;
        // 해시태그
        bucketlistVO.hashtag1 = hashtag1.getText().toString();
        bucketlistVO.hashtag2 = hashtag2.getText().toString();
        bucketlistVO.hashtag3 = hashtag3.getText().toString();
        // 중요도
        bucketlistVO.star_count = total_count;
        // 도장(상태)
        bucketlistVO.state = 0;

        realm.copyToRealm(bucketlistVO);    // 객체를 Realm으로 복사
        realm.commitTransaction();
    }

    private void setSpinner() {
        String[] category = {"Goal", "Learning", "Travel", "WishList", "Sharing", "Etc"};

        categorySpinner = v.findViewById(R.id.CategorySpinner);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, category);
        categorySpinner.setAdapter(adapter);
    }

    private void changeStarState() {
        final ImageButton star1 = v.findViewById(R.id.Star1);
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count1 % 2 == 0) {star1.setColorFilter(getResources().getColor(R.color.staryellow)); count1++; total_count++;}
                else {star1.setColorFilter(null); count1++; total_count--;}
            }
        });

        final ImageButton star2 = v.findViewById(R.id.Star2);
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count2 % 2 == 0) {star2.setColorFilter(getResources().getColor(R.color.staryellow)); count2++; total_count++;}
                else {star2.setColorFilter(null); count2++; total_count--;}
            }
        });

        final ImageButton star3 = v.findViewById(R.id.Star3);
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count3 % 2 == 0) {star3.setColorFilter(getResources().getColor(R.color.staryellow));count3++; total_count++;}
                else {star3.setColorFilter(null); count3++; total_count--;}
            }
        });

        final ImageButton star4 = v.findViewById(R.id.Star4);
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count4 % 2 == 0) {star4.setColorFilter(getResources().getColor(R.color.staryellow)); count4++; total_count++;}
                else {star4.setColorFilter(null); count4++; total_count--;}
            }
        });

        final ImageButton star5 = v.findViewById(R.id.Star5);
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count5 % 2 == 0) {star5.setColorFilter(getResources().getColor(R.color.staryellow)); count5++; total_count++;}
                else {star5.setColorFilter(null); count5++; total_count--;}
            }
        });
    }

    // 프래그먼트 교체
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framge_main, fragment);
        fragmentTransaction.commit();
    }
}
