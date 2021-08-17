package codefellowship.example.codefellowship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    PostRepository postRepository;

    @GetMapping("/")
    public String home(){
        return "home.html";
    }

    @GetMapping("/in")
    public String homeIn(){
        return "homeIn.html";
    }


    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signUp.html";
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

    @PostMapping("/login")
    public String goToHomePage(Model m,@RequestParam(value="username") String username){
        boolean isLoggedIn= false;
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        if(applicationUserRepository.findByUsername(username) != null){
            m.addAttribute("user",user);
            isLoggedIn = true;
        }
        m.addAttribute("isLoggedIn",isLoggedIn);
        return "homeIn.html";
    }



    @GetMapping("/allUsers")
    public String viewUsers(Model m, @RequestParam(value = "name")String username){

        m.addAttribute("allUsers",applicationUserRepository.findAll());
        m.addAttribute("theUser",applicationUserRepository.findByUsername(username));
        return "allUsers.html";
    }

    @GetMapping("/profile")
    public String profile(@RequestParam(value = "name")String  username,Model m){
        m.addAttribute("user", applicationUserRepository.findByUsername(username));
       // m.addAttribute("posts",postRepository.findByUser(applicationUserRepository.findByUsername(username)));
        return "profile.html";
    }

    @PostMapping("/addPost")
    public RedirectView posting(@RequestParam(value = "body")String  body,@RequestParam(value = "post")String name,Model m){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String time=formatter.format(date);

        ApplicationUser user= applicationUserRepository.findByUsername(name);
        Post post= new Post(body,time,user);
        postRepository.save(post);

        return new RedirectView("/profile");
    }

    @GetMapping("/profile/id")
    public String userPro(Model m,@RequestParam(value="id") Integer id){
        ApplicationUser user = applicationUserRepository.findById(id).get();
        Post posts = postRepository.findByUser(user);
        m.addAttribute("user",user);
        m.addAttribute("posts",posts);
        return "anyProfile.html";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String getHome(){
        return "Hello";
    }

}