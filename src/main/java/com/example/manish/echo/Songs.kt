package com.example.manish.echo

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable


@SuppressLint("ParcelCreator")
/**
 * Created by manish on 07-Jan-18.
 */
class Songs(var songID: Long ,var songTitle: String,var artist: String,var songData: String,var dataAdded:Long):Parcelable{
    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }

    override fun describeContents(): Int {
     return 0
    }
    object Statified{
        var nameComparator: Comparator<Songs> = Comparator<Songs>{ song1,song2 ->
            val songone=song1.songTitle.toUpperCase()
            val songtwo=song2.songTitle.toUpperCase()
            songone.compareTo(songtwo)
        }
        var dateComparator: Comparator<Songs> = Comparator<Songs>{song1,song2 ->
            val songone=song1.dataAdded.toDouble()
            val songtwo=song2.dataAdded.toDouble()
            songtwo.compareTo(songone)
        }
    }

}