package com.comvous.demo.services;

import com.comvous.demo.data.models.Promo;
import com.comvous.demo.data.repositories.PromoRepository;
import org.springframework.stereotype.Service;

@Service
public class PromoService extends BaseCrudService<PromoRepository, Promo> {
    public PromoService(PromoRepository promoRepository) {
        super(promoRepository);
    }

    public Promo findByName(String name) {
        return super.linkedRepository.findByNameContainsIgnoreCase(name);
    }
}
