package org.somesandwich.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.somesandwich.domain.CountryTestSamples.*;
import static org.somesandwich.domain.RegionTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.somesandwich.web.rest.TestUtil;

class RegionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Region.class);
        Region region1 = getRegionSample1();
        Region region2 = new Region();
        assertThat(region1).isNotEqualTo(region2);

        region2.setRegionId(region1.getRegionId());
        assertThat(region1).isEqualTo(region2);

        region2 = getRegionSample2();
        assertThat(region1).isNotEqualTo(region2);
    }

    @Test
    void countriesTest() throws Exception {
        Region region = getRegionRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        region.addCountries(countryBack);
        assertThat(region.getCountries()).containsOnly(countryBack);
        assertThat(countryBack.getRegion()).isEqualTo(region);

        region.removeCountries(countryBack);
        assertThat(region.getCountries()).doesNotContain(countryBack);
        assertThat(countryBack.getRegion()).isNull();

        region.countries(new HashSet<>(Set.of(countryBack)));
        assertThat(region.getCountries()).containsOnly(countryBack);
        assertThat(countryBack.getRegion()).isEqualTo(region);

        region.setCountries(new HashSet<>());
        assertThat(region.getCountries()).doesNotContain(countryBack);
        assertThat(countryBack.getRegion()).isNull();
    }
}
