package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //ignorar todas as outras variáveis que não foram descritas abaixo

public record DadosSerie(@JsonAlias("Title") String titulo, //@JsonAlias("nome da variável da api do filme")
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao) {
}
