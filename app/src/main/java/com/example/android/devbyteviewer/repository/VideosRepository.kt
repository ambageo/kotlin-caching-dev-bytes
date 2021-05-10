/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideoDao
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel

// TODO (01) Create a VideosRepository class that takes a VideosDatabase argument. //DONE
class VideosRepository (private val database: VideosDatabase){
    // TODO (02) Define a suspend refreshVideos() function that gets data from the network and //DONE
// inserts it into the database.
    suspend fun refreshVideos(){
        //getPlayList is suspend fun, we don't have to call await()
        val playlist = Network.devbytes.getPlaylist()
        // playlist is a List<NetworkVideo>, we want to insert it to the database
        // so we convert it to Array<DatabaseVideo>
        database.videoDao.insertAll(*playlist.asDatabaseModel())
    }

    // TODO (03) Define a Transformations.map  to convert the DatabaseVideo list to a list of Video. //DONE
    /*
    * We get a LiveData List of DatabaseVideo objects from the database; we need to convert it to
    * a LiveData List of Video (Domain) objects
    */
    val videos: LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()){
        it.asDomainModel()
    }

}




