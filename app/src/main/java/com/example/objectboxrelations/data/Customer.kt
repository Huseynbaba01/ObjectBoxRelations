package com.example.objectboxrelations.data
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Customer(
	@Id var id: Long = 0
)
