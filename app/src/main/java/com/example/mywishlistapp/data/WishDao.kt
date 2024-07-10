package com.example.mywishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao {

    //Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addWish(wishEntity: Wish)

    //Read
    @Query("SELECT * from 'wish-table'")
    abstract fun getAllWishes(): Flow<List<Wish>>

    @Query("SELECT * from 'wish-table' where id=:id")
    abstract fun getWishById(id: Long): Flow<Wish>

    //Update
    @Update
    abstract suspend fun updateWish(wishEntity: Wish)

    //Delete
    @Delete
    abstract suspend fun deleteWish(wishEntity: Wish)

}

/*DAO EXPLANATION
In Kotlin, DAO stands for **Data Access Object**. It's a design pattern used to abstract and
encapsulate the access to a data source such as a database or a web service. The DAO pattern helps
separate business logic from data access logic by providing an interface through which data
manipulation operations are performed.

Here are key points about DAOs in Kotlin:

**Purpose**: DAOs provide a centralized interface to interact with a specific data source. They
typically define methods for CRUD operations (Create, Read, Update, Delete) related to a particular
entity or data model.

**Implementation**: In Kotlin, DAOs are often implemented using interfaces or abstract classes.
These interfaces declare the operations that can be performed on the data source, such as inserting,
querying, updating, or deleting records.

**Example Usage**: For instance, if you have an application that stores `User` objects in a local
SQLite database, you might define a `UserDao` interface with methods like `insertUser`,
`getUserById`, `updateUser`, and `deleteUser`. Implementations of these methods would handle the
actual SQL queries or database operations.

**Framework Integration**: DAOs are commonly used in conjunction with ORM (Object-Relational
Mapping) frameworks or database libraries in Kotlin, such as Room for Android apps or Exposed for
Kotlin/JVM projects. These frameworks provide annotations or code generation tools to simplify the
implementation of DAOs and their integration with the underlying data storage.

**Benefits**: Using DAOs promotes code reusability, improves maintainability by separating concerns
(business logic vs. data access), and facilitates unit testing. DAOs also help enforce good design
practices such as the Single Responsibility Principle by focusing each DAO on a specific entity or
data type.

In summary, a DAO in Kotlin is a crucial component for managing data access operations in an
organized and efficient manner, making it easier to interact with databases or other data sources
while maintaining a clear separation of concerns within your application architecture.
*/