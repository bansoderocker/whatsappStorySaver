package com.example.whatsappstorysaver.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsappstorysaver.Fragments.ImageFragments;
import com.example.whatsappstorysaver.Fragments.SavedFragment;
import com.example.whatsappstorysaver.Fragments.VideoFragments;

public class PagerAdapter extends FragmentPagerAdapter {

    private final ImageFragments imageFragments;
    private final VideoFragments videoFragments;
    private final SavedFragment savedFragment;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        imageFragments = new ImageFragments();
        videoFragments = new VideoFragments();
        savedFragment = new SavedFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return imageFragments;
        }
        if (i == 1) {
            return videoFragments;
        } else {
            return savedFragment;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int i) {
        if (i == 0) {
            return "Images";
        } else if (i == 1) {
            return "Videos";
        } else {
            return "Saved";

        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
