package com.ethan.launcherkt.launch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.ethan.launcherkt.LauncherKtMainActivity

class HolderFragment : Fragment() {

    private lateinit var mLaunchCallBack: LaunchCallBack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun launchActivity(callBack: LaunchCallBack) {
        this.mLaunchCallBack = callBack
        val intent = Intent(context, LauncherKtMainActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_FOR_LAUNCH)
    }

    companion object Instance {
        private val TAG = "HolderFragment"
        private val REQUEST_CODE_FOR_LAUNCH = 10088

        fun getHolderFragment(fragment: Fragment): HolderFragment {
            return getHolderFragment(fragment.childFragmentManager)
        }

        fun getHolderFragment(activity: FragmentActivity): HolderFragment {
            return getHolderFragment(activity.supportFragmentManager)
        }

        private fun getHolderFragment(fragmentManager: FragmentManager): HolderFragment {
            var holderFragment = findHolderFragment(fragmentManager)
            if (holderFragment == null) {
                holderFragment = HolderFragment()
                fragmentManager.beginTransaction()
                    .add(holderFragment, TAG)
                    .commitAllowingStateLoss() // 执行
                fragmentManager.executePendingTransactions()
            }
            return holderFragment
        }

        private fun findHolderFragment(fragmentManager: FragmentManager?): HolderFragment? {
            if (fragmentManager != null) {
                return fragmentManager.findFragmentByTag(TAG) as HolderFragment?
            }
            return null
        }
    }


}