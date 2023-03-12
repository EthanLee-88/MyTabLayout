package com.ethan.launcherkt.launch

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class EasyLauncher {

    companion object {

        fun with(fragment: Fragment): EasyBuilder {
            return EasyBuilder.with(fragment)
        }

        fun with(fragmentActivity: FragmentActivity): EasyBuilder {
            return EasyBuilder.with(fragmentActivity)
        }
    }
}