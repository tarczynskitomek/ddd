package it.tarczynski.library.ddd.core.aggregate

import it.tarczynski.library.ddd.core.event.AggregateId
import it.tarczynski.library.ddd.core.event.DomainEvent

interface Aggregate<A : Aggregate<A, ID>, ID : AggregateId> {
    fun commitEvents(): A
    val id: ID
    val uncommittedEvents: List<DomainEvent<ID, A>>
}
