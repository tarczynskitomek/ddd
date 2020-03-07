package it.tarczynski.library.ddd.title.model

data class CreateTitleRequest(val title: String, val bookCount: Int = 1, val issn: String? = null)