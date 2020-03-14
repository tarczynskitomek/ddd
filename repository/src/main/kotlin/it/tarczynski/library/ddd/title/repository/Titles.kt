package it.tarczynski.library.ddd.title.repository

import it.tarczynski.library.ddd.title.model.Title
import it.tarczynski.library.ddd.title.model.TitleId

interface Titles {

    fun save(title: Title): Title

    fun findBy(titleId: TitleId): Title

}