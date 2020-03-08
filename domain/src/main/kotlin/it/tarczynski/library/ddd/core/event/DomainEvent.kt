package it.tarczynski.library.ddd.core.event

import it.tarczynski.library.ddd.core.aggregate.Aggregate
import java.time.LocalDateTime
import java.util.*

open class AggregateId(val uuid: UUID)

abstract class DomainEvent<A : Aggregate<A, out AggregateId>>(val id: AggregateId,
                                                              val occurred: LocalDateTime = LocalDateTime.now()) {

    abstract fun applyTo(aggregate: A): A
    abstract val type: String

    override fun equals(other: Any?): Boolean {
        return other is DomainEvent<*> && other.id == id && other.occurred == occurred
    }

    override fun hashCode(): Int {
        return occurred.hashCode() + id.hashCode() * 31
    }

}
