package codefellowship.example.codefellowship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

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
    public String goToHomePage(Model m, @RequestParam(value="username") String username){
        boolean isLoggedIn= false;
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        m.addAttribute("user",user);
        return "homeIn.html";
    }



    @GetMapping("/allUsers")
    public String viewUsers(Model m, @RequestParam(value = "name")String username){

        m.addAttribute("allUsers",applicationUserRepository.findAll());
       // m.addAttribute("theUser",applicationUserRepository.findByUsername(username));
        return "allUsers.html";
    }

    @GetMapping("/profile")
    public String profile(@RequestParam(value = "name")String  username,Model m){
        boolean postss= false;
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        Integer userId = user.getId();
        Post posts = postRepository.findByUserId(userId);
        m.addAttribute("user",user);
        m.addAttribute("posts",posts);
        if(posts != null){
            postss = true;
            m.addAttribute("postss",postss);
        }
        return "profile.html";
    }

    @PostMapping("/addPost")
    public RedirectView posting(@RequestParam(value = "body")String  body, Principal principal, Model m){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String time=formatter.format(date);

        ApplicationUser user= applicationUserRepository.findByUsername(principal.getName());
        Post post= new Post(body,time,user);
        postRepository.save(post);

        return new RedirectView("/profile");
    }

    @GetMapping("/profile/id")
    public String userPro(Model m,@RequestParam(value="id") Integer id){
        boolean postss= false;
        ApplicationUser user = applicationUserRepository.findById(id).get();
        Post posts = postRepository.findByUserId(user.getId());
        m.addAttribute("user",user);
        m.addAttribute("posts",posts);
        if(posts != null){
            postss = true;
            m.addAttribute("postss",postss);
        }

        return "anyProfile.html";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String getHome(){
        return "Hello";
    }

}