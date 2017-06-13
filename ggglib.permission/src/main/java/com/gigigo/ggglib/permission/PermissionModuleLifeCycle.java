package com.gigigo.ggglib.permission;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by nubor on 13/06/2017.
 */

public class PermissionModuleLifeCycle implements Application.ActivityLifecycleCallbacks {

  private WeakReference<Activity> activity;
  private Stack<ActivityWrapper> activityStack = new Stack<>();

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

  }

  @Override public void onActivityStarted(Activity activity) {
    if (!activity.isFinishing()) {
      this.activityStack.push(new ActivityWrapper(activity, true, false));
    }
  }

  @Override public void onActivityResumed(Activity activity) {
    try {
      checkNotEmpty(activityStack);
      activityStack.peek().setIsPaused(false);
    } catch (Exception e) {
      System.out.println("Exception :" + e.getMessage());
    }
  }

  @Override public void onActivityPaused(Activity activity) {
    try {
      checkNotEmpty(activityStack);
      activityStack.peek().setIsPaused(true);
    } catch (Exception e) {
      System.out.println("Exception :" + e.getMessage());
    }
  }

  @Override public void onActivityStopped(Activity activity) {
    try {
      checkNotEmpty(activityStack);
      removeActivityFromStack(activity);
    } catch (Exception e) {
      System.out.println("Exception :" + e.getMessage());
    }
  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

  }

  @Override public void onActivityDestroyed(Activity activity) {

  }

  public boolean isActivityContextAvailable() {
    return (getCurrentActivity() != null);
  }

  public Activity getCurrentActivity() {

    for (ActivityWrapper activityLifecyleWrapper : activityStack) {
      if (!activityLifecyleWrapper.isPaused()) {
        return activityLifecyleWrapper.getActivity();
      }
    }

    for (ActivityWrapper activityLifecyleWrapper : activityStack) {
      if (!activityLifecyleWrapper.isStopped()) {
        return activityLifecyleWrapper.getActivity();
      }
    }

    return null;
  }

  private void removeActivityFromStack(Activity activity) {
    Iterator<ActivityWrapper> iter = activityStack.iterator();
    while (iter.hasNext()) {
      ActivityWrapper activityLifecyleWrapper = iter.next();
      if (activityLifecyleWrapper.getActivity().equals(activity)) {
        iter.remove();
      }
    }
  }

  static <T> Collection<T> checkNotEmpty(Collection<T> container) {
    if (checkNotNull(container).isEmpty()) {
      throw new IllegalArgumentException("Container cannot be null or empty");
    }
    return container;
  }

  static <T> T checkNotNull(T object) {
    return checkNotNull(object, "The object is null");
  }

  static <T> T checkNotNull(T object, String message) {
    if (object == null) {
      throw new NullPointerException(message);
    }
    return object;
  }
}
