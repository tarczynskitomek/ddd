package it.tarczynski.library.ddd.book.model

import it.tarczynski.library.ddd.book.event.BookAdded
import it.tarczynski.library.ddd.title.model.TitleId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BookModelTest {

    @Test
    fun `from empty list should create Unitialized book`() {
        // given
        val bookId = BookId("id")

        // when
        val book = Book.from(bookId, emptyList())

        // then
        assertEquals(bookId, book.id)
        assertEquals(Book.Status.UNKNOWN, book.status)
    }

    @Test
    fun `add should set the book status to available`() {
        // given
        val book = Book.from(BookId("id"), emptyList())
        val titleId = TitleId("title-id")

        // when
        val updated = book.addToTitle(titleId)

        // then
        assertTrue(updated.bookEvents.any { it is BookAdded })
        assertEquals(Book.Status.AVAILABLE, updated.status)
    }
}