package com.capstone.dayj.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNickname {
    public static String randomMix(int range) {
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        
        for (int i = 0; i < range; i++) {
            if (rd.nextBoolean())
                sb.append(rd.nextInt(10));
            else
                sb.append((char) (rd.nextInt(26) + 65));
        }
        
        return sb.toString();
    }
}
