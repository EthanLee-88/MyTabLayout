package com.ethan.launcher.launch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

public class LauncherBuilder {
    private static final String TAG = "LauncherBuilder";

    private WeakReference<FragmentActivity> mActivity;
    private WeakReference<Fragment> mFragment;

    private LauncherConfig mLauncherConfig;

    private LauncherBuilder(FragmentActivity activity) {
        mActivity = new WeakReference<>(activity);
    }

    private LauncherBuilder(Fragment fragment) {
        mFragment = new WeakReference<>(fragment);
    }

    public static LauncherBuilder with(FragmentActivity activity) {
        return new LauncherBuilder(activity);
    }

    public static LauncherBuilder with(Fragment fragment) {
        return new LauncherBuilder(fragment);
    }

    public LauncherBuilder setLauncherConfig(LauncherConfig mLauncherConfig) {
        this.mLauncherConfig = mLauncherConfig;
        return this;
    }

    private LauncherConfig getCurrentLauncherConfig() {
        if (this.mLauncherConfig == null) {
            this.mLauncherConfig = new LauncherConfig(0, "", null);
        }
        return this.mLauncherConfig;
    }

    public void startLauncherActivity(LauncherCallBack callBack) {
        if ((mActivity != null) && (mActivity.get() != null)) {
            HolderFragment.getHolderFragment(mActivity.get()).startLauncher(callBack, getCurrentLauncherConfig());
            return;
        }
        if ((mFragment != null) && (mFragment.get() != null)) {
            HolderFragment.getHolderFragment(mFragment.get()).startLauncher(callBack, getCurrentLauncherConfig());
            return;
        }
        throw new RuntimeException("mActivity or mFragmentV maybe null, you can not use this method... ");
    }
}
