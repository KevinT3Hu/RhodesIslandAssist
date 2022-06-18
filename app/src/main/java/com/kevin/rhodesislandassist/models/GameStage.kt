package com.kevin.rhodesislandassist.models

import android.os.Parcel
import android.os.Parcelable
import com.kevin.rhodesislandassist.R

enum class StageType {
    MAIN,
    ACTIVITY,
    DAILY,
    GUIDE,
    SUB,
    SPECIAL_STORY,
    CAMPAIGN;

    companion object {
        fun getLabelId(stageType: StageType) = when (stageType) {
            MAIN, SUB -> R.string.stage_type_main
            ACTIVITY -> R.string.stage_type_activity
            DAILY -> R.string.stage_type_daily
            CAMPAIGN -> R.string.stage_type_campaign
            else -> R.string.stage_type_default
        }
    }
}

enum class Difficulty {
    NORMAL,
    FOUR_STAR
}

data class GameStage(
    val stageId: String?,
    val code: String?,
    val name: String?,
    val desc: String?,
    val difficulty: Difficulty,
    val type: StageType,
    val apCost: Int,
    val stageDrops: List<DropInfo>?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        Difficulty.valueOf(parcel.readString()?:"NORMAL"),
        StageType.valueOf(parcel.readString()?:"MAIN"),
        parcel.readInt(),
        parcel.createTypedArrayList(DropInfo.CREATOR)?.toList()
    ) {
    }

    enum class DropType {
        FIRST_TIME, //for 1
        COMMON, //for 2
        MINOR_CHANCE, //for 3
        SMALL_CHANCE, //for 4
    }

    data class DropInfo(
        val id: String?,
        val dropType: DropType
    ):Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            DropType.valueOf(parcel.readString()?:"COMMON")
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(dropType.toString())
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<DropInfo> {
            override fun createFromParcel(parcel: Parcel): DropInfo {
                return DropInfo(parcel)
            }

            override fun newArray(size: Int): Array<DropInfo?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(stageId)
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeString(desc)
        parcel.writeString(difficulty.toString())
        parcel.writeString(type.toString())
        parcel.writeInt(apCost)
        parcel.writeTypedList(stageDrops)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameStage> {
        override fun createFromParcel(parcel: Parcel): GameStage {
            return GameStage(parcel)
        }

        override fun newArray(size: Int): Array<GameStage?> {
            return arrayOfNulls(size)
        }
    }
}
