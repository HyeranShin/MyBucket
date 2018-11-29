package com.hyeran.android.mybucket;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    Realm realm;
    RealmResults<BucketlistVO> bucketlistVO;
    String TITLE;

    Spinner categorySpinner;
    Button btnChangeState;
    ImageButton btnImage, btnVideo;
    ImageButton ibStar1, ibStar2, ibStar3, ibStar4, ibStar5;

    ViewSwitcher vsTitle, vsContent, vsCompanion, vsPlace;
    ViewSwitcher vsHashtag1, vsHashtag2, vsHashtag3, vsOpinion;

    // 데이터 세팅할 뷰
    TextView tvTitle, tvContent, tvCompanion, tvPlace;
    TextView tvHashtag1, tvHashtag2, tvHashtag3, tvOpinion;

    // 데이터 수정할 뷰
    EditText etTitle, etContent, etCompanion, etPlace;
    EditText etHashtag1, etHashtag2, etHashtag3, etOpinion;

    int count = 0;

    int startday, startmonth, startyear;
    int endday, endmonth,endyear;

    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    int count4 = 0;
    int count5 = 0;
    int start_count = 0;

    LinearLayout modifyDateLayout;
    TextView detailDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("자세히 보기");
        TITLE = getIntent().getStringExtra("TITLE");

        findViewById(R.id.btn_delete_detail).setOnClickListener(this);
        findViewById(R.id.btn_modify_detail).setOnClickListener(this);
        findViewById(R.id.btn_save_detail).setOnClickListener(this);
        btnChangeState = findViewById(R.id.btn_change_state_detail);
        btnChangeState.setOnClickListener(this);

//        vsTitle = findViewById(R.id.TItleSwitcher);
        tvTitle = findViewById(R.id.DetailTitle);
        etTitle = findViewById(R.id.DetailTItleEdit);

        vsContent = findViewById(R.id.ContentSwitcher);
        tvContent = findViewById(R.id.DetailContent);
        etContent = findViewById(R.id.DetailContentEdit);

        vsCompanion = findViewById(R.id.PersonSwitcher);
        tvCompanion = findViewById(R.id.DetailPerson);
        etCompanion = findViewById(R.id.DetailPersonEdit);

        vsPlace = findViewById(R.id.PlaceSwitcher);
        tvPlace = findViewById(R.id.DetailPlace);
        etPlace = findViewById(R.id.DetailPlaceEdit);

        vsHashtag1 = findViewById(R.id.HashTag1Switcher) ;
        tvHashtag1 = findViewById(R.id.DetailHashtag1);
        etHashtag1 = findViewById(R.id.HastTag1EditText);

        vsHashtag2 = findViewById(R.id.HastTag2Switcher) ;
        tvHashtag2 = findViewById(R.id.DetailHashtag2);
        etHashtag2 = findViewById(R.id.HastTag2EditText);

        vsHashtag3 = findViewById(R.id.HashTag3Switcher) ;
        tvHashtag3 = findViewById(R.id.DetailHashtag3);
        etHashtag3 = findViewById(R.id.HashTag3EditText);

        vsOpinion = findViewById(R.id.OpinionSwitcher);
        tvOpinion = findViewById(R.id.DetailOpinion);
        etOpinion = findViewById(R.id.DetailOpinionEditText);

        btnImage = findViewById(R.id.btn_image_detail);
        btnVideo = findViewById(R.id.btn_video_detail);
        btnImage.setVisibility(View.INVISIBLE);
        btnVideo.setVisibility(View.INVISIBLE);

        ibStar1 = findViewById(R.id.DetailStar1);
        ibStar2 = findViewById(R.id.DetailStar2);
        ibStar3 = findViewById(R.id.DetailStar3);
        ibStar4 = findViewById(R.id.DetailStar4);
        ibStar5 = findViewById(R.id.DetailStar5);
        ibStar1.setEnabled(false);
        ibStar2.setEnabled(false);
        ibStar3.setEnabled(false);
        ibStar4.setEnabled(false);
        ibStar5.setEnabled(false);

        modifyDateLayout = findViewById(R.id.DateModifyLayout);
        modifyDateLayout.setVisibility(View.GONE);

        detailDate = findViewById(R.id.DetailDate);

        setSpinner();

        init();

        categorySpinner = findViewById(R.id.DetailCategorySpinner);
        categorySpinner.setEnabled(false);

        final CircleImageView imageView = findViewById(R.id.DetailCategoryImageView);

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
    }

    @Override
    public void onClick(View v) {
        ViewSwitcher btnSwitcher = findViewById(R.id.btn_switcher_detail);
        DatePicker datePickerStart = findViewById(R.id.DetailDatePickerStart);
        DatePicker datePickerEnd = findViewById(R.id.DetailDatePickerEnd);

        switch (v.getId()) {
            case R.id.btn_delete_detail:
                realm.beginTransaction();
                bucketlistVO.deleteAllFromRealm();
                realm.commitTransaction();
                finish();
                break;

            case R.id.btn_change_state_detail:
                realm.beginTransaction();
                switch (bucketlistVO.get(0).state) {
                    // 초기:0, 진행중:1, 달성완료:2
                    case 0:
                        bucketlistVO.get(0).state = 1;
                        btnChangeState.setText("달성 완료로 변경");
                        break;
                    case 1:
                        bucketlistVO.get(0).state = 2;
                        btnChangeState.setText("달성 취소로 변경");
                        break;
                    case 2:
                        bucketlistVO.get(0).state = 0;
                        btnChangeState.setText("진행중으로 변경");
                        break;
                }
                realm.copyToRealm(bucketlistVO);
                realm.commitTransaction();
                break;

            case R.id.btn_modify_detail:

                detailDate.setVisibility(View.GONE);
                modifyDateLayout.setVisibility(View.VISIBLE);

                datePickerStart.updateDate(bucketlistVO.get(0).start_year, bucketlistVO.get(0).start_month-1, bucketlistVO.get(0).start_day);
                datePickerEnd.updateDate(bucketlistVO.get(0).end_year, bucketlistVO.get(0).end_month-1, bucketlistVO.get(0).end_day);
                categorySpinner.setEnabled(true);

                etTitle.setText(tvTitle.getText().toString());
                etContent.setText(tvContent.getText().toString());
                etCompanion.setText(tvCompanion.getText().toString());
                etPlace.setText(tvPlace.getText().toString());
                etHashtag1.setText(tvHashtag1.getText().toString());
                etHashtag2.setText(tvHashtag2.getText().toString());
                etHashtag3.setText(tvHashtag3.getText().toString());
                etOpinion.setText(tvOpinion.getText().toString());

                btnImage.setVisibility(View.VISIBLE);
                btnVideo.setVisibility(View.VISIBLE);

                if(count % 2 == 0) {
//                    vsTitle.showNext();
                    vsContent.showNext(); vsCompanion.showNext(); vsPlace.showNext();
                    vsHashtag1.showNext(); vsHashtag2.showNext(); vsHashtag3.showNext(); vsOpinion.showNext(); count++;
                }

                ibStar1.setEnabled(true);
                ibStar2.setEnabled(true);
                ibStar3.setEnabled(true);
                ibStar4.setEnabled(true);
                ibStar5.setEnabled(true);

                if(!(ibStar1.getColorFilter() == null)) start_count++;
                if(!(ibStar2.getColorFilter() == null)) start_count++;
                if(!(ibStar3.getColorFilter() == null)) start_count++;
                if(!(ibStar4.getColorFilter() == null)) start_count++;
                if(!(ibStar5.getColorFilter() == null)) start_count++;

                ibStar1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//
//                        if(count1 % 2 == 0) {ibStar1.setColorFilter(getResources().getColor(R.color.staryellow)); count1++;}
//                        else {ibStar1.setColorFilter(null); count1++;}

                        if(ibStar1.getColorFilter() == null) {
                            ibStar1.setColorFilter(getResources().getColor(R.color.staryellow)); start_count++;
                        } else {
                            ibStar1.setColorFilter(null); start_count--;
                        }
                    }
                });

                ibStar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        if(count2 % 2 == 0) {ibStar2.setColorFilter(getResources().getColor(R.color.staryellow)); count2++;}
//                        else {ibStar2.setColorFilter(null); count2++;}

                        if(ibStar2.getColorFilter() == null) {
                            ibStar2.setColorFilter(getResources().getColor(R.color.staryellow)); start_count++;
                        } else {
                            ibStar2.setColorFilter(null); start_count--;
                        }
                    }
                });

                ibStar3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//
//                        if(count3 % 2 == 0) {ibStar3.setColorFilter(getResources().getColor(R.color.staryellow)); count3++; }
//                        else {ibStar3.setColorFilter(null); count3++; }

                        if(ibStar3.getColorFilter() == null) {
                            ibStar3.setColorFilter(getResources().getColor(R.color.staryellow)); start_count++;
                        } else {
                            ibStar3.setColorFilter(null); start_count--;
                        }
                    }
                });

                ibStar4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        if(count4 % 2 == 0) {ibStar4.setColorFilter(getResources().getColor(R.color.staryellow)); count4++; }
//                        else {ibStar4.setColorFilter(null); count4++; }

                        if(ibStar4.getColorFilter() == null) {
                            ibStar4.setColorFilter(getResources().getColor(R.color.staryellow)); start_count++;
                        } else {
                            ibStar4.setColorFilter(null); start_count--;
                        }
                    }
                });

                ibStar5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        if(count5 % 2 == 0) {ibStar5.setColorFilter(getResources().getColor(R.color.staryellow)); count5++; }
//                        else {ibStar5.setColorFilter(null); count5++; }

                        if(ibStar5.getColorFilter() == null) {
                            ibStar5.setColorFilter(getResources().getColor(R.color.staryellow)); start_count++;
                        } else {
                            ibStar5.setColorFilter(null); start_count--;
                        }
                    }
                });

                btnSwitcher.showNext();

                break;

            case R.id.btn_save_detail:

                detailDate.setVisibility(View.VISIBLE);

                startday = datePickerStart.getDayOfMonth();
                startmonth = datePickerStart.getMonth() + 1;
                startyear = datePickerStart.getYear();
                //datePickerStart.updateDate(startyear, startmonth, startday);


                endday = datePickerEnd.getDayOfMonth();
                endmonth = datePickerEnd.getMonth() + 1;
                endyear = datePickerEnd.getYear();
                //datePickerEnd.updateDate(endyear, endmonth, endday);

                detailDate.setText(startyear + "년 " + startmonth + "월 " + startday + "일 " + "- " + endyear + "년 " + endmonth + "월 " + endday + "일 ");

                modifyDateLayout.setVisibility(View.GONE);

                tvTitle.setText(etTitle.getText().toString());
                tvContent.setText(etContent.getText().toString());
                tvCompanion.setText(etCompanion.getText().toString());
                tvPlace.setText(etPlace.getText().toString());
                tvHashtag1.setText(etHashtag1.getText().toString());
                tvHashtag2.setText(etHashtag2.getText().toString());
                tvHashtag3.setText(etHashtag3.getText().toString());

                tvOpinion.setText(etOpinion.getText().toString());

                if(count % 2 == 1 ) {
//                    vsTitle.showNext();
                    vsContent.showNext();vsCompanion.showNext(); vsPlace.showNext();
                    vsHashtag1.showNext(); vsHashtag2.showNext(); vsHashtag3.showNext(); vsOpinion.showNext();count++;
                }

                btnImage.setVisibility(View.INVISIBLE);
                btnVideo.setVisibility(View.INVISIBLE);

                String text = categorySpinner.getSelectedItem().toString();
                int spinnerposition = 0;
                if(text == "Goal") { spinnerposition = 0;}
                else if(text == "Learning") {spinnerposition = 1;}
                else if(text == "Travel") {spinnerposition = 2;}
                else if(text == "WishList") {spinnerposition = 3;}
                else if(text == "Sharing") {spinnerposition = 4;}
                else if(text == "Etc") {spinnerposition = 5;}
                categorySpinner.setSelection(spinnerposition);

                categorySpinner.setEnabled(false);

                ibStar1.setEnabled(false);
                ibStar2.setEnabled(false);
                ibStar3.setEnabled(false);
                ibStar4.setEnabled(false);
                ibStar5.setEnabled(false);

                btnSwitcher.showNext();

                putDatatoRealm();

//                intent = new Intent(DetailActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);   // 액티비티 스택 삭제
//                startActivity(intent);

                break;
        }
    }

    private void putDatatoRealm() {
        realm.beginTransaction();
        bucketlistVO.get(0).title = etTitle.getText().toString();
        bucketlistVO.get(0).content = etContent.getText().toString();
        bucketlistVO.get(0).companion = etCompanion.getText().toString();
        bucketlistVO.get(0).place = etPlace.getText().toString();
        DatePicker datePickerStart = findViewById(R.id.DetailDatePickerStart);
        bucketlistVO.get(0).start_year = datePickerStart.getYear();
        bucketlistVO.get(0).start_month = datePickerStart.getMonth() + 1;
        bucketlistVO.get(0).start_day = datePickerStart.getDayOfMonth();
        DatePicker datePickerEnd = findViewById(R.id.DetailDatePickerEnd);
        bucketlistVO.get(0).end_year = datePickerEnd.getYear();
        bucketlistVO.get(0).end_month = datePickerEnd.getMonth() + 1;
        bucketlistVO.get(0).end_day = datePickerEnd.getDayOfMonth();
        bucketlistVO.get(0).category_index = categorySpinner.getSelectedItemPosition()+1;
        bucketlistVO.get(0).hashtag1 = etHashtag1.getText().toString();
        bucketlistVO.get(0).hashtag2 = etHashtag2.getText().toString();
        bucketlistVO.get(0).hashtag3 = etHashtag3.getText().toString();
        bucketlistVO.get(0).star_count = start_count;
        bucketlistVO.get(0).opinion = etOpinion.getText().toString();
        realm.commitTransaction();
    }

    private void setSpinner() {
        String[] category = {"Goal", "Learning", "Travel", "WishList", "Sharing", "Etc"};

        categorySpinner = findViewById(R.id.DetailCategorySpinner);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.spinner_item, category);
        categorySpinner.setAdapter(adapter);
    }

    // Realm 초기화
    private void init() {
        Realm.init(this);
        realm = Realm.getDefaultInstance(); // 쓰레드의 Realm 인스턴스 얻기

        // 데이터 세팅
        bucketlistVO = realm.where(BucketlistVO.class).equalTo("title", TITLE).findAll();
        tvTitle.setText(bucketlistVO.get(0).title+"");
        tvContent.setText(bucketlistVO.get(0).content+"");
        tvCompanion.setText(bucketlistVO.get(0).companion+"");
        // 장소
        tvPlace.setText(bucketlistVO.get(0).place+"");
        // 기한
        detailDate.setText(bucketlistVO.get(0).start_year+"."+bucketlistVO.get(0).start_month+"."+bucketlistVO.get(0).start_day+" - "+
                bucketlistVO.get(0).end_year+"."+bucketlistVO.get(0).end_month+"."+bucketlistVO.get(0).end_day);
        // 카테고리 선택
        categorySpinner.setSelection(bucketlistVO.get(0).category_index-1);
        // 해시태그
        tvHashtag1.setText(bucketlistVO.get(0).hashtag1);
        tvHashtag2.setText(bucketlistVO.get(0).hashtag2);
        tvHashtag3.setText(bucketlistVO.get(0).hashtag3);
        // 중요도(별)
        if(bucketlistVO.get(0).star_count > 0) ibStar1.setColorFilter(getResources().getColor(R.color.staryellow));
        if(bucketlistVO.get(0).star_count > 1 ) ibStar2.setColorFilter(getResources().getColor(R.color.staryellow));
        if(bucketlistVO.get(0).star_count > 2 ) ibStar3.setColorFilter(getResources().getColor(R.color.staryellow));
        if(bucketlistVO.get(0).star_count > 3 ) ibStar4.setColorFilter(getResources().getColor(R.color.staryellow));
        if(bucketlistVO.get(0).star_count > 4 ) ibStar5.setColorFilter(getResources().getColor(R.color.staryellow));
        // 소감
        tvOpinion.setText(bucketlistVO.get(0).opinion);

        switch (bucketlistVO.get(0).state) {
            // 초기:0, 진행중:1, 달성완료:2
            case 0:
                btnChangeState.setText("진행중으로 변경");
                break;
            case 1:
                btnChangeState.setText("달성 완료로 변경");
                break;
            case 2:
                btnChangeState.setText("달성 취소로 변경");
                break;
        }
    }
}