package it.tarczynski.library.ddd.book.model

import it.tarczynski.library.ddd.book.event.BookAddedToTitle
import it.tarczynski.library.ddd.book.policy.BookBorrowingPolicy
import spock.lang.Unroll

import static it.tarczynski.library.ddd.book.model.Book.Status.*

class BookSpec extends BookSpecFixture {

    def bookBorrowingPolicy = Mock(BookBorrowingPolicy)

    @Unroll
    def "from should return Book based on the events"() {
        given:
            'none'
        when:
            def book = Book.from(bookId, events)
        then:
            book.id == bookId
            book.status == expectedStatus
        where:
            events                                                                 || expectedStatus
            []                                                                     || INITIALIZED
            [bookAddedToTitle]                                                     || AVAILABLE
            [bookAddedToTitle, bookBorrowed]                                       || BORROWED
            [bookAddedToTitle, bookBorrowed, bookReturned]                         || AVAILABLE
            [bookAddedToTitle, bookBorrowed, bookReturned, bookBorrowed, bookLost] || LOST
    }

    def "addBookToTitle should set the book status to available"() {
        given:
            def book = initialized()
        when:
            def updatedBook = book.addToTitle(titleId)
        then:
            updatedBook.uncommittedEvents.any { it instanceof BookAddedToTitle && it.id == book.id }
            updatedBook.status == AVAILABLE
    }

    def "addBookToTitle should throw if book is already added to title"() {
        given:
            def alreadyAdded = addedToTitle()
        when:
            alreadyAdded.addToTitle(titleId)
        then:
            thrown(IllegalStateException)
    }

    def "borrow should set the book status to borrowed"() {
        given:
            def book = addedToTitle()
        when:
            def borrowedBook = book.borrow(readerId, bookBorrowingPolicy)
        then:
            borrowedBook.status == BORROWED
    }

}
