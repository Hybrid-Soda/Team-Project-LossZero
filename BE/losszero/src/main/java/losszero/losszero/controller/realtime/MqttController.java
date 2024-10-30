package losszero.losszero.controller.realtime;

import losszero.losszero.dto.realtime.MqttDto;
import losszero.losszero.service.realtime.MqttService;
import losszero.losszero.service.realtime.MqttServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realtime")
public class MqttController {
    @Autowired
    MqttService mqttService = new MqttServiceImpl();

    @PostMapping("/pub")
    public ResponseEntity<Void> publish(@RequestBody MqttDto dto) {
        mqttService.publish(dto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
