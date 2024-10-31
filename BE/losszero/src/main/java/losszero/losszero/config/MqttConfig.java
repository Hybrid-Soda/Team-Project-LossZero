package losszero.losszero.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.scheduling.annotation.EnableAsync;

import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.UUID;

@EnableAsync
@Configuration
public class MqttConfig {

    private static final String topicFilter = "test";
    private final String brokerUrl = "tcp://k11e202.p.ssafy.io:1883";
    private static final String clientId = "SpringBoot-MQTT-Client-" + UUID.randomUUID();

    /**
     * MQTT 연결 옵션을 설정하는 객체를 생성
     * @return MqttConnectOptions 객체
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }

    /**
     * MQTT 클라이언트를 생성하고 브로커에 연결
     * @return 연결된 IMqttClient 객체
     * @throws MqttException MQTT 연결 중 예외가 발생할 경우
     */
    @Bean
    public IMqttClient mqttClient() throws MqttException {
        IMqttClient mqttClient = new MqttClient(brokerUrl, clientId);
        mqttClient.connect(mqttConnectOptions());
        return mqttClient;
    }

    /**
     * MQTT 메시지를 수신할 입력 채널을 생성
     * @return DirectChannel 객체
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * MQTT 구독을 관리하고 메시지를 입력 채널로 전달하는 어댑터 생성
     * @return MessageProducer로 설정된 MqttPahoMessageDrivenChannelAdapter 객체
     */
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                brokerUrl,
                clientId,
                topicFilter
        );
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     * MQTT 메시지 수신 시 메시지 처리 작업을 수행하는 핸들러 생성
     * @return MessageHandler 객체
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("MQTT MessageHandler : " + message.getPayload());
            }

        };
    }
}
