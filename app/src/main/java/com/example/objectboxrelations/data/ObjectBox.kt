package com.example.objectboxrelations.data

import android.content.Context
import io.objectbox.BoxStore

object ObjectBox {
//	var boxStoreForOrder: BoxStore? = null
	var boxStoreForCustomer: BoxStore? = null
		private set

	fun init(context: Context) {
		/*if(boxStoreForOrder == null) {
			boxStoreForOrder = MyObjectBox.builder()
				.androidContext(context.applicationContext)
				.build()
		}*/
		if(boxStoreForCustomer == null){
			boxStoreForCustomer = MyObjectBox.builder()
				.androidContext(context.applicationContext)
				.build()
		}
	}
}