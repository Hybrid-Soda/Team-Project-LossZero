package losszero.losszero.controller.realtime;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.realtime.MqttDto;
import losszero.losszero.service.realtime.MqttService;
import losszero.losszero.service.realtime.MqttServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/realtime")
public class MqttController {

    private final MqttServiceImpl mqttService;

    @PostMapping("/pub")
    public ResponseEntity<Void> publish(@RequestBody MqttDto dto) {
        mqttService.publish(dto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
