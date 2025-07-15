package s5almiakki.oauth2practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/my-page")
    public String myPage() {
        return "my-page";
    }
}
