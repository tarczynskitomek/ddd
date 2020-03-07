package it.tarczynski.library.ddd.book.model

import it.tarczynski.library.ddd.book.event.*
import it.tarczynski.library.ddd.book.policy.BookBorrowingPolicy
import it.tarczynski.library.ddd.reader.model.ReaderId
import it.tarczynski.library.ddd.title.model.TitleId
import java.lang.IllegalStateException

data class Book private constructor(val id: BookId,
                                    val titleId: TitleId? = null,
                                    val status: Status = Status.INITIALIZED,
                                    private val bookEvents: List<BookEvent> = listOf()) {
    val uncommittedEvents: List<BookEvent>
        get() = bookEvents

    fun addToTitle(titleId: TitleId): Book {
        checkAlreadyAdded()
        return copy(
                titleId = titleId,
                status = Status.AVAILABLE,
                bookEvents = bookEvents + BookAddedToTitle(id, titleId)
        )
    }

    fun borrow(readerId: ReaderId, borrowingPolicy: BookBorrowingPolicy): Book {
        borrowingPolicy.validate(this, readerId)
        return copy(
                status = Status.BORROWED,
                bookEvents = bookEvents + BookBorrowed(id, readerId)
        )
    }

    fun markAsLost(readerId: ReaderId): Book {
        checkAlreadyLost()
        TODO()
    }

    fun markAsDestroyed(readerId: ReaderId): Book {
        checkAlreadyDestroyed()
        TODO()
    }

    fun markCommitted() = copy(bookEvents = listOf())

    private fun checkAlreadyDestroyed() {
        checkStatus("Book already marked as destroyed") { it is BookDestroyed }
    }

    private fun checkAlreadyLost() {
        checkStatus("Book already marked as lost") { it is BookLost }
    }

    private fun checkAlreadyAdded() {
        if (status != Status.INITIALIZED) {
            throw IllegalStateException("Book already added to title")
        }
    }

    private fun checkStatus(message: String, p: (BookEvent) -> Boolean) {
        bookEvents.find(p)?.let { throw IllegalStateException(message) }
    }

    companion object {

        @JvmStatic
        fun from(id: BookId, events: List<BookEvent>): Book {
            return events.fold(Book(id)) { book, event -> event.applyTo(book) }
        }

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