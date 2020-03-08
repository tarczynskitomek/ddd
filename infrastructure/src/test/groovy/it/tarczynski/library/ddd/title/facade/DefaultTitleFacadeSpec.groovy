package it.tarczynski.library.ddd.title.facade

import it.tarczynski.library.ddd.book.repository.BookRepository
import it.tarczynski.library.ddd.books.repository.Books
import it.tarczynski.library.ddd.event_store.InMemoryEventStore
import it.tarczynski.library.ddd.title.model.CreateTitleRequest
import it.tarczynski.library.ddd.title.repository.TitleRepository
import it.tarczynski.library.ddd.title.repository.Titles
import spock.lang.Specification

class DefaultTitleFacadeSpec extends Specification {

    private InMemoryEventStore eventStore = new InMemoryEventStore()
    private Titles titles = Spy(new TitleRepository(eventStore))
    private Books books = Spy(new BookRepository(eventStore))

    private TitleFacade titleFacade = new DefaultTitleFacade(titles, books)

    def "createTitleWithBooks"() {
        given:
            def request = new CreateTitleRequest("the title", 1, "iss")

        when:
            titleFacade.createTitleWithBooks(request)

        then:
            eventStore.persistedEvents.size() == 2
    }
}
