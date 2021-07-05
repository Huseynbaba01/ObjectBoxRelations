package com.example.objectboxrelations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.objectboxrelations.data.Customer
import com.example.objectboxrelations.data.ObjectBox
import com.example.objectboxrelations.data.ObjectBox.boxStoreForCustomer
import com.example.objectboxrelations.data.ObjectBox.init
//import com.example.objectboxrelations.data.ObjectBox.boxStoreForOrder
import com.example.objectboxrelations.data.Order
import io.objectbox.Box
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription


class MainActivity : AppCompatActivity() {
	private lateinit var buttonUpdate:Button
	private lateinit var button:Button
	private lateinit var textView: TextView
	private lateinit var inputEditText: EditText
	private lateinit var subscriptionOrder: DataSubscription
	private lateinit var subscriptionCustomer: DataSubscription
	private lateinit var queryOrder: Query<Order>
	private lateinit var queryCustomer: Query<Customer>
	private lateinit var orderBox: Box<Order>
	private lateinit var customerBox: Box<Customer>
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initializeBoxForOrder()
		initializeBoxForCustomer()
//		initializeQueryForOrder()
		initializeQueryForCustomer()
		initializeCustomerObserver()
//		initializeOrderObserver()
//		initializeQueryForCustomer()
		buttonUpdate = findViewById(R.id.button_update)
		button = findViewById(R.id.button_click)
		textView = findViewById(R.id.text_view)
		inputEditText = findViewById(R.id.inputOrderName)
		/*button.setOnClickListener {
			var string = inputEditText.text.toString()
			if(string.isNullOrEmpty()){
				Toast.makeText(this,"Enter valid text",Toast.LENGTH_SHORT).show()
			}
			else{
				val order = Order()
				val customer = Customer()
				order.name = string
				order.customer.target = customer
				orderBox.put(order)
			}
		}*/
		button.setOnClickListener {
			val list = inputEditText.text.toString().split(" ")
			val customer = Customer()
			customer.name = list[0]
			for(i in 1..list.size-1){
				val order = Order()
				order.name = list[i]
				customer.orders.add(order)
			}
			customerBox.put(customer)
		}
		buttonUpdate.setOnClickListener {
			val list = inputEditText.text.toString().split(" ")
			val tempCustomer = customerBox[list[0].toLong()]
			customerBox[list[0].toLong()].orders.forEach {
				tempCustomer.orders.remove(it)
			}
			customerBox.put(tempCustomer)
			val customer = Customer()
			customer.id = list[0].toLong()

			customer.name = list[1]
			customerBox.attach(customer)
			for(i in 2..list.size-1){
				val order = Order()
				order.name = list[i]
				customer.orders.add(order)
			}
			customerBox.put(customer)
		}
		val customer = Customer()
		val order = Order()
		order.customer.target = customer
//		val orderId = boxStoreForOrder?.boxFor(Order::class.java)
		val customerId = boxStoreForCustomer?.boxFor(Customer::class.java)
	}

	private fun initializeBoxForOrder() {
		ObjectBox.init(this)
//		orderBox = boxStoreForOrder!!.boxFor(Order::class.java)
	}

	/*private fun initializeOrderObserver() {
		subscriptionOrder = queryOrder.subscribe().observer {
			updateResultDisplay(
				it
			)
		}
	}*/
	private fun initializeCustomerObserver() {
		subscriptionCustomer = queryCustomer.subscribe().observer {
			updateResultDisplayForCustomer(
				it
			)
		}
	}

	private fun updateResultDisplayForCustomer(it: List<Customer>) {
		val stringBuilder = StringBuilder()
		it.forEach {
			stringBuilder.append("${it.id.toString()} ${it.name}   \n")
			it.orders.forEach {
				stringBuilder.append("\t ${it.id.toString()}) ${it.name}\n")
			}
		}
		textView.text = stringBuilder
	}

	private fun updateResultDisplay(it: List<Order>) {
		val stringBuilder = StringBuilder()
		it.forEach {
			stringBuilder.append(it.toString())
		}
		textView.text = stringBuilder
	}

	private fun initializeQueryForCustomer() {
		queryCustomer = customerBox
			.query()
			.build()
	}

	private fun initializeQueryForOrder() {
		queryOrder = orderBox
			.query()
			.build()
	}

	private fun initializeBoxForCustomer() {
		init(this)
		customerBox = boxStoreForCustomer!!.boxFor(Customer::class.java)
	}
}