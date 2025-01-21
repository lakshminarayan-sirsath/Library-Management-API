package com.task.library.management.RestController;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/demo")
public class DemoController {

	@GetMapping("/hello")
	public ResponseEntity<String> sayHello(){
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = authentication.getName(); // Retrieve the username of the authenticated user
        return ResponseEntity.ok("Hello, " + username + "!");
		
	}
	
}
