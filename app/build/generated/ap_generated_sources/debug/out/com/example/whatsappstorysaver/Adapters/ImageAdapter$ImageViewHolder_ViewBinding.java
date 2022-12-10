// Generated code from Butter Knife. Do not modify!
package com.example.whatsappstorysaver.Adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.whatsappstorysaver.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageAdapter$ImageViewHolder_ViewBinding implements Unbinder {
  private ImageAdapter.ImageViewHolder target;

  @UiThread
  public ImageAdapter$ImageViewHolder_ViewBinding(ImageAdapter.ImageViewHolder target,
      View source) {
    this.target = target;

    target.ivThumbnailImgView = Utils.findRequiredViewAsType(source, R.id.ivThumbnail, "field 'ivThumbnailImgView'", ImageView.class);
    target.imageButtonDownload = Utils.findRequiredViewAsType(source, R.id.ibSaveToGallery, "field 'imageButtonDownload'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImageAdapter.ImageViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivThumbnailImgView = null;
    target.imageButtonDownload = null;
  }
}
