package it.tarczynski.library.ddd.core.command.title

data class CreateTitleCommand(val title: String,
                              val issn: String?)
