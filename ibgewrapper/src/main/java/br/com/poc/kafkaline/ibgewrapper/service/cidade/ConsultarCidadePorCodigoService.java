package br.com.poc.kafkaline.ibgewrapper.service.cidade;

import br.com.poc.kafkaline.ibgewrapper.gateway.feign.CidadeClient;
import br.com.poc.kafkaline.ibgewrapper.gateway.json.CidadeJson;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarCidadePorCodigoService {

    public List<CidadeJson> execute(String estado){
        CidadeClient cidadeClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(CidadeClient.class, "https://servicodados.ibge.gov.br");
        return cidadeClient.get(estado);
    }
}
