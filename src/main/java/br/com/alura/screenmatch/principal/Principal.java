package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    ConverteDados conversor = new ConverteDados();
    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    final String ENDERECO = "https://www.omdbapi.com/?t=";
    final String API_KEY = "&apikey=6585022c";

    public void exibeSerie() {
        System.out.println("Digite o nome da serie: ");
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
        //System.out.println("lista episodio X 'titulo'");
        for (var i = 0; i < dadosSerie.totalTemporadas(); i++) {
            List<DadosEpisodio> episodios = temp.get(i).episodios();
            for (var j = 0; j < episodios.size(); j++) {
                String titulo = episodios.get(j).titulo();
                //System.out.println("Episodio " + j + " " + titulo);
            } //OUTRA FORMA DE FAZER ABAIXO:
        }

        //temp.forEach(temporada -> temporada.episodios().forEach(episodio -> System.out.println("Episodio " + episodio.titulo())));
//----------------lista top 5 DadosEpisodio--------------------------------------------------
        // lista somente as avaliações
        temp.forEach(dadosTemporada -> dadosTemporada.episodios().forEach(episodio -> System.out.println(episodio.avaliacao())));

        // lista somente DadosEpisodio dentro da lista de DadosTemporada
        List<DadosEpisodio> dadosEpisodios = temp.stream()
                // flatMap possibilita trabalhar com listas dentro de listas
                .flatMap(temporada -> temporada.episodios().stream())
                // .collect(Collectors.toList()); // possibilita adicionar novos dadosEpisodio a lista
                .toList(); // diferente do collect o toList é uma lista imutável, ou seja, não deixa adicionar novos dadosEpisodio

        dadosEpisodios.forEach(System.out::println);

        // lista os 5 DadosEpisodio mais bem avaliados
            System.out.println("5 melhores episodios");
            dadosEpisodios.stream()
                    .filter(d -> !d.avaliacao().equalsIgnoreCase("n/a")) // filtra toda avaliação diferente de n/a
                    .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()) // ordena pela avaliação de forma decrescente (reversed)
                    .peek(d -> System.out.println("ordena: " + d)) // metodo para averiguar o que está sendo feito no código sem a necessidade de debugar
                    .map(dadosEpisodio -> dadosEpisodio.titulo().toUpperCase(Locale.ROOT))
                    .limit(5)
                    .forEach(System.out::println);

 //--------------Lista informações personalizadas de DadosTemporada e DadosEpisodio
            List<Episodio> episodio = temp.stream()
                    .flatMap(dadosEpisodio -> dadosEpisodio.episodios().stream() // pega a lista de DadosEpisodio dentro da lista DadosTemporada
                            .map(infoEpisodio -> new Episodio(dadosEpisodio.temporada(), infoEpisodio)) // para cada informação do episodio, cria um novo dado de Episodio
                    ).toList();
            System.out.println("lista Episodio");
          episodio.forEach(System.out::println);


        // ---------- Filtrar a busca episodios pela temporada
        System.out.println("digite a temporada desejada");
        var buscaTemporada = scanner.nextInt();
        scanner.nextLine();

        List<Episodio> filtraEpisodio = temp.stream()
                // filtrar (para cada temporada -> buscar todos os episódios da temporada filtrada)
                .filter(t -> t.temporada() == buscaTemporada)
                .flatMap(dadosEpisodio -> dadosEpisodio.episodios().stream() // pega a lista de DadosEpisodio dentro da lista DadosTemporada
                        .map(d -> new Episodio(dadosEpisodio.temporada(), d)))
                .toList();
        // filtraEpisodio.forEach(System.out::println);

        System.out.println("busca temp" + filtraEpisodio);

        //----------- Filtrar a busca por data
        List<Episodio> episodios1 = temp.stream()
                .flatMap(dadosTemporada -> dadosTemporada.episodios().stream()  // pega a lista de DadosEpisodio dentro da lista DadosTemporada
                        .map(dadosEpisodio -> new Episodio(dadosTemporada.temporada(), dadosEpisodio))) // mapeia dados dos episodios dentro da temporada
                .toList();

        System.out.println("a partir de que ano deseja buscar as temporadas?");
        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate data = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios1.stream()
                .filter(episodio1 -> episodio1.getDataLancamento().isAfter(data))
                .forEach(episodio1 -> System.out.println(
                        "Temporada: " + episodio1.getTemporada() +
                                " - Episodio: " + episodio1.getNumEpisodio() +
                                " - Titulo: " + episodio1.getTitulo() +
                                " - Data lancamento: " + episodio1.getDataLancamento().format(formatter)
                ));

        /* Embora o Optional seja um aliado útil, há algumas coisas que devem ser levadas em consideração para usá-lo de maneira eficaz:
         * Prefira o retorno Optional em vez de retornar null: Isso torna suas intenções claras e evita erros.
         * Não use Optional.get() sem Optional.isPresent(): O Optional.get() lançará um erro se o valor não estiver presente.
         * Portanto, é melhor verificar antes se o valor está presente.
         * Não use Optional para campos da classe ou parâmetros do método: O Optional deve ser usado principalmente para
         * retornos de métodos que podem não ter valor.
         * */

        //-------------BUSCA POR TITULO-------------
        System.out.println("Busca por titulo:");
        var buscaTitulo = scanner.nextLine();
        var episodioOptional = episodios1.stream()
                .filter(episodio3 -> episodio3.getTitulo().toUpperCase().contains(buscaTitulo.toUpperCase()))
                .peek(System.out::println)
                .collect(Collectors.toSet()).stream()
                .findFirst();

        episodioOptional.ifPresentOrElse(
                (value) -> {
                    System.out.println(
                            "Title is present, its: " + value);
                },
                () -> {
                    System.out.println("titulo nao encontrado");
                });

        // ------------ média das avaliacoes por temporada ---------------
        System.out.println("MEDIA DAS AVALIACOES");
        Map<Integer, Double> mediaAvalicoes = episodios1.stream()
                .filter(episodio3 -> episodio3.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(mediaAvalicoes);

        System.out.println("MEDIAS ESTATISTICAS");
        DoubleSummaryStatistics estatistica = episodios1.stream()
                        .filter(episodio3 -> episodio3.getAvaliacao() > 0)
                                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println(estatistica);
        System.out.println("episodio com maior nota: " + estatistica.getMax());

        scanner.close();

    }
}
