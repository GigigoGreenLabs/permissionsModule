/*
 * Created by Gigigo Android Team
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
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

package com.gigigo.permissions.interfaces;

import android.app.Activity;
import com.karumi.dexterox.listener.multi.MultiplePermissionsListener;

public interface PermissionChecker {

  void askForPermission( UserPermissionRequestResponseListener userResponse,Permission permission);

  void askForPermissions(MultiplePermissionsListener permissionsListener,
      Permission... permissions);

  void continuePendingPermissionsRequestsIfPossible();

  boolean isGranted(Permission permission);
  boolean isAllGranted(Permission... permission);
}
