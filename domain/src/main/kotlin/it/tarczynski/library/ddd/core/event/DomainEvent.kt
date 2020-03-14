package it.tarczynski.library.ddd.core.event

import it.tarczynski.library.ddd.core.aggregate.Aggregate
import java.time.LocalDateTime
import java.util.*

open class AggregateId(val uuid: UUID) {

    override fun equals(other: Any?): Boolean {
        return other is AggregateId && other.uuid == this.uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode() * 31
    }

    override fun toString(): String {
        return uuid.toString()
    }

}

abstract class DomainEvent<ID: AggregateId, A : Aggregate<A, ID>>(
        val id: ID,
        val occurred: LocalDateTime = LocalDateTime.now()) {

    abstract val type: String
    abstract fun applyTo(aggregate: A): A

    override fun equals(other: Any?): Boolean {
        return other is DomainEvent<*, *> && other.id == id && other.occurred == occurred
    }

    override fun hashCode(): Int {
        return occurred.hashCode() + id.hashCode() * 31
    }

}
