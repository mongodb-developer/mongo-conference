package com.mongodb.mongoize

import io.realm.kotlin.types.ObjectId
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
    var _id: ObjectId = ObjectId.create()
    var talkTitle: String = ""
    var abstract: String = ""
    var duration: Int = 0
    var isAccepted: Boolean = false
    var conferenceId: ObjectId = ObjectId.create()
}

class ConferenceInfo : RealmObject {

    @PrimaryKey
    var _id: ObjectId = ObjectId.create()
    var name: String = ""
    var startDate: String = ""
    var endDate: String = ""
    var location: String = ""
    @Ignore
    var submissionCount: Long = 0L
}







