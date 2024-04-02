package br.com.alura.screenmatch.principal;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HackerRank {

    public static void main(String[] args) {
        LocalDate hoje = LocalDate.now();
        System.out.println(hoje);

        LocalDate aniversarioAlice = LocalDate.of(2005, Month.SEPTEMBER, 15);
        System.out.println(aniversarioAlice);

        int idade = hoje.getYear() - aniversarioAlice.getYear();
        System.out.println(idade);

        Period periodo = Period.between(hoje, aniversarioAlice);
        System.out.println(periodo);

        String pattern = "E, dd MMM yyyy HH:mm:ss z";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        System.out.println("formatado semana, dia mes, ano e hora " + date);

        //O local time representa um horário, seu formato padrão é hh:mm:ss.zzz.
        LocalTime hoje1 = LocalTime.now();
        System.out.println(hoje1);
        LocalTime personalizado = LocalTime.of(22, 33, 15);
        System.out.println("personalizado " + personalizado);

        //O localDateTime representa uma data com a hora, visto como ano-mês-dia-hora-minuto-segundo. O tempo é representado como yyyy-MM-dd-HH-mm-ss.zzz.
        LocalDateTime hojeDT = LocalDateTime.now();
        System.out.println("localdatetime" + hojeDT);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        System.out.println("data formatada " + hojeDT.format(formatador));

/*
* Letra	Componente data/hora	Exemplo
    G	Designador de era	    DC
    y	Ano	2010;               10
    M	Mês no ano	            Julho; jul; 10
    w	Semana no ano       	27
    W	Semana no mês	        2
    D	Dia no ano	            189
    d	Dia no mês	            10
    F	Dia da semana no mês	2
    E	Dia na semana	        Terça-feira; ter
    a	Marcador de am/pm      	PM
    H	Hora no dia (0-23)	    0
    k	Hora no dia (1-24)	    24
    K	Hora em am/pm (0-11)	0
    h	Hora em am/pm (1-12)	12
    m	Minuto na hora	        30
    s	Segundo no minuto   	55
    S	Fração de um segundo	978*/
    }


}










