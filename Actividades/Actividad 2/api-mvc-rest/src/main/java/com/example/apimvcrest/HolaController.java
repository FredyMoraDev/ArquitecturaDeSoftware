package com.example.apimvcrest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {

    @GetMapping("/Hola")
    public String saludos()
    {
        return "Hola UIA" +
                " Actividad 2 api-mvc-rest";
    }
}
