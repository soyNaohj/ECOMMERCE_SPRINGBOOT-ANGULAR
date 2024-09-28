package com.Ecommerce.ecom.controller;

//Esta clase se utiliza para gestionar las solicitudes de autenticación.

import com.Ecommerce.ecom.dto.AuthenticationRequest;
import com.Ecommerce.ecom.dto.SignupRequest;
import com.Ecommerce.ecom.dto.UserDto;
import com.Ecommerce.ecom.entity.User;
import com.Ecommerce.ecom.repository.UserRepository;
import com.Ecommerce.ecom.services.auth.AuthService;
import com.Ecommerce.ecom.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private  static  final String TOKEN_PREFIX = "Bearer";
    private  static  final String HEADER_STRING = "Authorization";
    private final AuthService authService; //Crear objeto para nuestra servicio de autenticacion

    //Prueba la API de inicio de sesión enviando una solicitud POST
    @PostMapping("/authenticate")
    //Este metodo se utiliza para crear un token de autenticación para el usuario.
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest
    , HttpServletResponse response) throws IOException, JSONException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
            ,authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Usuario o contraseña Incorrecta. ");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        //Si la autenticación es exitosa, el servidor devolverá un token JWT.
        if(optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getRole())
                    .toString()
            );
        }
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);

    }
    @PostMapping("/sign-up")
public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){ //SI ya hay un usuario devolvera usuario ya existe
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return  new ResponseEntity<>("Usuario ya existe ", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
