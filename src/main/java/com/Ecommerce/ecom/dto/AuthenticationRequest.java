package com.Ecommerce.ecom.dto;

//Esta clase se utiliza para representar los datos de la solicitud de autenticación.

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
