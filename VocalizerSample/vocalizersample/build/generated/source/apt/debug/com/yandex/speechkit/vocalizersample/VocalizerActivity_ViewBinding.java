// Generated code from Butter Knife. Do not modify!
package com.yandex.speechkit.vocalizersample;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VocalizerActivity_ViewBinding implements Unbinder {
  private VocalizerActivity target;

  private View view2131427437;

  private View view2131427438;

  @UiThread
  public VocalizerActivity_ViewBinding(VocalizerActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public VocalizerActivity_ViewBinding(final VocalizerActivity target, View source) {
    this.target = target;

    View view;
    target.synthesisTextView = Utils.findRequiredViewAsType(source, R.id.synthesis_text_view, "field 'synthesisTextView'", EditText.class);
    target.logTextView = Utils.findRequiredViewAsType(source, R.id.log_text_view, "field 'logTextView'", LogTextView.class);
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
    VocalizerActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.synthesisTextView = null;
    target.logTextView = null;

    view2131427437.setOnClickListener(null);
    view2131427437 = null;
    view2131427438.setOnClickListener(null);
    view2131427438 = null;
  }
}
