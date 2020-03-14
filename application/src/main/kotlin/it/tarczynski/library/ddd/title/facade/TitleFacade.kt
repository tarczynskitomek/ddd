package it.tarczynski.library.ddd.title.facade

import it.tarczynski.library.ddd.core.TitleWithBooks
import it.tarczynski.library.ddd.title.model.CreateTitleRequest

interface TitleFacade {

    fun createTitleWithBooks(request: CreateTitleRequest): TitleWithBooks

}