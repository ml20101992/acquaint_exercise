package com.mateo.exercise.data.repositories;

import com.mateo.exercise.data.models.HotelModel;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface HotelRepository extends PagingAndSortingRepository<HotelModel, Long> {
}
