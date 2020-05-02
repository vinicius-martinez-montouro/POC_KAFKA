package br.com.poc.kafkaline.ibgewrapper.gateway.feign;

import br.com.poc.kafkaline.ibgewrapper.gateway.json.EstadoJson;
import feign.RequestLine;

import java.util.List;

public interface EstadoClient {
    //https://servicodados.ibge.gov.br/api/v1/localidades/estados
    @RequestLine("GET /api/v1/localidades/estados")
    List<EstadoJson> get();
}
