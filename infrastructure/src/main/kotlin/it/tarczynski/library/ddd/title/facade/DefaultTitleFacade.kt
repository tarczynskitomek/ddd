package it.tarczynski.library.ddd.title.facade

import it.tarczynski.library.ddd.book.model.Book
import it.tarczynski.library.ddd.books.repository.Books
import it.tarczynski.library.ddd.core.TitleWithBooks
import it.tarczynski.library.ddd.title.model.CreateTitleRequest
import it.tarczynski.library.ddd.title.model.Title
import it.tarczynski.library.ddd.title.repository.Titles
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DefaultTitleFacade(private val titles: Titles,
                         private val books: Books) : TitleFacade {

    override fun createTitleWithBooks(request: CreateTitleRequest): TitleWithBooks {
        return Title.from(request)
                .let { title -> titles.save(title) }
                .let { createBooks(it, request.bookCount) }
    }

    private fun createBooks(title: Title, bookCount: Int): TitleWithBooks {
        val newBooks = mutableListOf<Book>()
        for (i in 1..bookCount) {
            newBooks.add(Book.initialize().addToTitle(title.id))
        }
        return TitleWithBooks(title, books.saveAll(newBooks))
    }

}