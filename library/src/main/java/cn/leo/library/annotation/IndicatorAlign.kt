package cn.leo.library.annotation

import androidx.annotation.IntDef
import cn.leo.library.annotation.Align.BOTTOM
import cn.leo.library.annotation.Align.LEFT
import cn.leo.library.annotation.Align.RIGHT
import cn.leo.library.annotation.Align.TOP

/**
 * @author : ling luo
 * @date : 2019-12-26
 */

@IntDef(
    value = [LEFT, RIGHT, TOP, BOTTOM],
    flag = true
)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class IndicatorAlign