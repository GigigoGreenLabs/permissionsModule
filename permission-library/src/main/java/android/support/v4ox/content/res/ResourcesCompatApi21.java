/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.v4ox.content.res;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;

class ResourcesCompatApi21 {
    public static Drawable getDrawable(Resources res, int id, Theme theme)
            throws NotFoundException {
        return res.getDrawable(id, theme);
    }

    public static Drawable getDrawableForDensity(Resources res, int id, int density, Theme theme)
            throws NotFoundException {
        return res.getDrawableForDensity(id, density, theme);
    }
}
