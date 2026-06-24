package com.example.backend.helper.validator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import com.example.backend.entity.dto.AppointmentDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AppoinetmentValidator implements ConstraintValidator<ValidAppoinement,AppointmentDTO>{
    
    private static final LocalTime OPENING_HOUR = LocalTime.of(9,0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(17,0);

    @Override
    public boolean isValid(AppointmentDTO dto, ConstraintValidatorContext context) {
       if(dto == null || dto.getAppointmentDate() == null || dto.getAppointmentTime() == null){
        return true;
       }

       LocalTime time = dto.getAppointmentTime();
       LocalDate date = dto.getAppointmentDate();
       boolean valid = true;
       context.disableDefaultConstraintViolation();

       if(!date.isAfter(LocalDate.now())){
        context.buildConstraintViolationWithTemplate("Appointment date must be in the future")
               .addPropertyNode("appointmentDate")
               .addConstraintViolation();
        valid = false;
       }

       DayOfWeek day = date.getDayOfWeek();
       if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
        context.buildConstraintViolationWithTemplate("Clinic is closed on weekends")
                .addPropertyNode("appointmentDate")
                .addConstraintViolation();
        valid = false;
       }

       if(time.isBefore(OPENING_HOUR) || time.isAfter(CLOSING_TIME)){
        context.buildConstraintViolationWithTemplate("Appointment time must be between 9:00 to 17:00")
                .addPropertyNode("appointmentTime")
                .addConstraintViolation();
        return false;
       }

       return valid;
    }
}
