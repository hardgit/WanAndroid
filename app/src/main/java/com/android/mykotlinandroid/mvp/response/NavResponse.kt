package com.android.mykotlinandroid.mvp.response

import android.os.Parcel
import android.os.Parcelable

/**
 * author : zf
 * date   : 2019/11/4
 * You are the best.
 */
data class NavResponse(
    val articles: List<Article>,
    val cid: Int,
    val name: String
)

data class Article(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val selfVisible: Int,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Any>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        TODO("tags"),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(apkLink)
        parcel.writeInt(audit)
        parcel.writeString(author)
        parcel.writeInt(chapterId)
        parcel.writeString(chapterName)
        parcel.writeByte(if (collect) 1 else 0)
        parcel.writeInt(courseId)
        parcel.writeString(desc)
        parcel.writeString(envelopePic)
        parcel.writeByte(if (fresh) 1 else 0)
        parcel.writeInt(id)
        parcel.writeString(link)
        parcel.writeString(niceDate)
        parcel.writeString(niceShareDate)
        parcel.writeString(origin)
        parcel.writeString(prefix)
        parcel.writeString(projectLink)
        parcel.writeLong(publishTime)
        parcel.writeInt(selfVisible)
        parcel.writeString(shareUser)
        parcel.writeInt(superChapterId)
        parcel.writeString(superChapterName)
        parcel.writeString(title)
        parcel.writeInt(type)
        parcel.writeInt(userId)
        parcel.writeInt(visible)
        parcel.writeInt(zan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}