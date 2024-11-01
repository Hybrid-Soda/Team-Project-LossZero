package losszero.losszero.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

import org.springframework.messaging.MessageChannel;

import java.util.UUID;

@EnableAsync
@Configuration
public class MqttConfig {

    private static final String[] topics = {"realtime-oper", "realtime-prod", "realtime-circ"};
//    private static final String brokerUrl = "tcp://k11e202.p.ssafy.io";
    private static final String brokerUrl = "tcp://localhost:1883";
    private static final String clientId = "SpringBoot-MQTT-Client-" + UUID.randomUUID();

    // MQTT 연결 옵션을 설정하는 객체 생성
    @Bean
    @ConfigurationProperties(prefix = "spring.mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }

    // MQTT 클라이언트를 생성하고 브로커에 연결
    @Bean
    public IMqttClient mqttClient() throws MqttException {
        IMqttClient mqttClient = new MqttClient(brokerUrl, clientId);
        mqttClient.connect(mqttConnectOptions());
        return mqttClient;
    }

    // MQTT Subscribe Channel
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    // MQTT 구독을 관리하고 메시지를 입력 채널로 전달하는 어댑터 생성
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(brokerUrl, clientId, topics);
        adapter.setCompletionTimeout(5000); // 연결 완료 대기 시간 설정 (불필요한 지연 방지 목적)
        adapter.setConverter(new DefaultPahoMessageConverter()); // MQTT 메시지 -> Spring Integration 메시지 방식 설정
        adapter.setQos(1); // QoS 설정
        adapter.setOutputChannel(mqttInputChannel()); // 메시지를 전달할 채널
        return adapter;
    }
}
