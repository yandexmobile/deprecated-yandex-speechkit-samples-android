// Generated code from Butter Knife. Do not modify!
package com.yandex.speechkit.recognizersample;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecognizerActivity_ViewBinding implements Unbinder {
  private RecognizerActivity target;

  private View view2131427437;

  private View view2131427438;

  @UiThread
  public RecognizerActivity_ViewBinding(RecognizerActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RecognizerActivity_ViewBinding(final RecognizerActivity target, View source) {
    this.target = target;

    View view;
    target.logTextView = Utils.findRequiredViewAsType(source, R.id.log_text_view, "field 'logTextView'", LogTextView.class);
    target.powerProgressBar = Utils.findRequiredViewAsType(source, R.id.voice_power_progress_bar, "field 'powerProgressBar'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.start_button, "method 'onStartButtonTap'");
    view2131427437 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onStartButtonTap(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.stop_button, "method 'onStopButtonTap'");
    view2131427438 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onStopButtonTap(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RecognizerActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.logTextView = null;
    target.powerProgressBar = null;

    view2131427437.setOnClickListener(null);
    view2131427437 = null;
    view2131427438.setOnClickListener(null);
    view2131427438 = null;
  }
}
