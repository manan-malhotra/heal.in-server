package in.app.heal.controller;

import in.app.heal.aux.AuxUserDTO;
import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import in.app.heal.service.UserCredentialsService;
import in.app.heal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCredentialsService userCredentialsService;
    @PostMapping(path="/register")
    public ResponseEntity<?> registerUser(@RequestBody AuxUserDTO auxUserDTO){
        User newUser = new User();
        System.out.println("TESTING");
        newUser.setEmail(auxUserDTO.getEmail());
        newUser.setFirst_name(auxUserDTO.getFirstName());
        newUser.setLast_name(auxUserDTO.getLastName());
        newUser.setContact_number(auxUserDTO.getContact());
        newUser = userService.addUser(newUser);
        UserCredentials newUserCredentials = new UserCredentials();
        newUserCredentials.setUser_id(newUser);
        newUserCredentials.setPassword(auxUserDTO.getPassword()); // to be hashed
        newUserCredentials.setRole(auxUserDTO.getRole());
        userCredentialsService.addUser(newUserCredentials);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
