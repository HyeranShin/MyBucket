package com.hyeran.android.mybucket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BucketlistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static private ArrayList<BucketlistVO> bucketlistItems;

    static Context context;
    static ImageView btn_detail;

    public static class BucketlistViewHolder extends RecyclerView.ViewHolder {

        // 데이터 세팅할 뷰
        TextView num, title;
        CircleImageView category_background;
        ImageView category_icon, stamp;
        TextView star1, star2, star3, star4, star5;
        TextView year, month, day;
        TextView hashtag1, hashtag2, hashtag3;

        public BucketlistViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            // 데이터 세팅할 뷰
            num = itemView.findViewById(R.id.tv_num_item_bucketlist);
            // 카테고리
            category_background = itemView.findViewById(R.id.background_category_item_bucketlist);
            category_icon = itemView.findViewById(R.id.iv_category_item_bucketlist);
            // 제목
            title = itemView.findViewById(R.id.tv_title_item_bucketlist);
            // 도장
            stamp = itemView.findViewById(R.id.iv_stamp_item_bucketlist);
            // 중요도(별)
            star1 = itemView.findViewById(R.id.tv_star1_item_bucketlist);
            star2 = itemView.findViewById(R.id.tv_star2_item_bucketlist);
            star3 = itemView.findViewById(R.id.tv_star3_item_bucketlist);
            star4 = itemView.findViewById(R.id.tv_star4_item_bucketlist);
            star5 = itemView.findViewById(R.id.tv_star5_item_bucketlist);
            // 기한
            year = itemView.findViewById(R.id.tv_year_item_bucketlist);
            month = itemView.findViewById(R.id.tv_month_item_bucketlist);
            day = itemView.findViewById(R.id.tv_day_item_bucketlist);
            // 해시태그
            hashtag1 = itemView.findViewById(R.id.tv_hashtag1_item_bucketlist);
            hashtag2 = itemView.findViewById(R.id.tv_hashtag2_item_bucketlist);
            hashtag3 = itemView.findViewById(R.id.tv_hashtag3_item_bucketlist);

            // 자세히 보기 버튼
            btn_detail = itemView.findViewById(R.id.btn_detail_bucketlist);

            // 아이템 레이아웃 클릭 시 추가 정보 Visibility 값 변경
            LinearLayout whole = itemView.findViewById(R.id.whole_item_bucketlist);
            final RelativeLayout moreinfo = itemView.findViewById(R.id.relative_moreinfo_item_bucketlist);
            moreinfo.setVisibility(View.GONE);
            whole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(moreinfo.getVisibility() == View.GONE) {
                        moreinfo.setVisibility(View.VISIBLE);
                    } else {
                        moreinfo.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    BucketlistAdapter(ArrayList<BucketlistVO> bucketlistItems) {
        this.bucketlistItems = bucketlistItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bucketlist, parent, false);
        return new BucketlistViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final BucketlistViewHolder bucketlistViewHolder = (BucketlistViewHolder) holder;
//        this.position = position;

        // 데이터 세팅
        bucketlistViewHolder.num.setText(position+1+"");
        // 카테고리
        switch (bucketlistItems.get(position).category_index) {
            // Goal:1, Learning:2, Travel:3, Wishlist:4, Sharing:5, Etc:6
            case 1:
                bucketlistViewHolder.category_background.setColorFilter(Color.parseColor("#fff3b7"));
                bucketlistViewHolder.category_icon.setImageResource(R.drawable.goal);
                break;
            case 2:
                bucketlistViewHolder.category_background.setColorFilter(Color.parseColor("#fedfaa"));
                bucketlistViewHolder.category_icon.setImageResource(R.drawable.learning);
                break;
            case 3:
                bucketlistViewHolder.category_background.setColorFilter(Color.parseColor("#dcf1e6"));
                bucketlistViewHolder.category_icon.setImageResource(R.drawable.travel);
                break;
            case 4:
                bucketlistViewHolder.category_background.setColorFilter(Color.parseColor("#b3e5fc"));
                bucketlistViewHolder.category_icon.setImageResource(R.drawable.wishlist);
                break;
            case 5:
                bucketlistViewHolder.category_background.setColorFilter(Color.parseColor("#ffd5cc"));
                bucketlistViewHolder.category_icon.setImageResource(R.drawable.sharing);
                break;
            case 6:
                bucketlistViewHolder.category_background.setColorFilter(Color.parseColor("#e1bee7"));
                bucketlistViewHolder.category_icon.setImageResource(R.drawable.etc);
                break;
        }
        // 제목
        bucketlistViewHolder.title.setText(bucketlistItems.get(position).title);
        // 도장
        Log.d("STATE", String.format("%d", bucketlistItems.get(position).state));
        switch (bucketlistItems.get(position).state) {
            // 처음:0, 진행중:1, 성공:2
            case 0:
                bucketlistViewHolder.stamp.setImageResource(0);
                break;
            case 1:
                bucketlistViewHolder.stamp.setImageResource(R.drawable.stamp_run);
                bucketlistViewHolder.stamp.setColorFilter(Color.parseColor("#3669CF"));
                break;
            case 2:
                bucketlistViewHolder.stamp.setImageResource(R.drawable.stamp_medal);
                bucketlistViewHolder.stamp.setColorFilter(Color.parseColor("#FFCD12"));
                break;
        }
        // 추가 정보
        // 중요도(별)
        if(bucketlistItems.get(position).star_count > 0 ) bucketlistViewHolder.star1.setTextColor(Color.parseColor("#FFBB00"));
        if(bucketlistItems.get(position).star_count > 1 ) bucketlistViewHolder.star2.setTextColor(Color.parseColor("#FFBB00"));
        if(bucketlistItems.get(position).star_count > 2 ) bucketlistViewHolder.star3.setTextColor(Color.parseColor("#FFBB00"));
        if(bucketlistItems.get(position).star_count > 3 ) bucketlistViewHolder.star4.setTextColor(Color.parseColor("#FFBB00"));
        if(bucketlistItems.get(position).star_count > 4 ) bucketlistViewHolder.star5.setTextColor(Color.parseColor("#FFBB00"));
        // 기한
        bucketlistViewHolder.year.setText(bucketlistItems.get(position).end_year+"");
        bucketlistViewHolder.month.setText(bucketlistItems.get(position).end_month+"");
        bucketlistViewHolder.day.setText(bucketlistItems.get(position).end_day+"");
        // 해시태그
        bucketlistViewHolder.hashtag1.setText(bucketlistItems.get(position).hashtag1);
        bucketlistViewHolder.hashtag2.setText(bucketlistItems.get(position).hashtag2);
        bucketlistViewHolder.hashtag3.setText(bucketlistItems.get(position).hashtag3);

        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("TITLE", bucketlistItems.get(position).title);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bucketlistItems.size();
    }
}
