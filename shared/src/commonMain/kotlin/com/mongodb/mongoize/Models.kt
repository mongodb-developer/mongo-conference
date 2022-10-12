package com.mongodb.mongoize

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
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
    var _id: RealmUUID = RealmUUID.random()
    var talkTitle: String = ""
    var abstract: String = ""
    var duration: Int = 0
    var startsAt: RealmInstant? = null
    var speaker: UserInfo? = null
    var conferenceInfo: RealmUUID? = null
}

class ConferenceInfo : RealmObject {

    @PrimaryKey
    var _id: RealmUUID = RealmUUID.random()
    var name: String = ""
    var startDate: RealmInstant = RealmInstant.MIN
    var endDate: RealmInstant = RealmInstant.MIN
    var location: String = ""
}




