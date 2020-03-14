package it.tarczynski.library.ddd.book.model

import it.tarczynski.library.ddd.book.event.*
import it.tarczynski.library.ddd.book.policy.BookBorrowingPolicy
import it.tarczynski.library.ddd.core.aggregate.Aggregate
import it.tarczynski.library.ddd.reader.model.ReaderId
import it.tarczynski.library.ddd.title.model.TitleId
import java.lang.IllegalStateException
import java.util.*

data class Book private constructor(override val id: BookId,
                                    val titleId: TitleId? = null,
                                    val status: Status = Status.INITIALIZED,
                                    private val bookEvents: MutableList<BookEvent> = mutableListOf()) : Aggregate<Book, BookId> {

    override val uncommittedEvents: List<BookEvent>
        get() = bookEvents

    fun addToTitle(titleId: TitleId): Book {
        checkAlreadyAdded()
        val added = BookAddedToTitle(id, titleId)
        bookEvents.add(added)
        return added.applyTo(this)
    }

    fun borrow(readerId: ReaderId, borrowingPolicy: BookBorrowingPolicy): Book {
        borrowingPolicy.validate(this, readerId)
        val bookBorrowed = BookBorrowed(id, readerId)
        bookEvents.add(bookBorrowed)
        return bookBorrowed.applyTo(this)
    }

    fun markAsLost(readerId: ReaderId): Book {
        checkAlreadyLost()
        val lost = BookLost(id, readerId)
        bookEvents.add(lost)
        return lost.applyTo(this)
    }

    fun markAsDestroyed(readerId: ReaderId): Book {
        checkAlreadyDestroyed()
        val destroyed = BookDestroyed(id, readerId)
        bookEvents.add(destroyed)
        return destroyed.applyTo(this
        )
    }

    fun removeBook(): Book {
        checkAlreadyRemoved()
        return copy(
                status = Status.REMOVED
        )
    }

    override fun commitEvents() = copy()

    private fun checkAlreadyDestroyed() {
        if (status == Status.DESTROYED)
            throw IllegalStateException("Book already marked as destroyed")
    }

    private fun checkAlreadyLost() {
        if (status == Status.LOST)
            throw IllegalStateException("Book already marked as LOST")
    }

    private fun checkAlreadyAdded() {
        if (status != Status.INITIALIZED)
            throw IllegalStateException("Book already added to title")
    }

    private fun checkAlreadyRemoved() {
        if (status != Status.REMOVED)
            throw IllegalStateException("Book already removed")
    }

    companion object {

        @JvmStatic
        fun from(id: BookId, events: List<BookEvent>): Book {
            return events.fold(Book(id)) { book, event -> event.applyTo(book) }
        }

        @JvmStatic
        fun initialize() = Book(BookId(UUID.randomUUID()))

    }

    enum class Status {
        INITIALIZED,
        AVAILABLE,
        BORROWED,
        LOST,
        DESTROYED,
        REMOVED
    }

}