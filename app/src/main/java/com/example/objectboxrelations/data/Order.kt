package com.example.objectboxrelations.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class Order(
	@Id var id: Long = 0,
	var name: String? = null
){
	lateinit var customer: ToOne<Customer>
	override fun toString(): String {
		return "id: $id name: $name customer id:${this.customer.target.id}\n"
	}
}
