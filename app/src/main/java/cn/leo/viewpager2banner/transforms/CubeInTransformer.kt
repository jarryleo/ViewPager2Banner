/*
 * Copyright 2014 Toxic Bakery
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.leo.viewpager2banner.transforms

import android.view.View


open class CubeInTransformer : ABaseTransformer() {

    public override val isPagingEnabled: Boolean
        get() = true

    override fun onTransform(page: View, position: Float) {
        // Rotate the fragment on the left or right edge
        page.pivotX = if (position > 0) 0f else page.width.toFloat()
        page.pivotY = 0f
        page.rotationY = -90f * position
    }

}
