package com.example.mywishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywishlistapp.data.Wish
import com.example.mywishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
) : ViewModel() {

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChanged(newString: String) {
        wishTitleState = newString
    }

    fun onWishDescriptionChanged(newString: String) {
        wishDescriptionState = newString
    }

    fun addWish(wish: Wish) {
        viewModelScope.launch() {
            wishRepository.addWish(wish = wish)
        }
    }

    lateinit var getWishes: Flow<List<Wish>>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getWishes = wishRepository.getWishes()
        }
    }

    fun getWishById(id: Long): Flow<Wish> = wishRepository.getWishById(id)

    fun updateWish(wish: Wish) {
        viewModelScope.launch() {
            wishRepository.updateWish(wish = wish)
        }
    }

    fun deleteWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish = wish)
        }
    }

}

/*LATEINIT EXPLANATION
In Kotlin, lateinit is a modifier that can be applied to non-null properties of classes. It stands
for "late initialization" and is used when you know that a property will be initialized before its
first usage, thus avoiding the need for nullable types and potentially simplifying your code.

Here’s a breakdown of how lateinit works and its main characteristics:

**Initialization Timing**: With lateinit, you can declare a non-null property without providing an
initial value. Instead, you promise to initialize the property before accessing it for the first
time.

**Usage Requirements**:
-You can only use lateinit on mutable properties (var).
-It can't be used with properties that have custom getters or setters.
-Primitive types (like Int, Boolean, etc.) cannot be lateinit because they cannot be null, and
therefore, they must be initialized immediately.

**Initialization Process**:
-You need to ensure that the property is initialized with a value before accessing it, otherwise, a
LateinitPropertyAccessException will be thrown.
-Initialization typically occurs in the constructor, in an init block, or within another
method/function where you can ensure initialization before use.

**SUMMARY**
Overall, lateinit in Kotlin provides a convenient way to handle properties that are initialized
asynchronously or at a later stage in the object's lifecycle, helping to manage code complexity  and
improve performance by avoiding unnecessary null checks.
*/

/* DISPATCHERS EXPLANATION
In Kotlin, when we talk about **dispatchers**, we are usually referring to the mechanisms used for
managing and executing coroutines. Coroutines are lightweight threads that can suspend their
execution without blocking the underlying thread. Dispatchers are responsible for determining which
thread or threads the coroutine runs on. Here’s an overview of dispatchers in Kotlin:

Kotlin provides several built-in dispatchers in the kotlinx.coroutines library:

**Default**: This is the default dispatcher used when coroutines are launched without specifying a
dispatcher explicitly. It is optimized for typical, everyday operations, such as CPU-bound work or
performing asynchronous operations in UI-related code.

**IO**: This dispatcher is optimized for I/O-bound tasks, such as network or disk I/O operations.
It is suitable for tasks that do not consume much CPU time.

**Main**: This dispatcher is used for UI-related tasks in Android applications and UI frameworks
that support Kotlin coroutines. It ensures that coroutines run on the main UI thread.

**Unconfined**: This dispatcher starts the coroutine in the current call-stack context, but it does
not confine it to any specific thread afterwards. The coroutine will resume in whatever thread that
called its suspending function. It is useful when you need to switch between threads and execute
tasks in the current context.

**SUMMARY**
Dispatchers in Kotlin coroutines are essential for controlling where and how coroutines execute.
Choosing the right dispatcher ensures efficient use of resources and smooth operation of your
concurrent tasks, whether they are CPU-bound computations, I/O operations, or UI-related updates.
Understanding dispatchers allows you to write more efficient and responsive asynchronous code in
Kotlin.
*/