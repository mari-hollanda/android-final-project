package info.hccis.tennislessons

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import info.hccis.tennislessons.bo.TennisLessonsViewModel
import androidx.lifecycle.ViewModelProvider
import info.hccis.tennislessons.bo.TennisLessonsBO
import androidx.navigation.fragment.NavHostFragment
import info.hccis.tennislessons.databinding.FragmentViewLessonBinding

/**
 * View Lesson Fragment
 *
 * @author CIS2250
 * @since 20220118
 * @modified by mariannahollanda
 * @since 20220119
 * @modified 20220328
 * @author mariannahollanda
 * Modified the program language from Java to Kotlin.
 */
class ViewLessonFragment : Fragment() {
    private var binding: FragmentViewLessonBinding? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentViewLessonBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tennisLessonsViewModel = ViewModelProvider(requireActivity()).get(TennisLessonsViewModel::class.java)

        //Bundle is accessed to get the ticket order which is passed from the add order fragment.
        val bundle = arguments
        val tennisLessonsBO = bundle!!.getSerializable(AddLessonFragment.KEY) as TennisLessonsBO?
        Log.d("ViewLessonFragment MH", "Lesson passed in: " + tennisLessonsBO.toString())

        //Build the output to be displayed in the textview
        var output = ""
        var total = 0.0
        for (lesson in tennisLessonsViewModel.tennisLessons) {
            output += lesson.toString() + System.lineSeparator()
            total += tennisLessonsBO!!.calculateCost()
        }
        output += System.lineSeparator() + "Total: $" + total
        binding!!.textviewLessonDetails.text = output

        //Button sends the user back to the add fragment
        binding!!.buttonAddOrder.setOnClickListener {
            NavHostFragment.findNavController(this@ViewLessonFragment)
                    .navigate(R.id.action_ViewLessonFragment_to_AddLessonFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}