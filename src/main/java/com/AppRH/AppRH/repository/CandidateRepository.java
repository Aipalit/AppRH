package com.AppRH.AppRH.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Candidate;
import com.AppRH.AppRH.models.Vacancy;

public interface CandidateRepository extends CrudRepository<Candidate, String> {
    Iterable<Candidate> findByVacancy(Vacancy vacancy);

    Candidate findByRg(String rg);

    Candidate findById(long id);

    List<Candidate> findByCandidateName(String candidateName);
}
