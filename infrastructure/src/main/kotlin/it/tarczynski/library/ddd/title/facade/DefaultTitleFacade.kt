package it.tarczynski.library.ddd.title.facade

import it.tarczynski.library.ddd.title.model.CreateTitleRequest
import it.tarczynski.library.ddd.title.model.Title
import it.tarczynski.library.ddd.title.repository.Titles
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DefaultTitleFacade(private val titles: Titles) : TitleFacade {

    override fun createTitle(request: CreateTitleRequest): CreateTitleResponse {
        Title.from(request)
                .let { titles.save(it) } // to chyba nie jest konieczne, ponieważ wcześniej możemy oddelegować sprawdzanie
                // czy można książkę utworzyć
                // zrobić event o tym, że utworzono
                // zapisać event i jak nie wybuchnie - opublikowaać, wszystko w jednej transakcji
                // odpowiedzieć - 201 CREATED
                .let {  }
        TODO()
    }

}