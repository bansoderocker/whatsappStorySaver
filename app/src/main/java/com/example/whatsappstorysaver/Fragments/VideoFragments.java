package com.example.whatsappstorysaver.Fragments;

import static android.view.View.VISIBLE;
import static com.example.whatsappstorysaver.Utils.MyConstants.ImagePosition;
import static com.example.whatsappstorysaver.Utils.MyConstants.VideoPosition;
import static com.example.whatsappstorysaver.Utils.MyConstants.STATUS_DIRECTORY;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappstorysaver.Adapters.VideoAdapter;
import com.example.whatsappstorysaver.Models.StatusModel;
import com.example.whatsappstorysaver.R;
import com.example.whatsappstorysaver.Utils.MyConstants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragments extends Fragment {

    @BindView(R.id.progressBarVideo)
    ProgressBar progressBar;
    @BindView(R.id.recyclerViewVideo)
    RecyclerView recyclerView;
    @BindView(R.id.showVideoView)
    VideoView showVideoView;
    @BindView(R.id.llShowVideoView)
    RelativeLayout linearLayoutShowVideo;

    @BindView(R.id.BackToRecyclerView)
    ImageView BackToRecyclerView;
    @BindView(R.id.RightSlide)
    ImageView RightSlide;
    @BindView(R.id.LeftSlide)
    ImageView LeftSlide;

    @BindView(R.id.imageShareFAB)
    FloatingActionButton shareButton;
    @BindView(R.id.imageSaveFAB)
    FloatingActionButton saveButton;

    ArrayList<StatusModel> videoModelArrayList;
    VideoAdapter videoAdapter;
    Handler handler = new Handler();
    MediaController mediaController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_video, container, false);
        ButterKnife.bind(this, view);

        linearLayoutShowVideo.setVisibility(View.GONE);
        recyclerView.setVisibility(VISIBLE);

        BackToRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideoView.stopPlayback();

                linearLayoutShowVideo.setVisibility(View.GONE);
                recyclerView.setVisibility(VISIBLE);
            }
        });

        LeftSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VideoPosition - 1 != -1) {
                    StatusModel statusModel = videoModelArrayList.get(--VideoPosition);
                    showVideoStatus(statusModel);
                }
            }
        });

        RightSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VideoPosition + 1 < videoModelArrayList.size()) {
                    Log.d("adapter Position -> ", " " + (++VideoPosition));
                    Log.d("adapter Size - > ", " " + videoModelArrayList.size());
                    StatusModel statusModel = videoModelArrayList.get(++VideoPosition);
                    showVideoStatus(statusModel);
                }
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusModel statusModel = videoModelArrayList.get(VideoPosition);

                Uri imageUri = Uri.parse(statusModel.getFile().toString());
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share images..."));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusModel statusModel = videoModelArrayList.get(VideoPosition);
                downloadVideoStatus(statusModel);

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        videoModelArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mediaController = new MediaController(getContext());
        mediaController.setAnchorView(showVideoView);

        linearLayoutShowVideo.setVisibility(View.GONE);
        recyclerView.setVisibility(VISIBLE);
        getStatusVideo();
    }

    private void getStatusVideo() {

        if (STATUS_DIRECTORY.exists()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File[] statusFiles = STATUS_DIRECTORY.listFiles();
                    if (statusFiles != null && statusFiles.length > 0) {
                        Arrays.sort(statusFiles);
                        for (final File statusFile : statusFiles) {
                            StatusModel statusModel = new StatusModel(statusFile, statusFile.getName(), statusFile.getAbsolutePath());
                            statusModel.setThumbnail(getThumbnail(statusModel));
                            if (statusModel.isVideo()) {
                                videoModelArrayList.add(statusModel);
                            }
                        }
                        if (videoModelArrayList.size() > 0) {

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    videoAdapter = new VideoAdapter(getContext(), videoModelArrayList, VideoFragments.this);
                                    recyclerView.setAdapter(videoAdapter);
                                    videoAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }
            }).start();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Directory Not Exist..", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private Bitmap getThumbnail(StatusModel statusModel) {
        if (statusModel.isVideo()) {
            return ThumbnailUtils.createVideoThumbnail(statusModel.getFile().getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);

        } else {

            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(statusModel.getFile().getAbsolutePath()), MyConstants.THUMBSIZE, MyConstants.THUMBSIZE);
        }
    }

    public void downloadVideoStatus(StatusModel statusModel) {
        File file = new File(MyConstants.APP_DIR + "/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        File destFile = new File(file + File.separator + statusModel.getTitle());
        if (destFile.exists()) {
            destFile.delete();
        }
        try {
            copyFile(statusModel.getFile(), destFile);

            Toast.makeText(getContext(), "Download Completed...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(destFile));
            getActivity().sendBroadcast(intent);
        } catch (IOException e) {
            // Toast.makeText(getContext(),e.printStackTrace(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private void copyFile(File file, File destFile) throws IOException {
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        source = new FileInputStream(file).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        destination.transferFrom(source, 0, source.size());

        source.close();
        destination.close();
    }


    public void showVideoStatus(StatusModel statusModel) {
        File file = new File(statusModel.getFile().toString());
        if (file.exists()) {

            Uri uri = Uri.parse(statusModel.getFile().toString());

            //Setting MediaController and URI, then starting the videoView
            showVideoView.setMediaController(mediaController);
            showVideoView.setVideoURI(uri);
            showVideoView.requestFocus();
            showVideoView.start();

            linearLayoutShowVideo.setVisibility(VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        showVideoView.stopPlayback();

    }

    @Override
    public void onResume() {
        super.onResume();
        showVideoView.stopPlayback();

    }
}
