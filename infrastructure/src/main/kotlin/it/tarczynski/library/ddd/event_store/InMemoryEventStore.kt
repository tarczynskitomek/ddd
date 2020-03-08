package it.tarczynski.library.ddd.event_store

import it.tarczynski.library.ddd.core.aggregate.Aggregate
import it.tarczynski.library.ddd.core.event.AggregateId
import it.tarczynski.library.ddd.core.event.DomainEvent
import org.springframework.stereotype.Component

@Component
class InMemoryEventStore : EventStore {
    val persistedEvents: MutableMap<AggregateId, List<DomainEvent<*>>?> = mutableMapOf()

    override fun <A : Aggregate<out A, out AggregateId>> persist(aggregate: A): A {
        val uncommitedEvents = aggregate.uncommittedEvents
        persistedEvents.merge(aggregate.id, listOf()) { acc, _ -> acc + uncommitedEvents }
        println(persistedEvents)
        return aggregate.commitEvents()
    }

    override fun <A : Aggregate<out A, out AggregateId>> persistAll(aggregates: List<A>): List<A> {
        return aggregates.map { persist(it) }
    }

}