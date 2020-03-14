package it.tarczynski.library.ddd.event_store

import it.tarczynski.library.ddd.core.aggregate.Aggregate
import it.tarczynski.library.ddd.core.event.AggregateId
import it.tarczynski.library.ddd.core.event.DomainEvent
import kotlin.reflect.KClass

interface EventStore {

    fun <ID : AggregateId, A : Aggregate<A, ID>>
            persist(aggregate: A): A

    fun <ID : AggregateId, A : Aggregate<A, ID>>
            persistAll(aggregates: List<A>): List<A>

    fun <ID : AggregateId, A : Aggregate<A, ID>, E : DomainEvent<ID, A>>
            findAllBy(aggregateId: AggregateId): List<E>

    fun <ID : AggregateId, A : Aggregate<A, ID>, E : DomainEvent<ID, A>>
            findAllBy(clazz: KClass<E>): List<E>

}