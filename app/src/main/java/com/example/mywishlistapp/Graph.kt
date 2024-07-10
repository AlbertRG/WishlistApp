package com.example.mywishlistapp

import android.content.Context
import androidx.room.Room
import com.example.mywishlistapp.data.WishDatabase
import com.example.mywishlistapp.data.WishRepository

object Graph {

    lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()
    }

}

/*BY LAZY EXPLANATION
In Kotlin, **by lazy** is a way to declare a property whose initialization is deferred until the
first usage. It's particularly useful when the initialization of a property is computationally
expensive or depends on external factors that might change over time.

val myLazyValue: String by lazy {
    // This block is executed the first time myLazyValue is accessed
    "Hello, Lazy!"
}

In this example:
-> myLazyValue is a String property that is initialized by the lambda expression { "Hello, Lazy!" }.
-> The lambda expression will only be evaluated the first time myLazyValue is accessed.
-> After the first evaluation, subsequent accesses to myLazyValue will return the cached result of
the lambda.

Key Points:
**Thread Safety**: by lazy is thread-safe by default. The initialization is synchronized such that
the lambda is executed exactly once, even in a multi-threaded environment.
**Immutable**: Once initialized, the value of a by lazy property cannot be changed, making it
effectively immutable.
**Initialization**: The lambda expression passed to by lazy initializes the property and its result
is stored for future accesses.

This feature helps optimize performance by delaying the initialization of properties until they are
actually needed, which can be crucial in reducing unnecessary computation or resource usage in your
Kotlin code.
*/