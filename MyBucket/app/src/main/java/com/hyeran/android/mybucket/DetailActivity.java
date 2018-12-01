package com.hyeran.android.mybucket;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Vector;

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

    ImageButton addImage;
    int RESULT_LOAD_IMAGE = 1;
    int fileIndex = 0;
    String filename = "";
    String EXTERNAL_STORAGE_PATH = "";
    ImageView addedImage;
    String unique;

    ImageButton addVideo;
    int REQUEST_TAKE_GALLERY_VIDEO = 2;
    VideoView addedvideo;

    Bitmap oldbitmap;

    int countforvideo = 0;
    int countforvideo2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("자세히 보기");
        TITLE = getIntent().getStringExtra("TITLE");

        String state = Environment.getExternalStorageState();

        if(!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "외장 메모리가 마운트 되지 않았습니다.", Toast.LENGTH_LONG).show();
        } else {
            EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

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

        final CircleImageView circleimageView = findViewById(R.id.DetailCategoryImageView);
        final ImageView imageView = (ImageView) findViewById(R.id.DetailCategoryImageViewImage);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(categorySpinner.getSelectedItemPosition() == 0) {
                    circleimageView.setColorFilter(getResources().getColor(R.color.yellow));
                    imageView.setImageResource(R.drawable.goal);
                    imageView.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
                else if(categorySpinner.getSelectedItemPosition() == 1) {
                    circleimageView.setColorFilter(getResources().getColor(R.color.orange));
                    imageView.setImageResource(R.drawable.learning);
                    imageView.setBackgroundColor(getResources().getColor(R.color.orange));
                }
                else if(categorySpinner.getSelectedItemPosition() == 2) {
                    circleimageView.setColorFilter(getResources().getColor(R.color.green));
                    imageView.setImageResource(R.drawable.travel);
                    imageView.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else if(categorySpinner.getSelectedItemPosition() == 3) {
                    circleimageView.setColorFilter(getResources().getColor(R.color.blue));
                    imageView.setImageResource(R.drawable.wishlist);
                    imageView.setBackgroundColor(getResources().getColor(R.color.blue));
                }
                else if(categorySpinner.getSelectedItemPosition() == 4) {
                    circleimageView.setColorFilter(getResources().getColor(R.color.pink));
                    imageView.setImageResource(R.drawable.sharing);
                    imageView.setBackgroundColor(getResources().getColor(R.color.pink));
                }
                else {
                    circleimageView.setColorFilter(getResources().getColor(R.color.purple));
                    imageView.setImageResource(R.drawable.etc);
                    imageView.setBackgroundColor(getResources().getColor(R.color.purple));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //SharedPreferences 사용
        unique = tvTitle.getText().toString();
        SharedPreferences pref = getApplicationContext().getSharedPreferences(unique, Context.MODE_PRIVATE);

        //각 버킷리스트에 맞는 사진을 보여줌
        int getFileIndex = pref.getInt("fileIndex", 0);
        String uniquestring = pref.getString("unique", null);
        addedImage = (ImageView)findViewById(R.id.added_image);

        //boolean hasDrawable = (addedImage.getDrawable() != null);

        //if(hasDrawable) {

        if (getFileIndex == 0) {
            addedImage.setVisibility(View.GONE);
        } else {
            addedImage.setVisibility(View.VISIBLE);

            String myuri = EXTERNAL_STORAGE_PATH + "/" + uniquestring + getFileIndex + ".jpg";
            Uri uri = Uri.parse(myuri);
            addedImage.setImageURI(uri);
        }
        //}

        //각 버킷리스트에 맞는 비디오 보여줌
        addedvideo = (VideoView) findViewById(R.id.added_video);
        String videouristring = pref.getString("videouristring", null);
        if(videouristring == null) {
            addedvideo.setVisibility(View.GONE);
        } else {
            addedvideo.setVisibility(View.VISIBLE);
            MediaController mediaController = new MediaController(this);
            mediaController.setVisibility(View.GONE);
            mediaController.setAnchorView(addedvideo);
            addedvideo.setMediaController(mediaController);
            addedvideo.setVideoPath(videouristring);
            addedvideo.seekTo(1);
           addedvideo.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    countforvideo++;
                    if(countforvideo % 2 == 1) {
                        addedvideo.start();
                    } else {
                        addedvideo.pause();
                    }
                    return false;
                }
            });
        }

        //사진 추가
        addImage = findViewById(R.id.btn_image_detail);
        addImage.setOnClickListener(this);

        //동영상 추가
        addVideo = findViewById(R.id.btn_video_detail);
        addVideo.setOnClickListener(this);

        checkDangerousPermission();
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

                SharedPreferences pref = getApplicationContext().getSharedPreferences(unique, Context.MODE_PRIVATE);

                //각 버킷리스트에 해당하는 사진 지우기
                int getFileIndex = pref.getInt("fileIndex", 0);
                String uniquestring = pref.getString("unique", null);
                if(getFileIndex != 0) {

                    String myuri = EXTERNAL_STORAGE_PATH+"/"+uniquestring+getFileIndex+".jpg";
                    Uri uri = Uri.parse(myuri);
                    File file = new File(uri.getPath());
                    if(file.exists()) file.delete();
                }

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
                addedImage.setVisibility(View.VISIBLE);
                addedvideo.setVisibility(View.VISIBLE);

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

                ImageView addedImage2 = (ImageView) findViewById(R.id.added_image);
                boolean hasDrawable2 = (addedImage2.getDrawable() != null);
                if(hasDrawable2) {
                    oldbitmap = ((BitmapDrawable) addedImage.getDrawable()).getBitmap();
                } else {
                    oldbitmap = null;
                }


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

                ImageView addedImage = (ImageView) findViewById(R.id.added_image);
                boolean hasDrawable = (addedImage.getDrawable() != null);

                if(hasDrawable) {

                    Bitmap bm = ((BitmapDrawable)addedImage.getDrawable()).getBitmap();

                    if(bm != oldbitmap) {
                        filename = createFilename();
                        FileOutputStream fOut;
                        String strDirectory = EXTERNAL_STORAGE_PATH;

                        File f = new File(strDirectory, filename);

                        try {
                            fOut = new FileOutputStream(f);
                            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                            fOut.flush();
                            fOut.close();
                            MediaStore.Images.Media.insertImage(getContentResolver(), f.getAbsolutePath(), f.getName(), f.getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String unique = tvTitle.getText().toString();
                        SharedPreferences pref2 = getApplicationContext().getSharedPreferences(unique, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref2.edit();

                        editor.putInt("fileIndex", fileIndex);
                        editor.putString("unique", tvTitle.getText().toString());
                        editor.commit();
                    }
                } else {
                    addedImage.setVisibility(View.GONE);
                }

                SharedPreferences pref3 = getApplicationContext().getSharedPreferences(unique, Context.MODE_PRIVATE);
                String videouristring = pref3.getString("videouristring", null);
                if(videouristring == null) {
                    addedvideo.setVisibility(View.GONE);
                } else {
                    addedvideo.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.btn_image_detail:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
                break;

            case R.id.btn_video_detail:
                Intent videoPickerIntent = new Intent(Intent.ACTION_PICK);
                videoPickerIntent.setType("video/*");
                startActivityForResult(videoPickerIntent, REQUEST_TAKE_GALLERY_VIDEO );
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView addedImage = (ImageView) findViewById(R.id.added_image);
        addedvideo = (VideoView) findViewById(R.id.added_video);

        if (resultCode == RESULT_OK) {
            if(requestCode == RESULT_LOAD_IMAGE) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    addedImage.setImageBitmap(selectedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                }

            } else if(requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                final Uri videoUri = data.getData();

                String selectedVideoPath = getPath(data.getData());
                if(selectedVideoPath != null) {
                    MediaController mediaController = new MediaController(this);
                    mediaController.setVisibility(View.GONE);
                    mediaController.setAnchorView(addedvideo);
                    addedvideo.setMediaController(mediaController);
                    addedvideo.setVideoPath(videoUri.toString());
                    addedvideo.seekTo(1);

                      addedvideo.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                countforvideo2++;
                                if(countforvideo2 % 2 == 1) {
                                    addedvideo.start();
                                } else {
                                    addedvideo.pause();
                                }
                                return false;
                            }
                        });

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(unique, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("videouristring", videoUri.toString());
                    editor.commit();

                }
            }
        }else {
            Toast.makeText(this, "사진을 선택하지 않았습니다.",Toast.LENGTH_LONG).show();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;

    }

    private String createFilename() {
        fileIndex++;
        String newFilename = "";
        String unique = tvTitle.getText().toString();

        newFilename = (unique+fileIndex+".jpg");
        return newFilename;
    }

    private void checkDangerousPermission(){
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1) {
            for(int i = 0; i < permissions.length; i++) {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + "권한이 승인됨", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + "권한이 승인되지 않음", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}