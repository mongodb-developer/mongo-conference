package com.mongodb.mongoize

import CommonFlow
import asCommonFlow
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.User
import io.realm.kotlin.mongodb.subscriptions
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.types.ObjectId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RealmRepo {

    private val schemaClass = setOf(UserInfo::class, SessionInfo::class, ConferenceInfo::class)

    private val appService by lazy {
        val appConfiguration =
            AppConfiguration.Builder(appId = "rconfernce-vkrny").log(LogLevel.ALL).build()
        App.create(appConfiguration)
    }

    private val realm by lazy {
        val user = appService.currentUser!!

        val config =
            SyncConfiguration.Builder(user, schemaClass).name("conferenceInfo").schemaVersion(1)
                .initialSubscriptions { realm ->
                    add(realm.query<UserInfo>(), name = "user info", updateExisting = true)
                    add(realm.query<SessionInfo>(), name = "session info", updateExisting = true)
                    add(
                        realm.query<ConferenceInfo>(),
                        name = "conference info",
                        updateExisting = true
                    )
                }.waitForInitialRemoteData().build()
        Realm.open(config)
    }

    suspend fun login(email: String, password: String): User {
        return appService.login(Credentials.emailPassword(email, password))
    }

    suspend fun registration(userName: String, password: String, email: String) {
        appService.emailPasswordAuth.registerUser(email, password)
    }

    fun getUserProfile(): Flow<UserInfo?> {

        println("State: ${realm.subscriptions.state}")
        println("State: ${
            realm.subscriptions.forEach {
                println("State Query: ${it.name} --- ${it.queryDescription} -- ${it.objectType}")
            }
        }")


        println("getUserProfile called")

        val count = realm.query<UserInfo>().count().find()
        println("getUserProfile userCount $count")

        val userId = appService.currentUser!!.id

        println("getUserProfile userId $userId")

        val user = realm.query<UserInfo>("_id = $0", userId).asFlow().map {
            println("getUserProfile userId ${it.list.size}")
            it.list.firstOrNull()
        }

        return user
    }

    fun isUserLoggedIn(): Flow<Boolean> {
        return flowOf(appService.currentUser != null)
    }

    suspend fun saveUserInfo(name: String, orgName: String, phoneNumber: String) {
        withContext(Dispatchers.Default) {
            if (appService.currentUser != null) {
                val userId = appService.currentUser!!.id
                realm.write {
                    var user = query<UserInfo>("_id = $0", userId).first().find()
                    if (user != null) {
                        user = findLatest(user)!!.also {
                            it.name = name
                            it.orgName = orgName
                            it.phoneNumber = phoneNumber.toLongOrNull()
                        }
                        copyToRealm(user)
                    }
                }
            }
        }
    }

    suspend fun doLogout() {
        appService.currentUser?.logOut()
    }

    suspend fun addConference(name: String, location: String, startDate: String, endDate: String) {
        withContext(Dispatchers.Default) {
            realm.write {
                val conferenceInfo = ConferenceInfo().apply {
                    this.name = name
                    this.location = location
                    this.startDate = startDate
                    this.endDate = endDate
                }
                copyToRealm(conferenceInfo)
            }
        }
    }

    suspend fun getEventLists(): CommonFlow<List<ConferenceInfo>> {
        return withContext(Dispatchers.Default) {
            realm.query<ConferenceInfo>().asFlow().map {
                it.list
            }
        }.asCommonFlow()
    }

    suspend fun getTalks(conferenceId: ObjectId): CommonFlow<List<SessionInfo>> {
        return withContext(Dispatchers.Default) {
            realm.query<SessionInfo>("conferenceId = $0 && isAccepted != true", conferenceId)
                .asFlow().map {
                    it.list
                }.asCommonFlow()
        }
    }

    suspend fun getSelectedTalks(conferenceId: ObjectId): CommonFlow<List<SessionInfo>> {
        return withContext(Dispatchers.Default) {
            realm.query<SessionInfo>("conferenceId = $0 && isAccepted = true", conferenceId)
                .asFlow().map {
                    it.list
                }.asCommonFlow()
        }
    }

    suspend fun updateTalkState(sessionId: ObjectId, state: Boolean) {
        return withContext(Dispatchers.Default) {
            val session = realm.query<SessionInfo>("_id = $0", sessionId).first().find()
            if (session != null) {
                realm.write {
                    (findLatest(session) as SessionInfo).run {
                        this.isAccepted = state
                        copyToRealm(this)
                    }
                }
            }

        }
    }

}