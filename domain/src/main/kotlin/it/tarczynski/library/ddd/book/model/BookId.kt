package it.tarczynski.library.ddd.book.model

import it.tarczynski.library.ddd.core.event.AggregateId
import java.util.UUID

class BookId(value: UUID) : AggregateId(value)
