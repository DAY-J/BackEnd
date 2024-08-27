package com.capstone.dayj.Oauth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OAuth2Controller {
    OAuth2AppUserService oAuth2AppUserService;
    @GetMapping("/loginForm")
    public String home(){
        return "loginForm";
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage";
    }

    @PostMapping("/api/auth/google")
    public ResponseEntity<String> handleGoogleLogin(@RequestParam("access_token") String accessToken) {
        System.out.println(accessToken);
        return oAuth2AppUserService.googleLogin(accessToken);
    }
}cd 