package com.Pluralsight1.NorthwindTradersAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(
            @RequestParam(name = "country", defaultValue = "") String country
    ) {
        if (!country.isBlank()) {
            return "Hello " + country + "!";
        }
        return "Hello World!";
    }
}
