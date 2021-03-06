/*
 * Copyright (C) 2015 The Android Open Source Project
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

package android.support.v4ox.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.widget.CompoundButton;

class CompoundButtonCompatLollipop {

    static void setButtonTintList(CompoundButton button, ColorStateList tint) {
        button.setButtonTintList(tint);
    }

    static ColorStateList getButtonTintList(CompoundButton button) {
        return button.getButtonTintList();
    }

    static void setButtonTintMode(CompoundButton button, PorterDuff.Mode tintMode) {
        button.setButtonTintMode(tintMode);
    }

    static PorterDuff.Mode getButtonTintMode(CompoundButton button) {
        return button.getButtonTintMode();
    }
}
