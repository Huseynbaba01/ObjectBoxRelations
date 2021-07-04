package com.example.objectboxrelations.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class Order(
	@Id var id: Long = 0
){
	lateinit var customer: ToOne<Customer>
}
