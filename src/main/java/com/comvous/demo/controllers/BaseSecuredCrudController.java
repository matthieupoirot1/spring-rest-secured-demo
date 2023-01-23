package com.comvous.demo.controllers;

import com.comvous.demo.services.BaseCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.links.Link;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @param <LinkedService> The service that will be used to perform CRUD operations
 * @param <LinkedRepository> The repository that will be used to perform CRUD operations
 * @param <LinkedDomainModel> The domain model that will be used to perform CRUD operations
 * @param <DTO> The DTO to which the domain model will be mapped
 */
public class BaseSecuredCrudController<LinkedService extends BaseCrudService<LinkedRepository, LinkedDomainModel>, LinkedRepository extends JpaRepository<LinkedDomainModel, Long>, LinkedDomainModel, DTO>{

    protected final LinkedService linkedService;
    ModelMapper modelMapper;
    Class<DTO> dtoClass;

    /**
     *
     * @param linkedService The service that will be used to perform CRUD operations
     * @param modelMapper The model mapper that will be used to map the domain model to the DTO
     * @param dtoClass The DTO to which the domain model will be mapped
     */
    public BaseSecuredCrudController(LinkedService linkedService, ModelMapper modelMapper, Class<DTO> dtoClass) {
        this.linkedService = linkedService;
        this.modelMapper = modelMapper;
        this.dtoClass = dtoClass;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    public List<DTO> getAll(){
        List<LinkedDomainModel> foundModels = linkedService.getAll();
        return foundModels.stream().map(model -> modelMapper.map(model, dtoClass)).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    public LinkedDomainModel getById(@PathVariable Long id){
        return linkedService.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    public void deleteById(@PathVariable Long id){
        linkedService.delete(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    public LinkedDomainModel create(@RequestBody LinkedDomainModel model){
        return linkedService.create(model);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    public LinkedDomainModel update(@RequestBody LinkedDomainModel model){
        return linkedService.update(model);
    }
}
