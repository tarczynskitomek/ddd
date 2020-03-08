package it.tarczynski.library.ddd.event_store

import it.tarczynski.library.ddd.core.aggregate.Aggregate
import it.tarczynski.library.ddd.core.event.AggregateId

interface EventStore {

    fun <A : Aggregate<out A, out AggregateId>> persist(aggregate: A): A

    fun <A : Aggregate<out A, out AggregateId>> persistAll(aggregates: List<A>): List<A>

}