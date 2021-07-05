package com.example.objectboxrelations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.objectboxrelations.data.Customer
import com.example.objectboxrelations.data.ObjectBox
import com.example.objectboxrelations.data.ObjectBox.boxStore
import com.example.objectboxrelations.data.Order
import io.objectbox.Box
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription


class MainActivity : AppCompatActivity() {
	private lateinit var button:Button
	private lateinit var textView: TextView
	private lateinit var inputEditText: EditText
	private lateinit var subscription: DataSubscription
	private lateinit var queryOrder: Query<Order>
	private lateinit var queryCustomer: Query<Customer>
	private lateinit var orderBox: Box<Order>
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initializeBoxForOrder()
//		initializeBoxForCustomer()
		initializeQueryForOrder()
		initializeOrderObserver()
//		initializeQueryForCustomer()
		button = findViewById(R.id.button_click)
		textView = findViewById(R.id.text_view)
		inputEditText = findViewById(R.id.inputOrderName)
		button.setOnClickListener {
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
		}
		val customer = Customer()
		val order = Order()
		order.customer.target = customer
		val orderId = boxStore?.boxFor(Order::class.java)
		val customerId = boxStore?.boxFor(Customer::class.java)

	}

	private fun initializeBoxForOrder() {
		ObjectBox.init(this)
		orderBox = boxStore!!.boxFor(Order::class.java)
	}

	private fun initializeOrderObserver() {
		subscription = queryOrder.subscribe().observer {
			updateResultDisplay(
				it
			)
		}
	}

	private fun updateResultDisplay(it: List<Order>) {
		val stringBuilder = StringBuilder()
		it.forEach {
			stringBuilder.append(it.toString())
		}
		textView.text = stringBuilder
	}

	/*private fun initializeQueryForCustomer() {
		TODO("Not yet implemented")
	}*/

	private fun initializeQueryForOrder() {
		queryOrder = orderBox
			.query()
			.build()
	}

	/*private fun initializeBoxForCustomer() {

	}*/
}