package com.capstone.dayj.jwt.controller;

import com.capstone.dayj.jwt.service.JoinService;
import com.capstone.dayj.jwt.dto.JoinDTO;
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
