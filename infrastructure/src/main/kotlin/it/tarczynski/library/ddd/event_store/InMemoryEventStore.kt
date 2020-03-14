package it.tarczynski.library.ddd.event_store

import it.tarczynski.library.ddd.core.aggregate.Aggregate
import it.tarczynski.library.ddd.core.event.AggregateId
import it.tarczynski.library.ddd.core.event.DomainEvent
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class InMemoryEventStore : EventStore {
    private val persistedEvents = mutableMapOf<AggregateId, List<DomainEvent<*, *>>>()

    override fun <ID : AggregateId, A : Aggregate<A, ID>>
            persist(aggregate: A): A {
        val uncommitedEvents = aggregate.uncommittedEvents
        persistedEvents[aggregate.id] = persistedEvents.getOrDefault(aggregate.id, listOf()) + uncommitedEvents
        return aggregate.commitEvents()
    }

    override fun <ID : AggregateId, A : Aggregate<A, ID>>
            persistAll(aggregates: List<A>): List<A> {
        return aggregates.map { persist(it) }
    }

    override fun <ID : AggregateId, A : Aggregate<A, ID>, E : DomainEvent<ID, A>>
            findAllBy(aggregateId: AggregateId): List<E> {
        return persistedEvents[aggregateId] as List<E>
    }

    override fun <ID : AggregateId, A : Aggregate<A, ID>, E : DomainEvent<ID, A>>
            findAllBy(clazz: KClass<E>): List<E> {
        return persistedEvents.values.filter { it.any { domainEvent -> clazz.isInstance(domainEvent) } }
                .flatten() as List<E>
    }


}