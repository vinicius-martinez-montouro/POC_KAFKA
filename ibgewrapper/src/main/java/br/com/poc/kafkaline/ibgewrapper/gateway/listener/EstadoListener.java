package br.com.poc.kafkaline.ibgewrapper.gateway.listener;

import br.com.poc.kafkaline.ibgewrapper.gateway.json.EstadoJson;
import br.com.poc.kafkaline.ibgewrapper.gateway.json.EstadoList;
import br.com.poc.kafkaline.ibgewrapper.service.estado.ConsultaEstadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoListener {

    @Autowired
    private ConsultaEstadoService consultaEstadoService;

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    public Message<String> execute(@Header(KafkaHeaders.REPLY_TOPIC) byte[] replyTo,
                                   @Header(KafkaHeaders.CORRELATION_ID) byte[] correlation) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        List<EstadoJson> listEstados = consultaEstadoService.execute();

        String jsonReturn = mapper.writeValueAsString(EstadoList.builder().estadoJsonList(listEstados).build());

        return MessageBuilder.withPayload(jsonReturn)
                .setHeader(KafkaHeaders.TOPIC, replyTo)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlation)
                .build();
    }
}
