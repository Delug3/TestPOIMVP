/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.delug3.testpoi.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "poi_table")
class PoiRoom(

        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,
        @ColumnInfo(name = "title")
        val title: String?,
        @ColumnInfo(name = "address")
        val address: String?,
        @ColumnInfo(name = "transport")
        val transport: String?,
        @ColumnInfo(name = "email")
        val email: String?,
        @ColumnInfo(name = "geocoordinates")
        val geocoordinates: String?,
        @ColumnInfo(name = "description")
        val description: String?)


