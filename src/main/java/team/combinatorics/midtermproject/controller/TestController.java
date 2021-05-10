package team.combinatorics.midtermproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import team.combinatorics.midtermproject.service.TestService;

@Controller
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {
    TestService testService;

    @RequestMapping("/hello")
    public String sayHello() {
        return "test";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/loginIn", method = RequestMethod.POST)
    public String login(String name, String password) {
        System.out.println("name is " + name);
        System.out.println("password is " + password);
        if(name.equals("shuwashuwa") && password.equals("114514")){
            return "success";
        }else {
            return "error";
        }
    }

}
