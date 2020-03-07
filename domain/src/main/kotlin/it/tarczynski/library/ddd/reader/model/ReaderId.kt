package it.tarczynski.library.ddd.reader.model

import it.tarczynski.library.ddd.core.event.AggregateId
import java.util.*

class ReaderId(value: UUID) : AggregateId(value)
