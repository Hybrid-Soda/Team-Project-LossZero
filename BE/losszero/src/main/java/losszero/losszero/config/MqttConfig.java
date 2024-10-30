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
    private final String brokerUrl = "tcp://localhost:1883";
    private static final String clientId = "SpringBoot-MQTT-Client-" + UUID.randomUUID();

    @Bean
    @ConfigurationProperties(prefix = "spring.mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }

    @Bean
    public IMqttClient mqttClient() throws MqttException {
        IMqttClient mqttClient = new MqttClient(brokerUrl, clientId);
        mqttClient.connect(mqttConnectOptions());
        return mqttClient;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

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
