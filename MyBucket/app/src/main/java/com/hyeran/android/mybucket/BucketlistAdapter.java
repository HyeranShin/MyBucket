package com.hyeran.android.mybucket;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class BucketlistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class BucketlistViewHolder extends RecyclerView.ViewHolder {
        Context context;

        LinearLayout whole;
        TextView num, title;
        ImageView btn_detail;
        RelativeLayout moreinfo;

        public BucketlistViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            whole = itemView.findViewById(R.id.whole_item_bucketlist);
            num = itemView.findViewById(R.id.tv_num_item_bucketlist);
            title = itemView.findViewById(R.id.tv_title_item_bucketlist);
            btn_detail = itemView.findViewById(R.id.btn_detail_bucketlist);
            moreinfo = itemView.findViewById(R.id.relative_moreinfo_item_bucketlist);

            moreinfo.setVisibility(View.GONE);

            btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });

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

    private ArrayList<BucketlistData> bucketlistDataArrayList;
    BucketlistAdapter(ArrayList<BucketlistData> bucketlistDataArrayList) {
        this.bucketlistDataArrayList = bucketlistDataArrayList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bucketlist, parent, false);
        return new BucketlistViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final BucketlistViewHolder bucketlistViewHolder = (BucketlistViewHolder) holder;

        bucketlistViewHolder.num.setText(bucketlistDataArrayList.get(position).count_num+"");
        bucketlistViewHolder.title.setText(bucketlistDataArrayList.get(position).title);
    }

    @Override
    public int getItemCount() {
        return bucketlistDataArrayList.size();
    }
}
