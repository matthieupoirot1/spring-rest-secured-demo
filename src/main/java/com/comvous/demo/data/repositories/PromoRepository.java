package com.comvous.demo.data.repositories;

import com.comvous.demo.data.models.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoRepository extends JpaRepository<Promo, Long> {
    public Promo findByNameContainsIgnoreCase(String name);
}
