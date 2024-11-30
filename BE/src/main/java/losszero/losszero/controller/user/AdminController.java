package losszero.losszero.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AdminController {

    @GetMapping("/api/v1/admin")
    public String adminP() {
        return "admin Controller";
    }
}
