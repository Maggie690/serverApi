package com.example.server.api.server.service.impl;

import com.example.server.api.server.enumaration.Status;
import com.example.server.api.server.model.Server;
import com.example.server.api.server.repository.ServerRepository;
import com.example.server.api.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server ipAddress {}", ipAddress);

        Server server = serverRepository.findByIpAddress(ipAddress);

        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.UP : Status.DOWN);

        return serverRepository.save(server);
    }

    @Override
    public Server create(Server server) {
        log.info("Saving new server {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");

        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id {}", id);

        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server {}", server.getName());

        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by {}", id);

        serverRepository.deleteById(id);
        return serverRepository.findById(id).isPresent();
    }

    private String setServerImageUrl() {
        String[] images = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("static/img/" + images[new Random().nextInt(4)])
                .toUriString();
    }
}
