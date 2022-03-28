package info.hccis.tennislessons.bo

import androidx.lifecycle.ViewModel
import info.hccis.tennislessons.bo.TennisLessonsBO
import java.util.ArrayList

/**
 * View Model for Tennis Lessons
 *
 * @author mariannahollanda
 * @since 20220119
 */
class TennisLessonsViewModel : ViewModel() {
    var tennisLessons: ArrayList<Any?> = ArrayList<Any?>()
}