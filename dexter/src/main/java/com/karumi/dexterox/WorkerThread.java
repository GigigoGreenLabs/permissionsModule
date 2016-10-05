/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.karumi.dexterox;

import android.os.Handler;
import android.os.Looper;

/**
 * A thread to execute passed runnable objects on a worker thread
 */
final class WorkerThread implements Thread {

  private final Handler handler;

  WorkerThread() {
    if (Looper.myLooper() == null) {
      Looper.prepare();
    }
    handler = new Handler();
  }

  @Override public void execute(final Runnable runnable) {
    handler.post(runnable);
  }

  @Override public void loop() {
    Looper.loop();
  }
}
