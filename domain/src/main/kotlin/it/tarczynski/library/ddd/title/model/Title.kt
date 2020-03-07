package it.tarczynski.library.ddd.title.model

data class Title(val id: TitleId,
                 val title: BookTitle,
                 val issn: ISSN) {


}