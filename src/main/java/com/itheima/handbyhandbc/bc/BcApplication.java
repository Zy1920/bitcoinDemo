package com.itheima.handbyhandbc.bc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Scanner;

@SpringBootApplication
public class BcApplication {

    public static String port;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        port = scanner.nextLine();
        //启动应用
        new SpringApplicationBuilder(BcApplication.class).properties("server.port=" + port).run(args);
    }
}
