package it.tarczynski.library.ddd.book.policy

import it.tarczynski.library.ddd.book.model.Book
import it.tarczynski.library.ddd.reader.model.ReaderId

interface BookBorrowingPolicy {
    fun validate(book: Book, readerId: ReaderId)
}
