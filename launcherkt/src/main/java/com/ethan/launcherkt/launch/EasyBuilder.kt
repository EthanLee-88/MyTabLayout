package com.ethan.launcherkt.launch

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference

class EasyBuilder {

    private lateinit var mFragment: WeakReference<Fragment>
    private lateinit var mActivity: WeakReference<FragmentActivity>

    private lateinit var mConfig: EasyConfig

    private constructor(fragment: Fragment) {
        mFragment = WeakReference(fragment)
    }

    private constructor(activity: FragmentActivity) {
        mActivity = WeakReference(activity)
    }

    companion object Instance {
        fun with(fragment: Fragment): EasyBuilder {
            return EasyBuilder(fragment)
        }

        fun with(activity: FragmentActivity): EasyBuilder {
            return EasyBuilder(activity)
        }
    }

    fun setConfig(config: EasyConfig) {
        this.mConfig = config
    }

    private fun getLocalConfig(): EasyConfig {
        if (!this::mConfig.isInitialized) {
            this.mConfig = EasyConfig(888, "碧云天")
        }
        return this.mConfig
    }

    fun launchActivity(callBack: LaunchCallBack) {
        if (this::mActivity.isInitialized) {
            val activity = mActivity.get()
            if (activity != null) {
                HolderFragment.getHolderFragment(activity)
                    .launchActivity(callBack, getLocalConfig())
            }
            return
        }
        if ((this::mFragment.isInitialized) && (this.mFragment.get() != null)) {
            val fragment = this.mFragment.get()
            if (fragment != null) {
                HolderFragment.getHolderFragment(fragment)
                    .launchActivity(callBack, getLocalConfig())
            }
        }
    }
}