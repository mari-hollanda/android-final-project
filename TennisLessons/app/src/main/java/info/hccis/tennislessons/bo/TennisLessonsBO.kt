package info.hccis.tennislessons.bo

import java.io.Serializable
import java.util.*

/**
 * Business logic class for tennis lessons
 *
 * @author mariannahollanda
 * @since 20220119
 * @modified 20220328
 * @author mariannahollanda
 * Modified the program language from Java to Kotlin.
 */
class TennisLessonsBO(var groupNumber: Int, var isMemberYN: String, var hours: Int) : Serializable {
    var isMember = false
    var cost = 0.0

    /**
     * Get information from the user
     *
     * @author mariannahollanda
     * @since 20220119
     * @modified 20220328
     * @author mariannahollanda
     * Modified the program language from Java to Kotlin.
     */
    val information: Unit
        get() {
            println("Welcome to the CIS Tennis Program")
            val input = Scanner(System.`in`)
            println("How many are in the group? (1,2,3,4)")
            groupNumber = input.nextInt()
            input.nextLine()
            println("Are you a member? (Y/N)")
            isMemberYN = input.nextLine()
            isMember = isMemberYN.equals("Y", ignoreCase = true)
            println("How many hours do you want for your lesson?")
            hours = input.nextInt()
            input.nextLine()
        }

    /**
     * Calculate the cost
     *
     * @return
     * @author mariannahollanda
     * @since 20220119
     * @modified 20220328
     * @author mariannahollanda
     * Modified the program language from Java to Kotlin.
     */
    fun calculateCost(): Double {
        when (isMemberYN) {
            "Y", "y" -> if (groupNumber == 1) {
                cost = hours * PRIVATE_MEMBER
            } else if (groupNumber == 2) {
                cost = hours * TWO_GROUP_MEMBER
            } else if (groupNumber == 3) {
                cost = hours * THREE_GROUP_MEMBER
            } else if (groupNumber == 4) {
                cost = hours * FOUR_GROUP_MEMBER
            }
            "N", "n" -> if (groupNumber == 1) {
                cost = hours * PRIVATE_NON_MEMBER
            } else if (groupNumber == 2) {
                cost = hours * TWO_GROUP_NON_MEMBER
            } else if (groupNumber == 3) {
                cost = hours * THREE_GROUP_NON_MEMBER
            } else if (groupNumber == 4) {
                cost = hours * FOUR_GROUP_NON_MEMBER
            }
        }
        return cost
    }

    fun display() {
        println(toString())
    }

    override fun toString(): String {
        return "Lesson details - Lesson group size: " + groupNumber + " Member: " + isMemberYN + "/hour Cost: $" + calculateCost()
    }

    companion object {
        const val PRIVATE_MEMBER = 55.0
        const val TWO_GROUP_MEMBER = 30.0
        const val THREE_GROUP_MEMBER = 21.0
        const val FOUR_GROUP_MEMBER = 16.0
        const val PRIVATE_NON_MEMBER = 60.0
        const val TWO_GROUP_NON_MEMBER = 33.0
        const val THREE_GROUP_NON_MEMBER = 23.0
        const val FOUR_GROUP_NON_MEMBER = 18.0
    }
}