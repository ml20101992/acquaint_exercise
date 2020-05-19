package com.mateo.exercise.controllers;

import com.mateo.exercise.data.models.HotelModel;
import com.mateo.exercise.http.models.requests.HotelCreateRequest;
import com.mateo.exercise.http.models.requests.HotelUpdateRequest;
import com.mateo.exercise.http.models.responses.HotelDeleteResponse;
import com.mateo.exercise.http.models.responses.HttpErrorResponse;
import com.mateo.exercise.http.models.responses.MultipleHotelResponse;
import com.mateo.exercise.http.models.responses.SingleHotelResponse;
import com.mateo.exercise.services.interfaces.HotelServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hotels", produces = MediaType.APPLICATION_JSON_VALUE)
public class HotelController {

    @Autowired
    private HotelServiceInterface hotelService;


    @GetMapping(value = "/single")
    public ResponseEntity<SingleHotelResponse> getHotelById(@RequestParam(value = "id", required = true) long id) {

        if(id < 1) throw new IllegalArgumentException("Parameter id must be greater than zero");


        Optional<HotelModel> model = hotelService.getHotel(id);

        if(model.isPresent()) {
            return ResponseEntity.status(200).body(new SingleHotelResponse(model.get()));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Could not find hotel with ID %d", id)
            );
        }
    }


    //stavi kao query param u smislu ?page= ubaci i page size value sa nekim defaultsima
    @GetMapping(value = "/all")
    public MultipleHotelResponse getAllHotels(@RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) int page) {


        Page<HotelModel> data = hotelService.getHotels(page);

        return new MultipleHotelResponse(data.getContent(), page, data.getTotalPages());
    }

    @PostMapping("/create")
    public ResponseEntity createHotel(@RequestBody @Valid HotelCreateRequest requestBody) {
        HotelModel hotel = new HotelModel(requestBody.getName(), requestBody.getAddress(), requestBody.getZip(), requestBody.getCountry());

        HotelModel saved = hotelService.createHotel(hotel);

        return  ResponseEntity.status(201).body(new SingleHotelResponse(saved, "Hotel Create Successful"));
    }

    @PutMapping("/update")
    public ResponseEntity<SingleHotelResponse> modifyHotel(@RequestBody @Valid HotelUpdateRequest requestBody) {
        HotelModel model = new HotelModel(requestBody.getId(), requestBody.getName(), requestBody.getAddress(), requestBody.getZip(), requestBody.getCountry());

        HotelModel modified = hotelService.modifyHotel(model);

        return ResponseEntity.status(200).body(new SingleHotelResponse(modified, "Hotel Modified Successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HotelDeleteResponse> deleteHotel(@RequestParam(value="id") @Min(1) int id) {

        hotelService.deleteHotel(id);

        return ResponseEntity.status(204).body(new HotelDeleteResponse(String.format("Hotel with ID %d deleted", id)));
    }
}
