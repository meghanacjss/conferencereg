package com.example.conference_reg.service;

import com.example.conference_reg.entity.Owner;
import com.example.conference_reg.model.OwnerModel;
import com.example.conference_reg.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public OwnerModel registerOwner(OwnerModel ownerModel) {
        Owner owner = convertToEntity(ownerModel);
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        Owner savedOwner = ownerRepository.save(owner);
        return convertToModel(savedOwner);
    }
    public OwnerModel login(String username,String password) {
        Owner owner = ownerRepository.findByUsernameAndPassword(username,password);
        return convertToModel(owner);
    }

    private OwnerModel convertToModel(Owner owner) {
        if (owner == null) {
            return null;
        }
        OwnerModel ownerModel = new OwnerModel();
        ownerModel.setName(owner.getName());
        ownerModel.setRole(owner.getRole());
        ownerModel.setUsername(owner.getUsername());
        ownerModel.setPassword(owner.getPassword());
        ownerModel.setEmail(owner.getEmail());
        return ownerModel;
    }

    private Owner convertToEntity(OwnerModel ownerModel) {
        if (ownerModel == null) {
            return null;
        }
        Owner owner = new Owner();
        owner.setName(ownerModel.getName());
        owner.setRole(ownerModel.getRole());
        owner.setUsername(ownerModel.getUsername());
        owner.setPassword(ownerModel.getPassword());
        owner.setEmail(ownerModel.getEmail());
        return owner;
    }
}
