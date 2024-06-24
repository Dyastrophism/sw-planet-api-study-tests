package com.example.sw_planet_api.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.springframework.dao.EmptyResultDataAccessException;
import java.util.Optional;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;


import static com.example.sw_planet_api.common.PlanetConstants.PLANET;
import static com.example.sw_planet_api.common.PlanetConstants.TATOOINE;

@DataJpaTest //cria um banco em memoria para testes (fake strategy)
class PlanetRepositoryTest {
    
    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach() {
        PLANET.setId(null);
    }

    @Test
    void createPlanet_WithValidData_ReturnPlanet() {
        Planet planet = planetRepository.save(PLANET);
    
        Planet sut = testEntityManager.find(Planet.class, planet.getId());
    
        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(planet.getName());
        assertThat(sut.getClimate()).isEqualTo(planet.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(planet.getTerrain());
    }

    @Test
    void createPlanet_WithInvalidData_ThrowsException() {
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("", "", "");

        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createPlanet_WithExistingname_ThrowsException() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet);
        planet.setId(null);

        assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void getPlanet_ByExistingId_ReturnPlanet() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        Optional<Planet> planeOptional = planetRepository.findById(planet.getId());

        assertThat(planeOptional)
                .isNotEmpty()
                .contains(planet);

    }

    @Test
    void getPlanet_ByUnexistingId_ReturnEmpty() {
        Optional<Planet> planeOptional = planetRepository.findById(1L);
        assertThat(planeOptional).isEmpty();
    }

    @Test
    void getPlanet_ByExistingName_ReturnPlanet() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        Optional<Planet> planeOptional = planetRepository.findByName(planet.getName());

        assertThat(planeOptional)
            .isNotEmpty()
            .contains(planet);
    }

    @Test
    void getPlanet_ByUnexistingName_ReturnEmpty() {
        Optional<Planet> planeOptional = planetRepository.findByName(PLANET.getName());
        assertThat(planeOptional).isEmpty();
    }

    @Sql(scripts = "/import_planets.sql")
    @Test
    void listPlanets_ReturnsFilteredPlanets() {
        Example<Planet> queryWithoutFilters = QueryBuilder.makeQuery(new Planet());
        Example<Planet> queryWithFilters = QueryBuilder.makeQuery(new Planet(TATOOINE.getClimate(), TATOOINE.getTerrain()));

        List<Planet> responseWithoutFilters = planetRepository.findAll(queryWithoutFilters);
        List<Planet> responseWithFilters = planetRepository.findAll(queryWithFilters);

        assertThat(responseWithoutFilters)
            .isNotEmpty()
            .hasSize(3);

        assertThat(responseWithFilters)
            .isNotEmpty()
            .hasSize(1)
            .contains(TATOOINE);
    }

    @Test
    void listPlanets_ReturnsNoPlanets() {
        Example<Planet> query = QueryBuilder.makeQuery(new Planet());

        List<Planet> response = planetRepository.findAll(query);

        assertThat(response).isEmpty();
    }

    @Test
    void removePlanet_WithExistingId_RemovesPlanetFromDatabase() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        planetRepository.deleteById(planet.getId());

        Planet sut = testEntityManager.find(Planet.class, planet.getId());

        assertThat(sut).isNull();
    }

    @Test
    void removePlanet_WithUnexistingId_ThrowsException() {
      assertThatThrownBy(() -> planetRepository.deleteById(1L)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
