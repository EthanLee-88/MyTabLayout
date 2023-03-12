package com.ethan.launcher.launch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class Launcher {

    public static LauncherBuilder launchWith(FragmentActivity fragmentActivity) {
        return LauncherBuilder.with(fragmentActivity);
    }

    public static LauncherBuilder launchWith(Fragment fragment) {
        return LauncherBuilder.with(fragment);
    }
}
