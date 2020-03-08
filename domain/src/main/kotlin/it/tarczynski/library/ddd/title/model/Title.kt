package it.tarczynski.library.ddd.title.model

import it.tarczynski.library.ddd.core.aggregate.Aggregate
import it.tarczynski.library.ddd.core.event.AggregateId
import it.tarczynski.library.ddd.core.event.DomainEvent
import it.tarczynski.library.ddd.title.event.TitleCreated
import it.tarczynski.library.ddd.title.event.TitleEvent
import java.util.*

data class Title(override val id: TitleId,
                 val title: BookTitle,
                 val issn: ISSN? = null,
                 private val titleEvents: List<TitleEvent> = listOf()) : Aggregate<Title, TitleId> {

    override val uncommittedEvents: List<DomainEvent<Title>>
        get() = titleEvents

    override fun commitEvents() = copy(titleEvents = listOf())

    companion object {

        @JvmStatic
        fun from(request: CreateTitleRequest): Title {
            val id = TitleId(UUID.randomUUID())
            val title = BookTitle(request.title)
            val issn = request.issn?.let { ISSN(it) }
            return Title(
                    id = id,
                    title = title,
                    issn = issn,
                    titleEvents = listOf(TitleCreated(id, title, request.bookCount, issn))
            )
        }

    }
}