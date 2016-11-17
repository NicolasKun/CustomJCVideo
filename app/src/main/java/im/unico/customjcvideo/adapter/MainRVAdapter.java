package im.unico.customjcvideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import im.unico.customjcvideo.R;
import im.unico.customjcvideo.bean.MainBean;
import im.unico.customjcvideo.ui.VideoDetail;

/**
 * Created by LeeQ on 2016-11-16.
 */

public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.RVAdapterHolder> {

    private List<MainBean> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public MainRVAdapter(List<MainBean> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public class RVAdapterHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivCover;
        public RVAdapterHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.item_mr_tv_title);
            ivCover = (ImageView) itemView.findViewById(R.id.item_mr_iv_cover);
        }
    }


    @Override
    public RVAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View convertView = View.inflate(context, R.layout.item_main_recycler, null);
        View convertView = layoutInflater.inflate(R.layout.item_main_recycler, parent, false);
        return new RVAdapterHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RVAdapterHolder holder, int position) {
        final String vPath = data.get(position).getVPath();
        final String vTitle = data.get(position).getVTitle();
        holder.tvTitle.setText(vTitle);

        String vCover = data.get(position).getVCover();
        if (vCover != null && !vCover.equals("")) {
            Glide.with(context).load(vCover).into(holder.ivCover);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, VideoDetail.class)
                        .putExtra("vPath", vPath)
                        .putExtra("vTitle", vTitle)
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

