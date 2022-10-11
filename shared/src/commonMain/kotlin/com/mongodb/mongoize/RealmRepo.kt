package com.mongodb.mongoize

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RealmRepo {

    private val schemaClass = setOf(UserInfo::class, SessionInfo::class, ConferenceInfo::class)

    private val appService by lazy {
        val appConfiguration = AppConfiguration
            .Builder(appId = "application-0-uxqmn")
            .log(LogLevel.ALL)
            .build()
        App.create(appConfiguration)
    }

    private val realm by lazy {
        val user = appService.currentUser!!

        val config = SyncConfiguration.Builder(user, schemaClass)
            .name("conferenceInfo")
            .schemaVersion(1)
            .initialSubscriptions { realm ->
                add(realm.query<UserInfo>(), name = "user info", updateExisting = true)
                add(realm.query<SessionInfo>(), name = "session info", updateExisting = true)
                add(realm.query<ConferenceInfo>(), name = "conference info", updateExisting = true)
            }
            .waitForInitialRemoteData()
            .build()
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
        println("State: ${realm.subscriptions.forEach {
            println("State Query: ${it.name} --- ${it.queryDescription} -- ${it.objectType}")    
        }}")


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

    fun saveUserInfo() {
        if (appService.currentUser != null) {
            val user = UserInfo().apply {
                _id = appService.currentUser!!.id
                email = "ex.com"
            }

            realm.writeBlocking {
                println("getUserProfile writeBlocking");
                copyToRealm(user)
            }
        }
    }

    suspend fun doLogout() {
        appService.currentUser?.logOut()
    }


}