package com.ethan.launcher.launch;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.ethan.launcher.MainLauncherActivity;

import static com.ethan.launcher.launch.LauncherConstance.LAUNCHER_CONFIG_SETTING;

public class HolderFragment extends Fragment {
    private static final String TAG = "com.launcher.launcher";

    private static final int LAUNCHER_START_REQUEST_CODE = 0x66;

    private LauncherCallBack mLauncherCallBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static HolderFragment getHolderFragment(FragmentActivity fragmentActivity) {
        return getHolderFragment(fragmentActivity.getSupportFragmentManager());
    }

    public static HolderFragment getHolderFragment(Fragment fragment) {
        return getHolderFragment(fragment.getChildFragmentManager());
    }

    private static HolderFragment getHolderFragment(FragmentManager fragmentManager) {
        HolderFragment holderFragment = findHolderFragment(fragmentManager);
        if (holderFragment == null) {
            holderFragment = new HolderFragment();
            fragmentManager.beginTransaction()
                    .add(holderFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return holderFragment;
    }

    private static HolderFragment findHolderFragment(FragmentManager fragmentManager) {
        return (HolderFragment) fragmentManager.findFragmentByTag(TAG);
    }


    public void startLauncher(LauncherCallBack callBack, LauncherConfig launcherConfig) {
        this.mLauncherCallBack = callBack;
        Intent intent = new Intent(getContext(), MainLauncherActivity.class);
        intent.putExtra(LAUNCHER_CONFIG_SETTING, launcherConfig);
        startActivityForResult(intent, LAUNCHER_START_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == LAUNCHER_START_REQUEST_CODE) && (resultCode == 10086)) {
            if (mLauncherCallBack != null) {
                mLauncherCallBack.onLauncherResult(10088);
            }
        }
    }
}
