package com.Ecommerce.ecom.services.jwt;

import com.Ecommerce.ecom.entity.User;
import com.Ecommerce.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService { // Implement la interface que se utilize para cargar los detalles del usuario por nombre de usuario.

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findFirstByEmail(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("Usuario no encontrado", null);
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getEmail(), optionalUser.get().getPassword()
                , new ArrayList<>());
    }
    //loadUserByUsername.
    //Este metodo se utiliza para cargar los detalles del usuario por nombre de usuario desde la base de datos.
}
