package br.com.kafkaline.ibgeservice.service.estado;

import br.com.kafkaline.ibgeservice.gateway.json.CidadeList;
import br.com.kafkaline.ibgeservice.gateway.json.EstadoList;
import br.com.kafkaline.ibgeservice.gateway.json.EstadoRequestTopicJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ConsultarCidadeService {

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    //Fila que vai ser enviado para o outro microservice
    @Value("${kafka.topic.request.topic-cidade")
    private String requestTopic;

    //Fila que vai ser guardado a resposta
    @Value("${kafka.topic.requestreply-topic-cidade}")
    private String requestReplyTopic;

    @Cacheable(value = "cidade principal")
    public CidadeList execute(String estado) throws JsonProcessingException, ExecutionException, InterruptedException {
        //Convertendo objeto para string
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(EstadoRequestTopicJson.builder().uf(estado).build());

        //Montando o producer que ira ser enviado para o kafka
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, jsonString);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

        //enviando
        RequestReplyFuture<String, String, String> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        //recebendo o retorno
        SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();

        CidadeList listJsonRetorn = mapper.readValue(consumerRecord.value(), CidadeList.class);

        return listJsonRetorn;
    }
}
