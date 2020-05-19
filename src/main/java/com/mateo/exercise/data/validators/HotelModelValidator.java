package com.mateo.exercise.data.validators;

import com.mateo.exercise.data.models.HotelModel;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class HotelModelValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return HotelModel.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "hotelName", "hotelName.empty");
        ValidationUtils.rejectIfEmpty(errors, "address", "address.empty");
        ValidationUtils.rejectIfEmpty(errors, "zip", "zip.empty");
        ValidationUtils.rejectIfEmpty(errors, "country", "country.empty");
    }
}
