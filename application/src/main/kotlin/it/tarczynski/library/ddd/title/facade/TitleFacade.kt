package it.tarczynski.library.ddd.title.facade

import it.tarczynski.library.ddd.title.model.CreateTitleRequest

interface TitleFacade {

    fun createTitle(request: CreateTitleRequest): CreateTitleResponse

}