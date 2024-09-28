package com.Ecommerce.ecom.services.auth;

import com.Ecommerce.ecom.dto.SignupRequest;
import com.Ecommerce.ecom.dto.UserDto;
import com.Ecommerce.ecom.entity.User;
import com.Ecommerce.ecom.enums.UserRole;
import com.Ecommerce.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(SignupRequest signupRequest) {
    //Implementacion de metodo
        User user = new User();

        //ponemos los detalles
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword())); //codificar la contraseña primero y dentro de encode proporcionamos la contraseña
        user.setRole(UserRole.CUSTOMER); //Mediante esta API solo crearemos clientes.
        User createUser = userRepository.save(user);//Asignaremos un rol para guardar en la bd

        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId()); //Establecemos ID para el cliente
        return userDto;

    }
    public Boolean hasUserWithEmail(String email){ //Verificara si ya hay un  usuario creado con email y retornara verdadero, de lo contrario falso
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
