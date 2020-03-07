package it.tarczynski.library.ddd.book.event

import it.tarczynski.library.ddd.book.model.Book
import it.tarczynski.library.ddd.book.model.BookId
import it.tarczynski.library.ddd.reader.model.ReaderId
import it.tarczynski.library.ddd.title.model.TitleId
import java.time.LocalDateTime
import java.util.*

sealed class BookEvent(val bookId: BookId) {
    private val eventId = UUID.randomUUID()
    val timestamp: LocalDateTime = LocalDateTime.now()
    abstract fun applyTo(book: Book): Book

    override fun equals(other: Any?): Boolean {
        return other is BookEvent
                && this.eventId == other.eventId
    }

    override fun hashCode(): Int {
        return eventId.hashCode() * 31
    }
}

class BookAdded(bookId: BookId, val titleId: TitleId) : BookEvent(bookId) {
    override fun applyTo(book: Book) = book.copy(titleId = titleId, status = Book.Status.AVAILABLE)
}

class BookBorrowed(bookId: BookId, val readerId: ReaderId) : BookEvent(bookId) {
    override fun applyTo(book: Book) = book.copy(status = Book.Status.BORROWED)
}

class BookReturned(bookId: BookId, val readerId: ReaderId) : BookEvent(bookId) {
    override fun applyTo(book: Book) = book.copy(status = Book.Status.RETURNED)
}

class BookLost(bookId: BookId, val readerId: ReaderId) : BookEvent(bookId) {
    override fun applyTo(book: Book) = book.copy(status = Book.Status.LOST)
}

class BookDestroyed(bookId: BookId, val readerId: ReaderId) : BookEvent(bookId) {
    override fun applyTo(book: Book) = book.copy(status = Book.Status.DESTROYED)
}
