package org.example.Controller;

import org.bson.types.ObjectId;
import org.example.Model.User;
import org.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public User register(@RequestBody User user){
        User res=userService.register(user);
        return res;

    }
    @PostMapping("/login")
    public Object login(@RequestBody User user){
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        return userService.userLogin(userEmail, userPassword);
    }
    @GetMapping("/getusers")
    public List<User> getallmovie(){
        return userService.save();
    }

    @GetMapping("/getuserbyid/{id}")
    public User getuserbyid(@PathVariable ObjectId id){
        return userService.getuserbyid(id);
    }
    @DeleteMapping("/deletebyid/{id}")
    public void deleteUser(@PathVariable ObjectId id){
        userService.deleteUser(id);
    }

    @PutMapping("/updatebyid/{id}")
    public User update(@PathVariable String id ,@RequestBody User user){
       return userService.update(id,user);
    }

}
