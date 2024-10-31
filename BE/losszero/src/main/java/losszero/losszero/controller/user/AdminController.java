package losszero.losszero.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@ResponseBody
@RequestMapping("/api/v1")

public class AdminController {

    @GetMapping("/admin")
    public String adminP() {
        return "admin Controller";
    }
}
