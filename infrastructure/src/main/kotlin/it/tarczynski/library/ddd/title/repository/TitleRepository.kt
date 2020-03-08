package it.tarczynski.library.ddd.title.repository

import it.tarczynski.library.ddd.event_store.EventStore
import it.tarczynski.library.ddd.title.model.Title
import org.springframework.stereotype.Repository

@Repository
class TitleRepository(private val eventStore: EventStore) : Titles {

    override fun save(title: Title): Title {
        return eventStore.persist(title)
    }

}
