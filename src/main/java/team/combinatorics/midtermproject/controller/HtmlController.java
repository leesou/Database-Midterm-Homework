package team.combinatorics.midtermproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("front")
public class HtmlController {

    @RequestMapping("/login")
    public String logIn() {
        return "login";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

}
