package com.luizreis.desafiocrudclientes.repositories;

import com.luizreis.desafiocrudclientes.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
