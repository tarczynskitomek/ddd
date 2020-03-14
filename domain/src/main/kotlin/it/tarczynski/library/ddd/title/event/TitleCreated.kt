package it.tarczynski.library.ddd.title.event

import it.tarczynski.library.ddd.core.event.DomainEvent
import it.tarczynski.library.ddd.title.model.BookTitle
import it.tarczynski.library.ddd.title.model.ISSN
import it.tarczynski.library.ddd.title.model.Title
import it.tarczynski.library.ddd.title.model.TitleId

sealed class TitleEvent(titleId: TitleId) : DomainEvent<TitleId, Title>(titleId)

class TitleCreated(titleId: TitleId,
                   val title: BookTitle,
                   val bookCount: Int,
                   val description: String? = null,
                   val coverImageUrl: String? = null,
                   val issn: ISSN?) : TitleEvent(titleId) {
    override val type = "title.created"
    override fun applyTo(aggregate: Title) = aggregate.copy(
            title = title,
            issn = issn
    )

}