package com.mongodb.mongoize

import io.realm.kotlin.mongodb.User
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey


class UserInfo : RealmObject {
    @PrimaryKey
    var _id: String = ""
    var name: String = ""
    var email: String = ""
    var orgName: String? = null
    var phoneNumber: Long? = null
    var isAdmin: Boolean = false

}

class SessionInfo : RealmObject {

    @PrimaryKey
    var _id: String = ""
    var talkTitle: String = ""
    var abstract: String = ""
    var duration: Int = 0
    var startsAt: RealmInstant? = null
    var speaker: UserInfo? = null
    var conferenceInfo: ConferenceInfo? = null
}

class ConferenceInfo : RealmObject {

    @PrimaryKey
    var _id: String = ""
    var title: String = ""
    lateinit var startDate: RealmInstant
    lateinit var endDate: RealmInstant
    var location: String = ""
    //todo: var tags: List<String> = emptyList()

}




