package br.com.alura.screenmatch.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Getter
@Setter
@ToString
public class Episodio {
    private Integer temporada;
    private String titulo;
    private int numEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;


    public Episodio(Integer temporada, DadosEpisodio infoEpisodio) {
        this.temporada = temporada;
        this.titulo = infoEpisodio.titulo();
        this.numEpisodio = infoEpisodio.episodio();

        try {
            this.avaliacao = Double.valueOf(infoEpisodio.avaliacao());
        } catch (NumberFormatException ex) {
            this.avaliacao = 0.0;
        }

        try {
        this.dataLancamento = LocalDate.parse(infoEpisodio.lancamento());
        } catch (DateTimeParseException ex) {
            this.dataLancamento = null;
        }
    }
}
