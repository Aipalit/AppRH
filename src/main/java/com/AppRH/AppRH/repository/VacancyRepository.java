package com.AppRH.AppRH.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Vacancy;

public interface VacancyRepository extends CrudRepository<Vacancy, String> {
    Vacancy findByCode(long code);

    List<Vacancy> findByName(String name);

}
