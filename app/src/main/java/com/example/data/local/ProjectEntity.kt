package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val type: String, // "Script", "Strategy", "Idea", "Analysis"
  val status: String, // "Draft", "In Progress", "Ready", "Published"
  val hook: String = "",
  val content: String,
  val format: String = "Shorts",
  val score: Int = 0,
  val createdAt: Long = System.currentTimeMillis()
)
