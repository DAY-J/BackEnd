package com.capstone.dayj.Oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    @GetMapping("/loginForm")
    public String home() {
        return "loginForm";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage";
    }

    @PostMapping("/api/auth/google")
    public ResponseEntity<String> handleGoogleLogin(@RequestParam("access_token") String accessToken) {
        System.out.println(accessToken);
        return ResponseEntity.ok(accessToken);
    }

//    @PostMapping("/api/auth/google")
//    public ResponseEntity<String> handleGoogleLogin(@RequestParam("access_token") TokenRequest tokenRequest) {
//        String accessToken = tokenRequest.getAccessToken();
//        System.out.println(accessToken);
//        return ResponseEntity.ok(accessToken);
//    }
}

//@Setter
//@Getter
//class TokenRequest {
//    private String accessToken;
//}