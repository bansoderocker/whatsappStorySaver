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

import com.example.whatsappstorysaver.Fragments.ImageFragments;
import com.example.whatsappstorysaver.Fragments.SavedFragment;
import com.example.whatsappstorysaver.Models.StatusModel;
import com.example.whatsappstorysaver.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ImageViewHolder> {

    private final ArrayList<StatusModel> list;
    Context context;
    SavedFragment imageFragments;

    public SavedAdapter(Context context, ArrayList<StatusModel> list, SavedFragment imageFragments) {
        this.list = list;
        this.context = context;
        this.imageFragments = imageFragments;
    }

    @NonNull
    @Override
    public SavedAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.ImageViewHolder holder, int position) {
        StatusModel statusModel = list.get(position);
        holder.ivThumbnailImgView.setImageBitmap(statusModel.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnailImgView;
        @BindView(R.id.ibSaveToGallery)
        ImageButton imageButtonDownload;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imageButtonDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatusModel statusModel = list.get(getAdapterPosition());
                    if (statusModel != null) {
                        imageFragments.downloadStatus(statusModel);
                    }
                }
            });
            ivThumbnailImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  Log.d("adapter Position"," "+getAdapterPosition());
                    Log.d("adapter Size"," "+list.size());*/
                    ImagePosition = getAdapterPosition();
                    StatusModel statusModel = list.get(getAdapterPosition());
                    if (statusModel != null) {
                        imageFragments.showStatus(statusModel);
                    }
                }
            });


        }
    }
}
