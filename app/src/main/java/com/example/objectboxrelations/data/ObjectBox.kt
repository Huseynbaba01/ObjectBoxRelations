package com.example.objectboxrelations.data

import android.content.Context
import io.objectbox.BoxStore

object ObjectBox {
	var boxStore: BoxStore? = null
	private set
	fun init(context: Context){
		if(boxStore == null){
			boxStore = MyObjectBox.builder()
				.androidContext(context.applicationContext)
				.build()
		}
	}
}