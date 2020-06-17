package com.evelin.cars.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cars")
public class CarController {



    @GetMapping("/all")
    public String getAllCars(){
        return "all";
    }
}
