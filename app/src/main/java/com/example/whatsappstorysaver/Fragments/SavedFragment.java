package com.example.whatsappstorysaver.Fragments;

import static android.view.View.VISIBLE;
import static com.example.whatsappstorysaver.Utils.MyConstants.APP_DIR;
import static com.example.whatsappstorysaver.Utils.MyConstants.ImagePosition;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.whatsappstorysaver.Adapters.ImageAdapter;
import com.example.whatsappstorysaver.Adapters.SavedAdapter;
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

public class SavedFragment extends Fragment {

    static File[] statusFiles;
    static Handler handler = new Handler();
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerViewImage)
    RecyclerView recyclerView;
    @BindView(R.id.showImageView)
    ImageView showImageView;
    @BindView(R.id.showVideoView)
    VideoView showVideoView;
    @BindView(R.id.llShowImageView)
    RelativeLayout linearLayoutShowImage;
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
    ArrayList<StatusModel> saveModelArrayList;
    SavedAdapter imageAdapter;
    MediaController mediaController;


    public SavedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        ButterKnife.bind(this, view);

        showVideoView.stopPlayback();
        BackToRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutShowImage.setVisibility(View.GONE);
                recyclerView.setVisibility(VISIBLE);
            }
        });

        LeftSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ImagePosition - 1 != -1) {
                    try {
                        StatusModel statusModel = saveModelArrayList.get(--ImagePosition);
                        showStatus(statusModel);
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Please try After Some time", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        RightSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImagePosition + 1 < saveModelArrayList.size()) {
                    try {
                        StatusModel statusModel = saveModelArrayList.get(++ImagePosition);
                        showStatus(statusModel);
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Please try After Some time", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusModel statusModel = saveModelArrayList.get(ImagePosition);

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
                StatusModel statusModel = saveModelArrayList.get(ImagePosition);
                downloadStatus(statusModel);
            }
        });

        getSavedStatus();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        saveModelArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mediaController = new MediaController(getContext());
        mediaController.setAnchorView(showVideoView);
        linearLayoutShowImage.setVisibility(View.GONE);
        recyclerView.setVisibility(VISIBLE);

        //     getSavedStatus();
    }

    public void getSavedStatus() {
        File Path = new File(APP_DIR + "/Images");

        if (Path.exists()) {
            new Thread(new Runnable() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void run() {
                    statusFiles = Path.listFiles();
                    if (statusFiles != null && statusFiles.length > 0) {
                        Arrays.sort(statusFiles);
                        //  saveModelArrayList.clear();
                        for (final File statusFile : statusFiles) {
                            StatusModel statusModel = new StatusModel(statusFile, statusFile.getName(), statusFile.getAbsolutePath());
                            statusModel.setThumbnail(getThumbnail(statusModel));

                            saveModelArrayList.add(statusModel);

                        }
                        if (saveModelArrayList.size() > 0) {
                            handler.post(() -> {
                                progressBar.setVisibility(View.GONE);
                                imageAdapter = new SavedAdapter(getContext(), saveModelArrayList, SavedFragment.this);
                                recyclerView.setAdapter(imageAdapter);
                                imageAdapter.notifyDataSetChanged();
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
            return ThumbnailUtils.createVideoThumbnail(statusModel.getFile().getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);

        } else {

            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(statusModel.getFile().getAbsolutePath()),
                    MyConstants.THUMBSIZE,
                    MyConstants.THUMBSIZE);
        }
    }

    public void downloadStatus(StatusModel statusModel) {
        File file = new File(APP_DIR + "/Images");
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

    public void showStatus(StatusModel statusModel) {

        File file = new File(statusModel.getFile().toString());
        if (file.exists()) {

            if (statusModel.isVideo()) {
                Uri uri = Uri.parse(statusModel.getFile().toString());
                showImageView.setVisibility(View.GONE);
                showVideoView.setVisibility(VISIBLE);
                //Setting MediaController and URI, then starting the videoView
                showVideoView.setMediaController(mediaController);
                showVideoView.setVideoURI(uri);
                showVideoView.requestFocus();
                showVideoView.start();
            } else {
                showVideoView.setVisibility(View.GONE);
                showImageView.setVisibility(VISIBLE);

                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                showImageView.setImageBitmap(bitmap);

            }
            linearLayoutShowImage.setVisibility(VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

    }
}