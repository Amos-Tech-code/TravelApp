package com.amos_tech_code.travelapp.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun dataStoreImpl(context: Context) = createDataStore {
    context.dataDir.resolve(dataStoreFileName).absolutePath
}

lateinit var dataStore : DataStore<Preferences>

fun createDataStore(producePath : () -> String): DataStore<Preferences> {

    if(::dataStore.isInitialized) {
        return dataStore

    } else {
        dataStore = PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                producePath().toPath()
            }
        )
    }
    return dataStore

}

val dataStoreFileName = "travel_app.preferences_pb"