package codefellowship.example.codefellowship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String home(){
        return "home.html";
    }

    @GetMapping("/profile")
    public String profile(@RequestParam(value = "username")String  username,Model m){

        m.addAttribute("user", applicationUserRepository.findByUsername(username));
        return "profile.html";
    }


    @GetMapping("/hello")
    @ResponseBody
    public String getHome(){

        return "Hello";
    }

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signUp.html";
    }

    @PostMapping("/login")
    public String goToProfile(Model m,@RequestParam(value="username") String username){
        boolean isLoggedIn= false;
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        if(applicationUserRepository.findByUsername(username) != null){
            m.addAttribute("user",user);
            isLoggedIn = true;
        }
        m.addAttribute("isLoggedIn",isLoggedIn);
        return "profile.html";
    }

    @GetMapping("/login")
    public String getSignInPage(){
        return "signIn.html";
    }

    @PostMapping("/signup")
    public RedirectView signUp(@RequestParam(value="username") String username, @RequestParam(value="password") String password, @RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, @RequestParam(value="bio") String bio, @RequestParam(value="dateOfBirth") String dateOfBirth){
        ApplicationUser newUser = new ApplicationUser(username,bCryptPasswordEncoder.encode(password),firstName,lastName, bio, dateOfBirth);
        applicationUserRepository.save(newUser);
        return new RedirectView("/login");
    }

}