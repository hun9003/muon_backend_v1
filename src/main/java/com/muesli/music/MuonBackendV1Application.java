package com.muesli.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MuonBackendV1Application {

    public static void main(String[] args) {
        SpringApplication.run(MuonBackendV1Application.class, args);
        String getVersion = org.springframework.core.SpringVersion.getVersion();
        String getVersion2 = org.springframework.boot.SpringBootVersion.getVersion();
        System.out.println("스프링 프레임워크 버전 : " + getVersion);
        System.out.println("스프링 부트 버전 : " + getVersion2);
    }

}
