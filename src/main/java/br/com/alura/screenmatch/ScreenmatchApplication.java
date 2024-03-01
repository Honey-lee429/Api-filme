package br.com.alura.screenmatch;

import br.com.alura.screenmatch.principal.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@SpringBootApplication
public class ScreenmatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);

        Principal principal = new Principal();
        principal.exibeSerie();

        String[] nomes = {"mari", "hanna", "norma", "giovanna", "hanely"};
        Arrays.stream(nomes)
                .sorted()
                .limit(2)
                .filter(n -> n.startsWith("h"))
                .map(n -> n.toUpperCase(Locale.ROOT))
                .forEach(System.out::println);
        // -------------------------------------------------------------------------------------------------------

        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> numerosPares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList()); //Collect: permite coletar os elementos da stream em uma coleção ou em
        // outro tipo de dado. Por exemplo, podemos coletar os números pares em um conjunto.

        System.out.println(numerosPares); // Output: [2, 4, 6, 8, 10]

// -------------------------------------------------------------------------------------------------------
        List<String> palavras = Arrays.asList("Java", "Stream", "Operações", "Intermediárias");

        List<Integer> tamanhos = palavras.stream()
                .map(s -> s.length())  // Map: permite transformar cada elemento da stream em outro tipo de dado.
                // Por exemplo, podemos transformar uma lista de strings em uma lista de seus respectivos tamanhos.
                .collect(Collectors.toList());

        System.out.println(tamanhos); // Output: [4, 6, 11, 17]


// -------------------------------------------------------------------------------------------------------

            /*var consumoApi = new ConsumoApi();
            var json = consumoApi.obterDados("https://www.omdbapi.com/?t=friends&episodes=1&apikey=6585022c");
//		json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
            System.out.println(json);

            ConverteDados conversor = new ConverteDados();

            var dadosSerie = conversor.obterDados(json, DadosSerie.class);
            System.out.println(dadosSerie);

            json = consumoApi.obterDados("https://www.omdbapi.com/?t=friends&episode=1&season=1&apikey=6585022c");
            var dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
            System.out.println(dadosEpisodio);

            var temp = new ArrayList<DadosTemporada>();
            for (var i = 1; i <= dadosSerie.totalTemporadas(); i++) {
                json = consumoApi.obterDados("https://www.omdbapi.com/?t=friends&season=" + i + "&apikey=6585022c");
                var dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                System.out.println(dadosTemporada);
                var add = temp.add(dadosTemporada);
            }
            System.out.println("Lista: " + temp);
            temp.forEach(System.out::println);*/
    }
}

