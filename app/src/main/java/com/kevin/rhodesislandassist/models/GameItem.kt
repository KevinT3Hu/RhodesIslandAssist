package com.kevin.rhodesislandassist.models

import android.os.Parcel
import android.os.Parcelable

data class GameItem(
    val itemId:String?,
    val name:String?,
    val description:String?,
    val rarity:Int,
    val iconId:String?,
    val usage:String?,
    val itemType:String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    class ItemType{
        companion object{
            const val MATERIAL="MATERIAL"
            const val CARD_EXP="CARD_EXP"
            const val ACTIVITY_COIN="ACTIVITY_COIN"
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemId)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(rarity)
        parcel.writeString(iconId)
        parcel.writeString(usage)
        parcel.writeString(itemType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameItem> {
        override fun createFromParcel(parcel: Parcel): GameItem {
            return GameItem(parcel)
        }

        override fun newArray(size: Int): Array<GameItem?> {
            return arrayOfNulls(size)
        }
    }
}
