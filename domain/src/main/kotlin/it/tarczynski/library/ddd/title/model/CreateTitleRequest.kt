package it.tarczynski.library.ddd.title.model

data class CreateTitleRequest(val title: String,
                              val bookCount: Int = 1,
                              val description: String? = null,
                              val coverImageUrl: String? = null,
                              val issn: String? = null)