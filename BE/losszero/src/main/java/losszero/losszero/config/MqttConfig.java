//package losszero.losszero.config;
//
//import org.eclipse.paho.client.mqttv3.IMqttClient;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.messaging.MessageChannel;
//
//@Configuration
//public class MqttConfig {
//
//    private final String subTopic = "test";
//
//    @Bean
//    @ConfigurationProperties(prefix = "mqtt")
//    public MqttConnectOptions mqttConnectOptions() {
//        return new MqttConnectOptions();
//    }
//
//    @Bean
//    public IMqttClient mqttClient(@Value("${mqtt.clientId}") String clientId,
//                                  @Value("${mqtt.hostname}") String hostname, @Value("${mqtt.port}") int port) throws MqttException {
//
//        IMqttClient mqttClient = new MqttClient("tcp://" + hostname + ":" + port, clientId);
//
//        mqttClient.connect(mqttConnectOptions());
//
//
//        return mqttClient;
//    }
//
//    @Bean
//    public MessageChannel mqttInputChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageProducer inbound() {
//        MqttPahoMessageDrivenChannelAdapter adapter =
//                new MqttPahoMessageDrivenChannelAdapter("tcp://192.168.0.254:1883", "SpringBoot-server",
//                        subTopic); // 192.168.0.254에서 본인 서버 ip 혹은 도메인 주소 넣으시면됩니다.
//        adapter.setCompletionTimeout(5000);
//        adapter.setConverter(new DefaultPahoMessageConverter());
//        adapter.setQos(1);
//        adapter.setOutputChannel(mqttInputChannel());
//        return adapter;
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannel")
//    public MessageHandler handler() {
//        return new MessageHandler() {
//
//            @Override
//            public void handleMessage(Message<?> message) throws MessagingException {
//                System.out.println("MQTT MessageHandler : " + message.getPayload());
//            }
//
//        };
//    }
//}
