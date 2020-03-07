package it.tarczynski.library.ddd.book.model

import it.tarczynski.library.ddd.book.event.BookAddedToTitle
import it.tarczynski.library.ddd.book.event.BookBorrowed
import it.tarczynski.library.ddd.book.event.BookLost
import it.tarczynski.library.ddd.book.event.BookReturned
import it.tarczynski.library.ddd.reader.model.ReaderId
import it.tarczynski.library.ddd.title.model.TitleId
import spock.lang.Specification

class BookSpecFixture extends Specification {
    static def bookId = new BookId(UUID.randomUUID())
    static def titleId = new TitleId(UUID.randomUUID())
    static def readerId = new ReaderId(UUID.randomUUID())

    static def bookAddedToTitle = new BookAddedToTitle(bookId, titleId)
    static def bookBorrowed = new BookBorrowed(bookId, readerId)
    static def bookReturned = new BookReturned(bookId, readerId)
    static def bookLost = new BookLost(bookId, readerId)

    static Book initialized() {
        Book.from(new BookId(UUID.randomUUID()), [])
    }

    static Book addedToTitle() {
        Book.from(new BookId(UUID.randomUUID()), [bookAddedToTitle])
    }

    static Book borrowed() {
        Book.from(new BookId(UUID.randomUUID()), [bookAddedToTitle, bookBorrowed])
    }

}
