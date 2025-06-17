package org.giovanna.conversorDeMoedas;

import org.giovanna.conversorDeMoedas.services.CurrencyConverterService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Qual a sigla da moeda que você deseja converter para real?");

        while (true){

            Scanner sc = new Scanner(System.in);

            String sigla = sc.nextLine();

            CurrencyConverterService currencyConverterService = new CurrencyConverterService();

            if(!currencyConverterService.conditionKeep(sigla)){
                break;
            }

            currencyConverterService.callApiCurrency(sigla);

            currencyConverterService.printRetryMessage();

        }

        }
    }
