package com.ethan.launcherkt.launch

import android.os.Parcel
import android.os.Parcelable

class EasyConfig() : Parcelable {

    var code: Int = 0
    var configStr: String = ""

    constructor(c: Int, str: String) : this() {
        code = c
        configStr = str
    }

    constructor(parcel: Parcel) : this() {
        code = parcel.readInt()
        configStr = parcel.readString()!!
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeInt(code)
        p0?.writeString(configStr)
    }

    companion object CREATOR : Parcelable.Creator<EasyConfig> {
        override fun createFromParcel(parcel: Parcel): EasyConfig {
            return EasyConfig(parcel)
        }

        override fun newArray(size: Int): Array<EasyConfig?> {
            return arrayOfNulls(size)
        }
    }

}