package br.com.poc.kafkaline.ibgewrapper.gateway.http;

import br.com.poc.kafkaline.ibgewrapper.gateway.json.CidadeJson;
import br.com.poc.kafkaline.ibgewrapper.gateway.json.EstadoJson;
import br.com.poc.kafkaline.ibgewrapper.service.cidade.ConsultarCidadePorCodigoService;
import br.com.poc.kafkaline.ibgewrapper.service.estado.ConsultaEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    private ConsultaEstadoService consultaEstadoService;

    @Autowired
    private ConsultarCidadePorCodigoService consultarCidadePorCodigoService;

    @GetMapping("/")
    public List<EstadoJson> consultarEstados(){
        return consultaEstadoService.execute();
    }

    @GetMapping("/{id}/cidades/")
    public List<CidadeJson> consultarCidades(@PathVariable("id") String estado){
        return consultarCidadePorCodigoService.execute(estado);
    }
}
