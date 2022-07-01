package com.kevin.rhodesislandassist.models

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kevin.rhodesislandassist.R

data class Character(
    val name: String,
    val desc: String,
    val nationId: String,
    val tagList: List<String>,
    val position: Position,
    val itemUsage: String,
    val profession: Profession,
    val subProfessionalId: String,
    val rarity: Int,
    val phases: List<Phase>,
    val skills: List<String>,
    val talents: List<Talent>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        Position.valueOf(parcel.readString()!!),
        parcel.readString()!!,
        Profession.valueOf(parcel.readString()!!),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.createTypedArrayList(Phase.CREATOR)!!,
        parcel.createStringArrayList()!!,
        parcel.createTypedArrayList(Talent.CREATOR)!!
    )

    fun getTotalLevel(): Int {
        var total = 0
        phases.forEach {
            total += it.maxLevel
        }
        return total
    }

    //fun calculateAttribute(phase:Int,level: Int)=phases[phase].attributeLevel1+((level-1)/(phases[phase].maxLevel-1)*(phases[phase].attributeLevelMax-phases[phase].attributeLevel1))

    //for debug purposes
    fun calculateAttribute(phase: Int, level: Int): Attribute {
        Log.i("attr", "${phase}-$level")
        return phases[phase].attributeLevel1 + (((level - 1) / (phases[phase].maxLevel - 1).toFloat()) * (phases[phase].attributeLevelMax - phases[phase].attributeLevel1))
    }


    enum class Position {
        MELEE,
        RANGED
    }

    enum class Profession {
        MEDIC,
        WARRIOR,
        SPECIAL,
        SNIPER,
        PIONEER,
        TANK,
        CASTER,
        SUPPORT;

        @DrawableRes
        fun getDrawableIcon() = when (this) {
            MEDIC -> R.drawable.icon_medic
            WARRIOR -> R.drawable.icon_warrior
            SPECIAL -> R.drawable.icon_special
            SNIPER -> R.drawable.icon_sniper
            PIONEER -> R.drawable.icon_pioneer
            TANK -> R.drawable.icon_tank
            CASTER -> R.drawable.icon_caster
            SUPPORT -> R.drawable.icon_support
        }

        @StringRes
        fun getProfessionName() = when (this) {
            MEDIC -> R.string.prof_medic
            WARRIOR -> R.string.prof_warrior
            SPECIAL -> R.string.prof_special
            SNIPER -> R.string.prof_sniper
            PIONEER -> R.string.prof_pioneer
            TANK -> R.string.prof_tank
            CASTER -> R.string.prof_caster
            SUPPORT -> R.string.prof_support
        }
        
    }

    data class Attribute(
        val maxHp: Int,
        val attack: Int,
        val defense: Int,
        val magicResistance: Int,
        val cost: Int,
        val attackSpeed: Int,
        val respawnTime: Int,
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(maxHp)
            parcel.writeInt(attack)
            parcel.writeInt(defense)
            parcel.writeInt(magicResistance)
            parcel.writeInt(cost)
            parcel.writeInt(attackSpeed)
            parcel.writeInt(respawnTime)
        }

        override fun describeContents(): Int {
            return 0
        }

        operator fun plus(attribute: Attribute) = Attribute(
            this.maxHp + attribute.maxHp,
            this.attack + attribute.attack,
            this.defense + attribute.defense,
            this.magicResistance + attribute.magicResistance,
            this.cost,
            this.attackSpeed,
            this.respawnTime
        )

        operator fun unaryMinus() = Attribute(
            -maxHp, -attack, -defense, -magicResistance, cost, attackSpeed, respawnTime
        )

        operator fun minus(attribute: Attribute) = this + (-attribute)

        operator fun times(n: Float) = Attribute(
            (n * maxHp).toInt(),
            (n * attack).toInt(),
            (n * defense).toInt(),
            (n * magicResistance).toInt(),
            cost, attackSpeed, respawnTime
        )

        operator fun div(n: Int) = Attribute(
            maxHp / n,
            attack / n,
            defense / n,
            magicResistance / n,
            cost, attackSpeed, respawnTime
        )

        companion object CREATOR : Parcelable.Creator<Attribute> {
            override fun createFromParcel(parcel: Parcel): Attribute {
                return Attribute(parcel)
            }

            override fun newArray(size: Int): Array<Attribute?> {
                return arrayOfNulls(size)
            }

            val Empty = Attribute(0, 0, 0, 0, 0, 0, 0)
        }

    }

    data class Phase(
        val maxLevel: Int,
        val attributeLevel1: Attribute,
        val attributeLevelMax: Attribute
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readParcelable<Attribute>(Attribute::class.java.classLoader)!!,
            parcel.readParcelable<Attribute>(Attribute::class.java.classLoader)!!
        )

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(maxLevel)
            parcel.writeParcelable(attributeLevel1, flags)
            parcel.writeParcelable(attributeLevelMax, flags)
        }

        companion object CREATOR : Parcelable.Creator<Phase> {
            override fun createFromParcel(parcel: Parcel): Phase {
                return Phase(parcel)
            }

            override fun newArray(size: Int): Array<Phase?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Talent(
        val candidates: List<Candidate>
    ) : Parcelable {

        data class Candidate(
            val phase: Int,
            val level: Int,
            val name: String,
            val desc: String
        ) : Parcelable {
            constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString()!!,
                parcel.readString()!!
            )

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(phase)
                parcel.writeInt(level)
                parcel.writeString(name)
                parcel.writeString(desc)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<Candidate> {
                override fun createFromParcel(parcel: Parcel): Candidate {
                    return Candidate(parcel)
                }

                override fun newArray(size: Int): Array<Candidate?> {
                    return arrayOfNulls(size)
                }
            }
        }

        constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Candidate)!!)

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeTypedList(candidates)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Talent> {
            override fun createFromParcel(parcel: Parcel): Talent {
                return Talent(parcel)
            }

            override fun newArray(size: Int): Array<Talent?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(desc)
        parcel.writeString(nationId)
        parcel.writeStringList(tagList)
        parcel.writeString(position.name)
        parcel.writeString(itemUsage)
        parcel.writeString(profession.name)
        parcel.writeString(subProfessionalId)
        parcel.writeInt(rarity)
        parcel.writeTypedList(phases)
        parcel.writeStringList(skills)
        parcel.writeTypedList(talents)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }
}

private operator fun Float.times(attribute: Character.Attribute) = attribute * this