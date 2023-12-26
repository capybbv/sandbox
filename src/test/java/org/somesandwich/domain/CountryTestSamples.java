package org.somesandwich.domain;

import java.util.UUID;

public class CountryTestSamples {

    public static Country getCountrySample1() {
        return new Country().countryId("countryId1").countryName("countryName1");
    }

    public static Country getCountrySample2() {
        return new Country().countryId("countryId2").countryName("countryName2");
    }

    public static Country getCountryRandomSampleGenerator() {
        return new Country().countryId(UUID.randomUUID().toString()).countryName(UUID.randomUUID().toString());
    }
}
