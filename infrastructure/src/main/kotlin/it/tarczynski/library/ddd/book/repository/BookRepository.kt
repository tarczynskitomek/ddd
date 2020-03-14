package it.tarczynski.library.ddd.book.repository

import it.tarczynski.library.ddd.book.event.BookEvent
import it.tarczynski.library.ddd.book.model.Book
import it.tarczynski.library.ddd.book.model.BookId
import it.tarczynski.library.ddd.books.repository.Books
import it.tarczynski.library.ddd.event_store.EventStore
import org.springframework.stereotype.Repository

@Repository
class BookRepository(private val eventStore: EventStore) : Books {

    override fun save(book: Book): Book {
        return eventStore.persist(book)
    }

    override fun saveAll(books: List<Book>): List<Book> {
        return eventStore.persistAll(books)
    }

    override fun findBy(bookId: BookId): Book {
        return Book.from(bookId, eventStore.findAllBy(bookId))
    }

    override fun findAll(): List<Book> {
        return eventStore.findAllBy(BookEvent::class)
                .groupBy { it.id }
                .map { Book.from(it.key, it.value) }
    }
}