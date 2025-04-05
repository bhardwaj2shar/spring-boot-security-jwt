package com.example.securityjwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/api/user")
    public String userEndPoint() {
        return  "Hello, User";
    }

    @GetMapping("/api/admin")
    public String adminEndPoint() {
        return  "Hello, Admin";
    }

    @GetMapping("/api/both")
    public String bothEndPoint() {
        return  "Hello, User Or Admin";
    }

}
