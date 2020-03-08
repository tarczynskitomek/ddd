package it.tarczynski.library.ddd.books.repository

import it.tarczynski.library.ddd.book.model.Book

interface Books {

    fun save(book: Book): Book

    fun saveAll(newBooks: MutableList<Book>): List<Book>

}