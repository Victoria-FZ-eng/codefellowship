package codefellowship.example.codefellowship;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class HomeController {
    // inside a controller class, you will add the endpoints (requests mapping)

    // as we have thymeleaf configuration, then the app expects to receive template names in responses

    // create an endpoint that corresponds to get requests
    // add an implementation to the function that will run when receiving a request on that path
//    @GetMapping("/")    // localhost:8080    localhost:8080/
//    @ResponseBody       // include the return value as is in the response body
//    public String getHome(Principal p){
////        m.addAttribute("userName", p.getName());
//        return "Hello World " + p.getName();
//    }

//    @GetMapping("/test")
//    public String testTemplate(Model model){
//        model.addAttribute("Name","Humam");
//        model.addAttribute("Age", 25);
//        return "template.html";
//    }


}
