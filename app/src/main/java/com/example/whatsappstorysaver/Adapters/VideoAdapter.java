package com.example.whatsappstorysaver.Adapters;

import static com.example.whatsappstorysaver.Utils.MyConstants.ImagePosition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappstorysaver.Fragments.VideoFragments;
import com.example.whatsappstorysaver.Models.StatusModel;
import com.example.whatsappstorysaver.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final ArrayList<StatusModel> list;
    Context context;
    VideoFragments videoFragments;

    public VideoAdapter(Context context, ArrayList<StatusModel> list, VideoFragments videoFragments) {
        this.list = list;
        this.context = context;
        this.videoFragments = videoFragments;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        StatusModel statusModel = list.get(position);
        holder.ivThumbnailImgView.setImageBitmap(statusModel.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnailImgView;
        @BindView(R.id.ibSaveToGallery)
        ImageButton imageButtonDownload;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ivThumbnailImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImagePosition = getAdapterPosition();
                    StatusModel statusModel = list.get(getAdapterPosition());
                    if (statusModel != null) {
                        videoFragments.showVideoStatus(statusModel);
                    }
                }
            });
        }
    }
}
