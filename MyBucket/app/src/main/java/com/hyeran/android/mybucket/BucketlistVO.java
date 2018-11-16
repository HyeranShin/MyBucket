package com.hyeran.android.mybucket;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BucketlistVO extends RealmObject{

    @PrimaryKey
    public String title;
    // All:0, Goal:1, Learning:2, Travel:3, Wishlist:4, Sharing:5, Etc:6
    public int category_index;
    public int star_count;  // 중요도
    public int year, month, day;
    public String hashtag1, hashtag2, hashtag3;

}