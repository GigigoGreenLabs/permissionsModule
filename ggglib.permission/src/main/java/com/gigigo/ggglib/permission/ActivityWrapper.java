package com.gigigo.ggglib.permission;

import android.app.Activity;
import java.lang.ref.WeakReference;

/**
 * Created by nubor on 13/06/2017.
 */

class ActivityWrapper {
  private WeakReference<Activity> activity;
  private boolean isPaused;
  private boolean isStopped;

  public ActivityWrapper(Activity activity, boolean isPaused, boolean isStopped) {
    this.activity = new WeakReference<>(activity);
    this.isPaused = isPaused;
    this.isStopped = isStopped;
  }

  public Activity getActivity() {
    if (activity!=null){
      return activity.get();
    }else{
      return null;
    }
  }

  public boolean isPaused() {
    return isPaused;
  }

  public void setIsPaused(boolean isPaused) {
    this.isPaused = isPaused;
  }

  public boolean isStopped() {
    return isStopped;
  }

  public void setIsStopped(boolean isStopped) {
    this.isStopped = isStopped;
  }

  public void setActivity(Activity activity) {
    if (activity == null){
      this.activity = new WeakReference<>(activity);
    }
  }
}
