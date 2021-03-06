package it.tarczynski.library.ddd.book.event

import it.tarczynski.library.ddd.book.model.Book
import it.tarczynski.library.ddd.book.model.BookId
import it.tarczynski.library.ddd.core.event.DomainEvent
import it.tarczynski.library.ddd.reader.model.ReaderId
import it.tarczynski.library.ddd.title.model.TitleId

sealed class BookEvent(bookId: BookId) : DomainEvent<BookId, Book>(bookId)

class BookAddedToTitle(bookId: BookId, val titleId: TitleId) : BookEvent(bookId) {
    override val type = "book.added"
    override fun applyTo(aggregate: Book) = aggregate.copy(titleId = titleId, status = Book.Status.AVAILABLE)
}

class BookBorrowed(bookId: BookId, val readerId: ReaderId) : BookEvent(bookId) {
    override val type = "book.borrowed"
    override fun applyTo(aggregate: Book) = aggregate.copy(status = Book.Status.BORROWED)
}

class BookReturned(bookId: BookId, val readerId: ReaderId) : BookEvent(bookId) {
    override val type = "book.returned"
    override fun applyTo(aggregate: Book) = aggregate.copy(status = Book.Status.AVAILABLE)
}

class BookLost(bookId: BookId, val readerId: ReaderId) : BookEvent(bookId) {
    override val type = "book.lost"
    override fun applyTo(aggregate: Book) = aggregate.copy(status = Book.Status.LOST)
}

class BookDestroyed(bookId: BookId, val readerId: ReaderId) : BookEvent(bookId) {
    override val type = "book.destroyed"
    override fun applyTo(aggregate: Book) = aggregate.copy(status = Book.Status.DESTROYED)
}

class BookRemoved(bookId: BookId) : BookEvent(bookId) {
    override val type = "book.removed"
    override fun applyTo(aggregate: Book) = aggregate.copy(status = Book.Status.REMOVED)
}
