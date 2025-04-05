package com.example.server.api.server;

import com.example.server.api.server.enumaration.Status;
import com.example.server.api.server.model.Server;
import com.example.server.api.server.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ServerRepository repository) {
        return args -> {
            repository.save(new Server(null, "127.0.0.1", "Mac", "16 GB", "Personal PC", "http://lovalhost:8080/server/image/server1.png", Status.UP));
            repository.save(new Server(null, "168.20.300.5", "Linux", "32 GB", "Personal PC", "http://lovalhost:8080/server/image/server1.png", Status.UP));
        };
    }
}
