// Generated code from Butter Knife. Do not modify!
package com.example.whatsappstorysaver.Fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.whatsappstorysaver.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SavedFragment_ViewBinding implements Unbinder {
  private SavedFragment target;

  @UiThread
  public SavedFragment_ViewBinding(SavedFragment target, View source) {
    this.target = target;

    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerViewImage, "field 'recyclerView'", RecyclerView.class);
    target.showImageView = Utils.findRequiredViewAsType(source, R.id.showImageView, "field 'showImageView'", ImageView.class);
    target.showVideoView = Utils.findRequiredViewAsType(source, R.id.showVideoView, "field 'showVideoView'", VideoView.class);
    target.linearLayoutShowImage = Utils.findRequiredViewAsType(source, R.id.llShowImageView, "field 'linearLayoutShowImage'", RelativeLayout.class);
    target.BackToRecyclerView = Utils.findRequiredViewAsType(source, R.id.BackToRecyclerView, "field 'BackToRecyclerView'", ImageView.class);
    target.RightSlide = Utils.findRequiredViewAsType(source, R.id.RightSlide, "field 'RightSlide'", ImageView.class);
    target.LeftSlide = Utils.findRequiredViewAsType(source, R.id.LeftSlide, "field 'LeftSlide'", ImageView.class);
    target.shareButton = Utils.findRequiredViewAsType(source, R.id.imageShareFAB, "field 'shareButton'", FloatingActionButton.class);
    target.saveButton = Utils.findRequiredViewAsType(source, R.id.imageSaveFAB, "field 'saveButton'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SavedFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
    target.recyclerView = null;
    target.showImageView = null;
    target.showVideoView = null;
    target.linearLayoutShowImage = null;
    target.BackToRecyclerView = null;
    target.RightSlide = null;
    target.LeftSlide = null;
    target.shareButton = null;
    target.saveButton = null;
  }
}
