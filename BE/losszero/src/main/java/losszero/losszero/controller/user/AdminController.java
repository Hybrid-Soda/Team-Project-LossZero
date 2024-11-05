package losszero.losszero.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequestMapping("/api/v1")

public class AdminController {

    @GetMapping("/api/v1/admin")
    public String adminP() {
        return "admin Controller";
    }
}
