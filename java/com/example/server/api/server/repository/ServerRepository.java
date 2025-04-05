package com.example.server.api.server.repository;

import com.example.server.api.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server,Long> {
    Server findByIpAddress(String ipAddress);
}
