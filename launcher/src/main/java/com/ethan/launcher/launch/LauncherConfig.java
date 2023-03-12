package com.ethan.launcher.launch;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class LauncherConfig implements Parcelable {
    private static final String TAG = "LauncherConfig";

    public int configCode;
    public String configStr;
    public Uri configUri;

    public LauncherConfig(int code, String str, Uri uri) {
        configCode = code;
        configStr = str;
        configUri = uri;
    }

    protected LauncherConfig(Parcel in) {
        configCode = in.readInt();
        configStr = in.readString();
        configUri = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(configCode);
        dest.writeString(configStr);
        dest.writeParcelable(configUri, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LauncherConfig> CREATOR = new Creator<LauncherConfig>() {
        @Override
        public LauncherConfig createFromParcel(Parcel in) {
            return new LauncherConfig(in);
        }

        @Override
        public LauncherConfig[] newArray(int size) {
            return new LauncherConfig[size];
        }
    };

    @Override
    public String toString() {
        return "LauncherConfig{" +
                "configCode=" + configCode +
                ", configStr='" + configStr + '\'' +
                ", configUri=" + configUri +
                '}';
    }
}
