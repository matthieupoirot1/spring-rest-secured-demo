package com.comvous.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class BaseCrudService<LinkedRepository extends JpaRepository<LinkedDomainModel, Long>, LinkedDomainModel> {

    LinkedRepository linkedRepository;
    protected BaseCrudService(LinkedRepository repository){
        this.linkedRepository = repository;
    }

    public LinkedDomainModel create(LinkedDomainModel model){
        return this.linkedRepository.save(model);
    }

    public LinkedDomainModel update(LinkedDomainModel model){
        return this.linkedRepository.save(model);
    }

    public void delete(Long id){
        this.linkedRepository.deleteById(id);
    }

    public LinkedDomainModel getById(Long id){
        return this.linkedRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Can't find model with given ID : " + id)
        );
    }

    public List<LinkedDomainModel> getAll(){
        return this.linkedRepository.findAll();
    }
}
