package org.giovanna.conversorDeMoedas;

import org.giovanna.conversorDeMoedas.services.CurrencyConverterService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Qual a sigla da moeda que você deseja converter?");
        System.out.println("Exemplo: BRL-USD,CAD-USD");

        while (true){

            CurrencyConverterService currencyConverterService = new CurrencyConverterService();

            Scanner sc = new Scanner(System.in);

            String sigla = sc.nextLine();

            if(!currencyConverterService.conditionKeep(sigla)){
                break;
            }

            System.out.println("Qual seria o valor?");

            Double valor = sc.nextDouble();


            currencyConverterService.callApiCurrency(sigla, valor);

            currencyConverterService.printRetryMessage();

        }

        }
    }
