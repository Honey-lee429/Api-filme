package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    ConverteDados conversor = new ConverteDados();
    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    final String ENDERECO = "https://www.omdbapi.com/?t=";
    final String API_KEY = "&apikey=6585022c";

    public void exibeSerie() {
        System.out.println("Busca: ");
        var busca = scanner.next();

        var json = consumoApi.obterDados(ENDERECO + busca.replace(" ", "+") + API_KEY);
        System.out.println(json);


//------------- lista temporada e dados da série ------------------------------------------
        var dadosSerie = conversor.obterDados(json, DadosSerie.class);
        var temp = new ArrayList<DadosTemporada>();
        for (var i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            json = consumoApi.obterDados(ENDERECO + busca.replace(" ", "+") + "&season=" + i + API_KEY);
            var dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            var add = temp.add(dadosTemporada);
        }
        //temp.forEach(System.out::println);

//----------------lista somente os títulos --------------------------------------------------
        /*for (var i = 0; i < dadosSerie.totalTemporadas(); i++) {
            List<DadosEpisodio> episodios = temp.get(i).episodios();
            for (var j = 0; j < episodios.size(); j++) {
                String titulo = episodios.get(j).titulo();
                System.out.println("Episodio " + j + " " + titulo);
            } OUTRA FORMA DE FAZER ABAIXO: */

        // temp.forEach(temporada -> temporada.episodios().forEach(episodio -> System.out.println("Episodio " + episodio.titulo())));

//----------------lista top 5 DadosEpisodio--------------------------------------------------
        //lista somente as avaliações
        temp.forEach(dadosTemporada -> dadosTemporada.episodios().forEach(dadosEpisodio -> dadosEpisodio.avaliacao()));

        // lista somente DadosEpisodio dentro da lista de DadosTemporada
        List<DadosEpisodio> dadosEpisodios = temp.stream()
                // flatMap possibilita trabalhar com listas dentro de listas
                .flatMap(temporada -> temporada.episodios().stream())
                // .collect(Collectors.toList()); // possibilita adicionar novos dadosEpisodio a lista
                .toList(); // diferente do collect o toList é uma lista imutável, ou seja, não deixa adicionar novos dadosEpisodio

        //dadosEpisodios.forEach(System.out::println);

        // lista os 5 DadosEpisodio mais bem avaliados
        dadosEpisodios.stream()
                .filter(d -> !d.avaliacao().equalsIgnoreCase("n/a")) // filtra toda avaliação diferente de n/a
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()) // classifica avaliação do meior para o menor (reversed)
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodio = temp.stream()
                .flatMap(dadosEpisodio -> dadosEpisodio.episodios().stream()
                        .map(infoEpisodio -> new Episodio(dadosEpisodio.temporada(), infoEpisodio))
                ).toList();

        episodio.forEach(System.out::println);

    }

}
