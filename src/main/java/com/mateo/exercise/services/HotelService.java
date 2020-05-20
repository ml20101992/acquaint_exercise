package com.mateo.exercise.services;

import com.mateo.exercise.data.models.HotelModel;
import com.mateo.exercise.data.repositories.HotelRepository;
import com.mateo.exercise.services.interfaces.HotelServiceInterface;
import com.mateo.exercise.util.exceptions.service.HotelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HotelService implements HotelServiceInterface {

    @Autowired
    private HotelRepository hotelRepository;

    private final int PAGE_SIZE = 10;

    @Override
    public Optional<HotelModel> getHotel(long id) {
        return hotelRepository.findById(id);
    }

    /**
     * Method used to get paginated hotel data
     * @param pageNum number as seen in pagination (starts from 1)
     * @return page with hotel data
     */
    @Override
    @Cacheable("hotels")
    public Page<HotelModel> getHotels(int pageNum) {
        int page = (pageNum - 1);

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        return hotelRepository.findAll(pageable);
    }

    @Override
    @CacheEvict(value = "hotels", allEntries = true)
    public HotelModel createHotel(HotelModel model) {
        return hotelRepository.save(model);
    }

    @Override
    @CacheEvict(value = "hotels", allEntries = true)
    public HotelModel modifyHotel(HotelModel model) throws HotelNotFoundException {
        //get the model in database
        Optional<HotelModel> dbModelOptional = hotelRepository.findById(model.getId());

        if(!dbModelOptional.isPresent()) throw new HotelNotFoundException();

        AtomicReference<HotelModel> updated = new AtomicReference<>();

        dbModelOptional.ifPresent(dbModel -> {
            dbModel.setHotelName(model.getHotelName());
            dbModel.setAddress(model.getAddress());
            dbModel.setZip(model.getZip());
            dbModel.setCountry(model.getCountry());
            updated.set(dbModel);
        });

        return hotelRepository.save(updated.get());

    }

    @Override
    @CacheEvict(value = "hotels", allEntries = true)
    public boolean deleteHotel(long hotelId) {
        HotelModel model = new HotelModel();
        model.setId(hotelId);
        hotelRepository.delete(model);
        return true;
    }
}
