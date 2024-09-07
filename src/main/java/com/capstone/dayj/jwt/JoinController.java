package com.capstone.dayj.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;
    
    @PostMapping("/join")
    public String joinProcess(JoinDTO dto) {
        joinService.joinProcess(dto);
        return "ok";
    }
}
