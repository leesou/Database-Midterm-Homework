package team.combinatorics.midtermproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/front")
public class HtmlController {

    @RequestMapping("/error")
    public String error() {
        return "error";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/")
    public String mainPage() {
        return "mainPage";
    }

}
