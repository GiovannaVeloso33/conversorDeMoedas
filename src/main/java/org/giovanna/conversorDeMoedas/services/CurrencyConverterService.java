package org.giovanna.conversorDeMoedas.services;

import org.giovanna.conversorDeMoedas.models.CurrencyModelDTO;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CurrencyConverterService {

    DecimalFormat f = new DecimalFormat("#.##");

    public void callApiCurrency(String sigla, Double valor) throws IOException, InterruptedException {

        String token = System.getenv("token");
        String url = "https://economia.awesomeapi.com.br/json/last/"+sigla.toUpperCase()+"?token="+token;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String json = response.body();

            JsonReader reader = Json.createReader(new StringReader(json));
            JsonObject root = reader.readObject();

            List<CurrencyModelDTO> currencyModelList = new ArrayList<>();

            for (String key : root.keySet()) {
                JsonObject obj = root.getJsonObject(key);

                CurrencyModelDTO currencyModel = new CurrencyModelDTO();

                currencyModel.setCode(obj.getString("code"));
                currencyModel.setCodein(obj.getString("codein"));
                currencyModel.setName(obj.getString("name"));
                currencyModel.setHigh(obj.getString("high"));
                currencyModel.setLow(obj.getString("low"));
                currencyModel.setVarBid(obj.getString("varBid"));
                currencyModel.setPctChange(obj.getString("pctChange"));
                currencyModel.setBid(obj.getString("bid"));
                currencyModel.setAsk(obj.getString("ask"));
                currencyModel.setTimestamp(obj.getString("timestamp"));
                currencyModel.setCreate_date(obj.getString("create_date"));


                currencyModelList.add(currencyModel);
            }

            for(CurrencyModelDTO currencyModelDTO : currencyModelList ){

                Double cotacao = Double.parseDouble(currencyModelDTO.getAsk());

                System.out.println("O valor da cotação de " + currencyModelDTO.getCode() +
                        " para " + currencyModelDTO.getCodein() + " é " + (f.format(cotacao)));

                calculateCoin(valor,cotacao);
            }
        } else {
            System.out.println("Erro na requisição: " + response.body());
        }

    }

    public void printRetryMessage(){
        System.out.println("Deseja consultar outra moeda? Caso sim, digite uma nova sigla, caso contrario digite sair");
    }

    public boolean conditionKeep(String condition){
        if(condition.equalsIgnoreCase("sair")){
            return false;
        }else{
            return true;
        }
    }

    public void calculateCoin(Double valor, Double cotacao){

        System.out.println("O valor da conversão é: " + f.format(valor/cotacao));
    }

}
