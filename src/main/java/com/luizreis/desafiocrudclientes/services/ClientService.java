package com.luizreis.desafiocrudclientes.services;

import com.luizreis.desafiocrudclientes.dto.ClientDTO;
import com.luizreis.desafiocrudclientes.entities.Client;
import com.luizreis.desafiocrudclientes.repositories.ClientRepository;
import com.luizreis.desafiocrudclientes.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client client = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Client not found!"));
        return new ClientDTO(client);
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<Client> result = repository.findAll(pageable);
        return result.map(client -> new ClientDTO(client));
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client client = new Client();
        copyDtoToEntity(client, dto);
        client = repository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try{
            Client client = repository.getReferenceById(id);
            copyDtoToEntity(client, dto);
            client = repository.save(client);
            return new ClientDTO(client);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Client not found!");
        }

    }
    private void copyDtoToEntity(Client entity, ClientDTO dto){
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setChildren(dto.getChildren());
        entity.setBirthDate(dto.getBirthDate());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Client not found!");
        }
        repository.deleteById(id);
    }
}
