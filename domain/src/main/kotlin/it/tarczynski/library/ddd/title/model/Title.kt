package it.tarczynski.library.ddd.title.model

data class Title(val title: BookTitle,
                 val id: TitleId? = null,
                 val issn: ISSN? = null) {

    companion object {

        @JvmStatic
        fun from(request: CreateTitleRequest): Title {
            return Title(
                    title = BookTitle(request.title),
                    issn = request.issn?.let { ISSN(it) }
            )
        }

    }
}