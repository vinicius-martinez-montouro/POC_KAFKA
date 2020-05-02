package br.com.poc.kafkaline.ibgewrapper.service.estado;

import br.com.poc.kafkaline.ibgewrapper.gateway.feign.EstadoClient;
import br.com.poc.kafkaline.ibgewrapper.gateway.json.EstadoJson;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ConsultaEstadoService {

    public List<EstadoJson> execute(){
        EstadoClient estadoClient = Feign.builder()
                                    .decoder(new GsonDecoder())
                                    .target(EstadoClient.class, "https://servicodados.ibge.gov.br");

        return estadoClient.get();
    }

}
