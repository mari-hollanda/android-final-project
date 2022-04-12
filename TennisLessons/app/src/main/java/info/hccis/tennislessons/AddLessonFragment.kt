package info.hccis.tennislessons

import info.hccis.tennislessons.bo.TennisLessonsBO
import info.hccis.tennislessons.bo.TennisLessonsViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import info.hccis.tennislessons.databinding.FragmentAddLessonBinding
import java.lang.Exception
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.Throws

/**
 * Add Lesson Fragment
 *
 * @author CIS2250
 * @since 20220118
 * @modified by mariannahollanda
 * @since 20220119
 * @modified 20220328
 * @author mariannahollanda
 * Modified the program language from Java to Kotlin.
 */
class AddLessonFragment : Fragment() {
    private var binding: FragmentAddLessonBinding? = null
    var tennisLessonsBO: TennisLessonsBO? = null
    var tennisLessonsViewModel: TennisLessonsViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("AddLessonFragment MH", "onCreateView triggered")
        binding = FragmentAddLessonBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("AddLessonFragment MH", "onViewCreated triggered")
        tennisLessonsViewModel = ViewModelProvider(requireActivity()).get(TennisLessonsViewModel::class.java)
        binding!!.buttonSubmit.setOnClickListener {
            Log.d("AddLessonFragment MH", "Submit was triggered")
            try {
                calculate()
                val bundle = Bundle()
                bundle.putSerializable(KEY, tennisLessonsBO)
                NavHostFragment.findNavController(this@AddLessonFragment)
                        .navigate(R.id.action_AddLessonFragment_to_ViewLessonFragment, bundle)
            } catch (e: Exception) {
                Log.d("AddLessonFragment MH", "Error calculating: " + e.message)
            }
        }
    }

    /**
     * Calculate the Lesson cost.
     *
     * @throws Exception Throw exception if number of tickets entered caused an issue.
     * @author CIS2250
     * @since 20220118
     * @modified by mariannahollanda
     * @since 20220119
     * @modified 20220328
     * @author mariannahollanda
     * Modified the program language from Java to Kotlin.
     */
    @Throws(Exception::class)
    fun calculate() {
        Log.d("MH-MainActivity", "Lesson group size: " + binding!!.editTextGroupNumber.text.toString())
        Log.d("MH-MainActivity", "Member: " + binding!!.editTextMember.text.toString())
        Log.d("MH-MainActivity", "Submit button was clicked.")
        val isMember = binding!!.editTextMember.text.toString()
        var groupSize: Int
        var totalHours: Int
        try {
            groupSize = binding!!.editTextGroupNumber.text.toString().toInt()
            totalHours = binding!!.editTextHours.text.toString().toInt()
        } catch (e: Exception) {
            groupSize = 0
            totalHours = 0
        }
        tennisLessonsBO = TennisLessonsBO(groupSize, isMember, totalHours)
        try {
            val cost = tennisLessonsBO!!.calculateCost()
            tennisLessonsViewModel!!.tennisLessons.add(tennisLessonsBO)

            //Now that I have the cost, want to set the value on the textview.
            val locale = Locale("en", "CA")
            val decimalFormat = DecimalFormat.getCurrencyInstance(locale) as DecimalFormat
            val dfs = DecimalFormatSymbols.getInstance(locale)
            decimalFormat.decimalFormatSymbols = dfs
            val formattedCost = decimalFormat.format(cost)
        } catch (nfe: NumberFormatException) {
            binding!!.editTextGroupNumber.setText("")
            throw nfe
        } catch (e: Exception) {
            binding!!.editTextGroupNumber.setText("")
            throw e
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val KEY = "info.hccis.tennislessons.LESSON"
    }
}