package losszero.losszero.controller.user;

import losszero.losszero.dto.user.JoinDTO;
import losszero.losszero.service.user.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@ResponseBody
@RequestMapping("/api/v1")

public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }
    @PostMapping("/api/v1/join")
    public String joinProcess(JoinDTO joinDTO) {

        joinService.joinProcess(joinDTO);
        return "join ok";
    }
}
