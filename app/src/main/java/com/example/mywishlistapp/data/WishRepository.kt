package com.example.mywishlistapp.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {

    //Create
    suspend fun addWish(wish: Wish) {
        wishDao.addWish(wish)
    }

    //Read
    fun getWishes(): Flow<List<Wish>> = wishDao.getAllWishes()

    /*
    Single-expression syntax (=) instead of a block body, example below. This is possible because
    Kotlin allows single-expression functions.

    Considerations:

    Readability: Both versions are quite readable. The single-expression functions be more concise
    and clear if the function is short and its purpose is simply to return the result of a call.

    Coding style: In Kotlin, it's considered good practice to use the single-expression form (=)
    when the function consists of a single expression. This can make the code cleaner and reduce
    visual clutter.
    */

    fun getWishById(id: Long): Flow<Wish> {
        return wishDao.getWishById(id)
    }

    //Update
    suspend fun updateWish(wish: Wish) {
        wishDao.updateWish(wish)
    }

    //Delete
    suspend fun deleteWish(wish: Wish) {
        wishDao.deleteWish(wish)
    }

}

/*REPOSITORY EXPLANATION
Using a **repository** in your MVVM (Model-View-ViewModel) Kotlin application offers several
benefits and helps in achieving a well-structured, maintainable, and testable codebase. Here are the
key reasons why using a repository is beneficial:

**Separation of Concerns**: The repository pattern helps separate different concerns within your
application. It acts as an intermediary between different data sources (like a local database,
network service, or cache) and the rest of your application components (ViewModels and UI). This
separation makes your codebase more modular and easier to understand.

**Promotes Code Reusability**: By encapsulating the logic for fetching and manipulating data inside
the repository, you can reuse these data access methods across different parts of your application.
This reduces code duplication and promotes a DRY (Don't Repeat Yourself) coding principle.

**Encapsulation of Data Operations**: The repository encapsulates the logic for interacting with
various data sources. This means that your ViewModel (or any other component) doesn't need to know
the details of how data is fetched, stored, or transformed. This separation of concerns makes it
easier to change or swap out data sources without affecting other parts of your application.

**Supports Unit Testing**: Repositories improve the testability of your code. Because business logic
and data access are separated, you can easily mock or stub repository methods in your unit tests.
This isolation allows you to test the behavior of your ViewModel or other components without needing
real data sources, making tests more predictable and reliable.

**Manages Complexity**: As your application grows, managing data access and ensuring consistency
across different parts of the app becomes more challenging. The repository pattern provides a
structured way to manage this complexity by centralizing data operations and enforcing clear
boundaries between layers of your application.

**Adheres to Single Responsibility Principle (SRP)**: Each component in your application should
ideally have a single responsibility. By offloading data access responsibilities to the repository,
your ViewModel can focus solely on handling UI-related logic and managing the UI state. This
separation improves code readability and maintainability.

In summary, using a repository in your MVVM Kotlin application helps you achieve separation of
concerns, promotes code reusability, encapsulates data operations, supports easier unit testing,
manages complexity as your app scales, and adheres to best practices like the Single Responsibility
Principle. These benefits contribute to building a robust, maintainable, and scalable application
architecture.
*/