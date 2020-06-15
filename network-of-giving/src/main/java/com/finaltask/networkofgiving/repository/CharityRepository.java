package com.finaltask.networkofgiving.repository;

import com.finaltask.networkofgiving.model.Charity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharityRepository extends JpaRepository<Charity, Long> {

    List<Charity> findAllByOwner(String username);

    void deleteByIdAndOwner(Long id, String owner);
}
