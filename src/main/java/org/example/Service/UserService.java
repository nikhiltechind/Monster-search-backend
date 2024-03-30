package org.example.Service;

import org.bson.types.ObjectId;
import org.example.Model.User;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public UserService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public User register(User user){
        User saveall=userRepository.save(user);
        System.out.println("register successful");
        return saveall;
    }

    public Boolean existByEmail(String email){
        Optional<User> usersObj = Optional.ofNullable(userRepository.getUserByUserName(email));
        if(!usersObj.isEmpty()){
            return true;
        }
        return false;
    }


    public Object userLogin(String email, String password ) {
        boolean foundUsers = existByEmail(email);
        HashMap<String,String> response=new HashMap<>();
        if(foundUsers) {
            User user = userRepository.getUserByUserName(email);
            if(user.getPassword().equals(password)) {
//                return "{" +
//                        "\"message\":"+"Successfully logged in\",\n"+
//                        "\"data\":"+user+",\n"+
//                        "\"Email:"+user.getEmail()
//                        +"\n"+
//                        "}"+
//                        "{"+
//                        "\"token\":"+tokenService.createTokenFunction(user.getId())+"\n"+
//                        "}"
//                        ;
                response.put("username",user.getUsername());
                response.put("message","Successfully logged in");
                response.put("email",user.getEmail());
                response.put("token",tokenService.createTokenFunction(user.getId()));
                return response;

            }
        }
        response.put("message","Authentication Failed");
        return  response;
    }
    public List<User>save(){
        return userRepository.findAll();
    }

    public void deleteUser(ObjectId id){

        userRepository.deleteById(id);
    }
    public User getuserbyid(ObjectId id){
        return userRepository.findById(id).get();
    }
    public User update(String id,User user){
       System.out.println(user);
        User saveduser = userRepository.findById(new ObjectId(id)).get();
         System.out.println(saveduser);

        saveduser.setUserid(user.getUserid());
        saveduser.setUsername(user.getUsername());
        saveduser.setEmail(user.getEmail());
        saveduser.setPassword(user.getPassword());
        return userRepository.save(saveduser);
    }
}
