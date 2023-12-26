package org.somesandwich.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.somesandwich.domain.CountryTestSamples.*;
import static org.somesandwich.domain.LocationTestSamples.*;
import static org.somesandwich.domain.RegionTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.somesandwich.web.rest.TestUtil;

class CountryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = getCountrySample1();
        Country country2 = new Country();
        assertThat(country1).isNotEqualTo(country2);

        country2.setCountryId(country1.getCountryId());
        assertThat(country1).isEqualTo(country2);

        country2 = getCountrySample2();
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    void locationsTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        country.addLocations(locationBack);
        assertThat(country.getLocations()).containsOnly(locationBack);
        assertThat(locationBack.getCountry()).isEqualTo(country);

        country.removeLocations(locationBack);
        assertThat(country.getLocations()).doesNotContain(locationBack);
        assertThat(locationBack.getCountry()).isNull();

        country.locations(new HashSet<>(Set.of(locationBack)));
        assertThat(country.getLocations()).containsOnly(locationBack);
        assertThat(locationBack.getCountry()).isEqualTo(country);

        country.setLocations(new HashSet<>());
        assertThat(country.getLocations()).doesNotContain(locationBack);
        assertThat(locationBack.getCountry()).isNull();
    }

    @Test
    void regionTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        Region regionBack = getRegionRandomSampleGenerator();

        country.setRegion(regionBack);
        assertThat(country.getRegion()).isEqualTo(regionBack);

        country.region(null);
        assertThat(country.getRegion()).isNull();
    }
}
