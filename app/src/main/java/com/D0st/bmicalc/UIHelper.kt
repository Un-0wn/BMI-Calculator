package com.D0st.bmicalc

import androidx.navigation.NavController
import androidx.navigation.NavDirections


    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction)
        }

    }
