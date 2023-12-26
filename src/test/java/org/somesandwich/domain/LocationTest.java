package org.somesandwich.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.somesandwich.domain.CountryTestSamples.*;
import static org.somesandwich.domain.DepartmentTestSamples.*;
import static org.somesandwich.domain.LocationTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.somesandwich.web.rest.TestUtil;

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2.setLocationId(location1.getLocationId());
        assertThat(location1).isEqualTo(location2);

        location2 = getLocationSample2();
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    void departmentsTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        location.addDepartments(departmentBack);
        assertThat(location.getDepartments()).containsOnly(departmentBack);
        assertThat(departmentBack.getLocation()).isEqualTo(location);

        location.removeDepartments(departmentBack);
        assertThat(location.getDepartments()).doesNotContain(departmentBack);
        assertThat(departmentBack.getLocation()).isNull();

        location.departments(new HashSet<>(Set.of(departmentBack)));
        assertThat(location.getDepartments()).containsOnly(departmentBack);
        assertThat(departmentBack.getLocation()).isEqualTo(location);

        location.setDepartments(new HashSet<>());
        assertThat(location.getDepartments()).doesNotContain(departmentBack);
        assertThat(departmentBack.getLocation()).isNull();
    }

    @Test
    void countryTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        location.setCountry(countryBack);
        assertThat(location.getCountry()).isEqualTo(countryBack);

        location.country(null);
        assertThat(location.getCountry()).isNull();
    }
}
