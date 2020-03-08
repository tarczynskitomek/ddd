package it.tarczynski.library.ddd.title.facade

import it.tarczynski.library.ddd.book.model.Book
import it.tarczynski.library.ddd.books.repository.Books
import it.tarczynski.library.ddd.title.model.CreateTitleRequest
import it.tarczynski.library.ddd.title.model.Title
import it.tarczynski.library.ddd.title.repository.Titles
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DefaultTitleFacade(private val titles: Titles,
                         private val books: Books) : TitleFacade {

    override fun createTitleWithBooks(request: CreateTitleRequest) {
        Title.from(request)
                .let { title -> titles.save(title) }
                .apply { createBooks(this, request.bookCount) }
    }

    private fun createBooks(title: Title, bookCount: Int) {
        val newBooks = mutableListOf<Book>()
        for (i in 1..bookCount) {
            val book = Book.initialize()
            book.addToTitle(title.id)
            newBooks.add(book)
        }
        books.saveAll(newBooks)
    }

}