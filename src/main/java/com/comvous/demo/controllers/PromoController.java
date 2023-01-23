package com.comvous.demo.controllers;

import com.comvous.demo.data.models.Promo;
import com.comvous.demo.data.models.projections.PromoDTO;
import com.comvous.demo.data.repositories.PromoRepository;
import com.comvous.demo.services.PromoService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/promo")
public class PromoController extends BaseSecuredCrudController<PromoService, PromoRepository, Promo, PromoDTO> {
    public PromoController(PromoService promoService, ModelMapper modelMapper) {
        super(promoService, modelMapper, PromoDTO.class);
    }

    @RequestMapping("/name/{name}")
    public Promo getPromoByName(@PathVariable String name) {
        return this.linkedService.findByName(name);
    }
}