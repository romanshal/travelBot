package com.shalabodov.onTravelSolutions.repository;

import com.shalabodov.onTravelSolutions.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository <City, Long> {

    @Transactional
    @Query(value = "select * from cities where city=:city", nativeQuery = true)
    Optional <City> getByCity(@Param("city") String city);

    @Modifying
    @Transactional
    @Query(value = "delete from cities where city=:city", nativeQuery = true)
    int deleteCity(@Param("city") String city);

    @Modifying
    @Transactional
    @Query(value = "update cities set description=:description where city=:city", nativeQuery = true)
    int updateCity(@Param("description") String description, @Param("city") String city);
}
