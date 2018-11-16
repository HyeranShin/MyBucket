package com.hyeran.android.mybucket;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BucketlistVO extends RealmObject{
    @PrimaryKey
    public String title;
    public String content;
    public String companion;
    public String place;
    public int start_year, start_month, start_day;
    public int end_year, end_month, end_day;
    public int category_index;
    // All:0, Goal:1, Learning:2, Travel:3, Wishlist:4, Sharing:5, Etc:6
    public String hashtag1, hashtag2, hashtag3;
    public int star_count;  // 중요도
    public int state;   // 도장
    // 초기:0, 진행중:1, 달성완료:2
}