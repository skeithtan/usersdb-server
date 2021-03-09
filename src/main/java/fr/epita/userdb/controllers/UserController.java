package fr.epita.userdb.controllers;

import fr.epita.userdb.datatransfer.UserCredentialsDTO;
import fr.epita.userdb.datatransfer.UserDTO;
import fr.epita.userdb.datatransfer.UserRegistrationDTO;
import fr.epita.userdb.repositories.UserRepository;
import fr.epita.userdb.services.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@EnableAutoConfiguration
@CrossOrigin(origins = "*")
@Validated
public class UserController {
    private final UserRepository repository;
    private final UserService service;

    @Autowired
    public UserController(UserRepository repository, UserService service) {
        this.repository = repository;
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createAccount(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        if (service.userWithUsernameExists(userRegistrationDTO.getCredentials().getUsername())) {
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var newUser = service.createUser(userRegistrationDTO);
        return new ResponseEntity<>(UserDTO.fromEntity(newUser), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, String>> getToken(@Valid @RequestBody UserCredentialsDTO userCredentialsDTO) {
        var targetUser = service.findUserWithUsername(userCredentialsDTO.getUsername());
        if (targetUser.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!service.credentialsAreValid(userCredentialsDTO, targetUser.get())) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        var jwt = service.makeJWT(targetUser.get());
        return new ResponseEntity<>(Map.of("token", jwt), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
