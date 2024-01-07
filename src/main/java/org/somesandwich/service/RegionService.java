package org.somesandwich.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.somesandwich.domain.Region;
import org.somesandwich.repository.RegionRepository;
import org.somesandwich.repository.search.RegionSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.somesandwich.domain.Region}.
 */
@Service
@Transactional
public class RegionService {

    private final Logger log = LoggerFactory.getLogger(RegionService.class);

    private final RegionRepository regionRepository;

    private final RegionSearchRepository regionSearchRepository;

    public RegionService(RegionRepository regionRepository, RegionSearchRepository regionSearchRepository) {
        this.regionRepository = regionRepository;
        this.regionSearchRepository = regionSearchRepository;
    }

    /**
     * Save a region.
     *
     * @param region the entity to save.
     * @return the persisted entity.
     */
    public Region save(Region region) {
        log.debug("Request to save Region : {}", region);
        Region result = regionRepository.save(region);
        regionSearchRepository.index(result);
        return result;
    }

    /**
     * Update a region.
     *
     * @param region the entity to save.
     * @return the persisted entity.
     */
    public Region update(Region region) {
        log.debug("Request to update Region : {}", region);
        Region result = regionRepository.save(region);
        regionSearchRepository.index(result);
        return result;
    }

    /**
     * Partially update a region.
     *
     * @param region the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Region> partialUpdate(Region region) {
        log.debug("Request to partially update Region : {}", region);

        return regionRepository
            .findById(region.getRegionId())
            .map(existingRegion -> {
                if (region.getRegionName() != null) {
                    existingRegion.setRegionName(region.getRegionName());
                }

                return existingRegion;
            })
            .map(regionRepository::save)
            .map(savedRegion -> {
                regionSearchRepository.index(savedRegion);
                return savedRegion;
            });
    }

    /**
     * Get all the regions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Region> findAll(Pageable pageable) {
        log.debug("Request to get all Regions");
        return regionRepository.findAll(pageable);
    }

    /**
     * Get one region by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Region> findOne(Long id) {
        log.debug("Request to get Region : {}", id);
        return regionRepository.findById(id);
    }

    /**
     * Delete the region by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Region : {}", id);
        regionRepository.deleteById(id);
        regionSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the region corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Region> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Regions for query {}", query);
        Page<Region> result = regionSearchRepository.search(query, pageable);
        java.util.List<Region> regions = regionRepository.findAll();
        result.forEach(region ->
            region.setRegionName(
                regions.stream().filter(x -> x.getRegionId().equals(region.getRegionId())).findFirst().get().getRegionName()
            )
        );

        return result;
    }
}
