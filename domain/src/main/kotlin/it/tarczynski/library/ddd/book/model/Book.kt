package it.tarczynski.library.ddd.book.model

import it.tarczynski.library.ddd.book.event.*
import it.tarczynski.library.ddd.book.policy.BookBorrowingPolicy
import it.tarczynski.library.ddd.reader.model.ReaderId
import it.tarczynski.library.ddd.title.model.TitleId
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

data class Book private constructor(val id: BookId,
                                    val titleId: TitleId? = null,
                                    val status: Status = Status.UNKNOWN,
                                    val bookEvents: List<BookEvent> = mutableListOf()) {
    private val events: MutableList<BookEvent>
        get() = bookEvents as MutableList<BookEvent>

    fun addToTitle(titleId: TitleId): Book {
        checkAlreadyAdded()
        events.add(BookAdded(id, titleId))
        return copy(titleId = titleId, status = Status.AVAILABLE)
    }

    fun borrow(readerId: ReaderId, borrowingPolicy: BookBorrowingPolicy): Book {
        borrowingPolicy.validate(this, readerId)
        events.add(BookBorrowed(id, readerId))
        return copy(status = Status.BORROWED)
    }

    fun markAsLost(readerId: ReaderId): Book {
        checkAlreadyLost()
        TODO()
    }

    fun markAsDestroyed(readerId: ReaderId): Book {
        checkAlreadyDestroyed()
        TODO()
    }

    private fun checkAlreadyDestroyed() {
        checkStatus("Book already marked as destroyed") { it is BookDestroyed }
    }

    private fun checkAlreadyLost() {
        checkStatus("Book already marked as lost") { it is BookLost }
    }

    private fun checkAlreadyAdded() {
        checkStatus("Book already added!") { it is BookAdded }
    }

    private fun checkStatus(message: String, p: (BookEvent) -> Boolean) {
        bookEvents.find(p)?.let { throw IllegalStateException(message) }
    }

    companion object {

        fun from(id: BookId, events: List<BookEvent>): Book {
            return events.fold(Book(id)) { book, event -> event.applyTo(book) }
        }

    }

    enum class Status(val description: String = "") {
        UNKNOWN("The book is unknown - hasn't been added to the library"),
        AVAILABLE,
        BORROWED,
        RETURNED,
        LOST,
        DESTROYED,
        REMOVED
    }

}