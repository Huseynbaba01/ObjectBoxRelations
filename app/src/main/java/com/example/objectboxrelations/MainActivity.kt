package com.example.objectboxrelations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.objectboxrelations.data.Customer
import com.example.objectboxrelations.data.ObjectBox.boxStore
import com.example.objectboxrelations.data.Order
import io.objectbox.Box


class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val customer = Customer()
		val order = Order()
		order.customer.target = customer

		val orderId = boxStore?.boxFor(Order::class.java)
		val customerId = boxStore?.boxFor(Customer::class.java)
	}
}