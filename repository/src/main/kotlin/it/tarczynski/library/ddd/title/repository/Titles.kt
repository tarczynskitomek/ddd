package it.tarczynski.library.ddd.title.repository

import it.tarczynski.library.ddd.title.model.Title

interface Titles {

    fun save(title: Title): Title

}