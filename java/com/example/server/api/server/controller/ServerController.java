package com.example.server.api.server.controller;

import com.example.server.api.server.enumaration.Status;
import com.example.server.api.server.model.Response;
import com.example.server.api.server.model.Server;
import com.example.server.api.server.service.ServerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService service;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(
                Response.builder()
                        .localDateTime(LocalDateTime.now())
                        .data(Map.of("servers", service.list(30)))
                        .message("Servers retrieved.")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = service.ping(ipAddress);

        return ResponseEntity.ok(
                Response.builder()
                        .localDateTime(LocalDateTime.now())
                        .data(Map.of("servers", service.list(30)))
                        .message(server.getStatus() == Status.UP ? "Ping success." : "Ping failed")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).build());
    }


    @PostMapping("/save")
    public ResponseEntity<Response> save(@RequestBody @Valid Server server) throws IOException {
        Server savedService = service.create(server);

        return ResponseEntity.ok(
                Response.builder()
                        .localDateTime(LocalDateTime.now())
                        .data(Map.of("servers", savedService))
                        .message("Service is created.")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getByIdServer(@PathVariable("id") Long id) {
        Server server = service.get(id);

        return ResponseEntity.ok(
                Response.builder()
                        .localDateTime(LocalDateTime.now())
                        .data(Map.of("servers", server))
                        .message("Server retrieved.")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {

        return ResponseEntity.ok(
                Response.builder()
                        .localDateTime(LocalDateTime.now())
                        .data(Map.of("Deleted server", service.delete(id)))
                        .message("Server deleted.")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping(path = "/image/{fileName}")
    @Procedure(value = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Downloads/resources/static/img" + fileName));

    }
}
