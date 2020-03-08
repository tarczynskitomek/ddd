package it.tarczynski.library.ddd.book.repository

import it.tarczynski.library.ddd.book.model.Book
import it.tarczynski.library.ddd.books.repository.Books
import it.tarczynski.library.ddd.event_store.EventStore
import org.springframework.stereotype.Repository

@Repository
class BookRepository(private val eventStore: EventStore) : Books {

    override fun save(book: Book): Book {
        return eventStore.persist(book)
    }

    override fun saveAll(books: MutableList<Book>): List<Book> {
        return eventStore.persistAll(books)
    }

}