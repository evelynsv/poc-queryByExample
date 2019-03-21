package com.sample.queryByExample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.queryByExample.domain.Passenger;
/**
 * 
 * @author EvelynVieira
 *
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
