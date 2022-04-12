package info.hccis.tennislessons.bo

import androidx.lifecycle.ViewModel
import java.util.ArrayList

/**
 * View Model for Tennis Lessons
 *
 * @author mariannahollanda
 * @since 20220119
 * @modified 20220328
 * @author mariannahollanda
 * Modified the program language from Java to Kotlin.
 */
class TennisLessonsViewModel : ViewModel() {
    var tennisLessons: ArrayList<Any?> = ArrayList<Any?>()
}