package org.somesandwich.service;

import jakarta.persistence.criteria.ListJoin;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;
import lombok.val;
import org.hibernate.mapping.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.somesandwich.domain.Country;
import org.somesandwich.repository.CountryRepository;
import org.somesandwich.repository.search.CountrySearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.somesandwich.domain.Country}.
 */
@Service
@Transactional
public class CountryService {

    private final Logger log = LoggerFactory.getLogger(CountryService.class);

    private final CountryRepository countryRepository;

    private final CountrySearchRepository countrySearchRepository;

    public CountryService(CountryRepository countryRepository, CountrySearchRepository countrySearchRepository) {
        this.countryRepository = countryRepository;
        this.countrySearchRepository = countrySearchRepository;
    }

    /**
     * Save a country.
     *
     * @param country the entity to save.
     * @return the persisted entity.
     */
    public Country save(Country country) {
        log.debug("Request to save Country : {}", country);
        Country result = countryRepository.save(country);
        countrySearchRepository.index(result);
        return result;
    }

    /**
     * Update a country.
     *
     * @param country the entity to save.
     * @return the persisted entity.
     */
    public Country update(Country country) {
        log.debug("Request to update Country : {}", country);
        country.setIsPersisted();
        Country result = countryRepository.save(country);
        countrySearchRepository.index(result);
        return result;
    }

    /**
     * Partially update a country.
     *
     * @param country the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Country> partialUpdate(Country country) {
        log.debug("Request to partially update Country : {}", country);

        return countryRepository
            .findById(country.getCountryId())
            .map(existingCountry -> {
                if (country.getCountryName() != null) {
                    existingCountry.setCountryName(country.getCountryName());
                }

                return existingCountry;
            })
            .map(countryRepository::save)
            .map(savedCountry -> {
                countrySearchRepository.index(savedCountry);
                return savedCountry;
            });
    }

    /**
     * Get all the countries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Country> findAll(Pageable pageable) {
        log.debug("Request to get all Countries");
        Page<Country> result = countryRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one country by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Country> findOne(String id) {
        log.debug("Request to get Country : {}", id);
        return countryRepository.findById(id);
    }

    /**
     * Delete the country by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Country : {}", id);
        countryRepository.deleteById(id);
        countrySearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the country corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Country> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Countries for query {}", query);
        Page<Country> result = countrySearchRepository.search(query, pageable);

        java.util.List<Country> countries = countryRepository.findAll();

        result.forEach(country -> {
            country.setCountryName(
                countries.stream().filter(c -> c.getCountryId().equals(country.getCountryId())).findFirst().get().getCountryName()
            );
        });
        return result;
    }
}
