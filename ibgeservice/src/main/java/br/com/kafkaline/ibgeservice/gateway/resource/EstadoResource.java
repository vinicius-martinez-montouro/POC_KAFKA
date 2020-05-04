package br.com.kafkaline.ibgeservice.gateway.resource;

import br.com.kafkaline.ibgeservice.gateway.json.CidadeList;
import br.com.kafkaline.ibgeservice.gateway.json.EstadoList;
import br.com.kafkaline.ibgeservice.service.estado.ConsultarCidadeService;
import br.com.kafkaline.ibgeservice.service.estado.ConsultarEstadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    private ConsultarEstadoService consultaEstadoService;

    @Autowired
    private ConsultarCidadeService consultarCidadeService;

    @GetMapping("/")
    public EstadoList consultarEstados() throws InterruptedException, ExecutionException, JsonProcessingException {
        return consultaEstadoService.execute();
    }
    @GetMapping("/{id}/cidades")
    public CidadeList consultarCidades(@PathVariable("id") String estado) throws InterruptedException, ExecutionException, JsonProcessingException {
        return consultarCidadeService.execute(estado);
    }

}
