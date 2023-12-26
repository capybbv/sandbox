package org.somesandwich.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.somesandwich.domain.Region;
import org.somesandwich.repository.RegionRepository;
import org.somesandwich.service.RegionService;
import org.somesandwich.web.rest.errors.BadRequestAlertException;
import org.somesandwich.web.rest.errors.ElasticsearchExceptionMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.somesandwich.domain.Region}.
 */
@RestController
@RequestMapping("/api/regions")
public class RegionResource {

    private final Logger log = LoggerFactory.getLogger(RegionResource.class);

    private static final String ENTITY_NAME = "region";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegionService regionService;

    private final RegionRepository regionRepository;

    public RegionResource(RegionService regionService, RegionRepository regionRepository) {
        this.regionService = regionService;
        this.regionRepository = regionRepository;
    }

    /**
     * {@code POST  /regions} : Create a new region.
     *
     * @param region the region to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new region, or with status {@code 400 (Bad Request)} if the region has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Region> createRegion(@RequestBody Region region) throws URISyntaxException {
        log.debug("REST request to save Region : {}", region);
        if (region.getRegionId() != null) {
            throw new BadRequestAlertException("A new region cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Region result = regionService.save(region);
        return ResponseEntity
            .created(new URI("/api/regions/" + result.getRegionId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getRegionId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regions/:regionId} : Updates an existing region.
     *
     * @param regionId the id of the region to save.
     * @param region the region to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated region,
     * or with status {@code 400 (Bad Request)} if the region is not valid,
     * or with status {@code 500 (Internal Server Error)} if the region couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{regionId}")
    public ResponseEntity<Region> updateRegion(
        @PathVariable(value = "regionId", required = false) final Long regionId,
        @RequestBody Region region
    ) throws URISyntaxException {
        log.debug("REST request to update Region : {}, {}", regionId, region);
        if (region.getRegionId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(regionId, region.getRegionId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regionRepository.existsById(regionId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Region result = regionService.update(region);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, region.getRegionId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /regions/:regionId} : Partial updates given fields of an existing region, field will ignore if it is null
     *
     * @param regionId the id of the region to save.
     * @param region the region to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated region,
     * or with status {@code 400 (Bad Request)} if the region is not valid,
     * or with status {@code 404 (Not Found)} if the region is not found,
     * or with status {@code 500 (Internal Server Error)} if the region couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{regionId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Region> partialUpdateRegion(
        @PathVariable(value = "regionId", required = false) final Long regionId,
        @RequestBody Region region
    ) throws URISyntaxException {
        log.debug("REST request to partial update Region partially : {}, {}", regionId, region);
        if (region.getRegionId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(regionId, region.getRegionId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regionRepository.existsById(regionId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Region> result = regionService.partialUpdate(region);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, region.getRegionId().toString())
        );
    }

    /**
     * {@code GET  /regions} : get all the regions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Region>> getAllRegions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Regions");
        Page<Region> page = regionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regions/:id} : get the "id" region.
     *
     * @param id the id of the region to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the region, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegion(@PathVariable("id") Long id) {
        log.debug("REST request to get Region : {}", id);
        Optional<Region> region = regionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(region);
    }

    /**
     * {@code DELETE  /regions/:id} : delete the "id" region.
     *
     * @param id the id of the region to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable("id") Long id) {
        log.debug("REST request to delete Region : {}", id);
        regionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /regions/_search?query=:query} : search for the region corresponding
     * to the query.
     *
     * @param query the query of the region search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<Region>> searchRegions(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of Regions for query {}", query);
        try {
            Page<Region> page = regionService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
