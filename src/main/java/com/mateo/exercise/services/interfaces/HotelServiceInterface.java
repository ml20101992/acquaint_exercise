package com.mateo.exercise.services.interfaces;

import com.mateo.exercise.data.models.HotelModel;
import com.mateo.exercise.util.exceptions.service.HotelNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface HotelServiceInterface {
    Optional<HotelModel> getHotel(long id);
    Page<HotelModel> getHotels(int pageNum);
    HotelModel createHotel(HotelModel model);
    HotelModel modifyHotel(HotelModel model) throws HotelNotFoundException;
    boolean deleteHotel(long hotelId);

}
