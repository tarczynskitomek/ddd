package it.tarczynski.library.ddd.books.repository

import it.tarczynski.library.ddd.book.model.Book
import it.tarczynski.library.ddd.book.model.BookId

interface Books {

    fun save(book: Book): Book

    fun saveAll(books: List<Book>): List<Book>

    fun findBy(bookId: BookId): Book

    fun findAll(): List<Book>
}