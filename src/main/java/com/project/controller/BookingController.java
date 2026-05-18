package com.project.controller;

import com.project.dto.BookingRequestDto;
import com.project.entities.Booking;
import com.project.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
     private BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<Booking>> getAll(){
        List<Booking> list = this.bookingService.getAll();
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable long id){
        Booking booking = this.bookingService.getById(id);
        return ResponseEntity.ok(booking);
    }
    @PostMapping
    public ResponseEntity<Booking> addBooking(@Valid @RequestBody BookingRequestDto dto){
        try{
            Booking b=this.bookingService.addBooking(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(b);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        try{
            this.bookingService.deleteAll();
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        try{
            this.bookingService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@RequestBody BookingRequestDto dto,@PathVariable long id){
        try{
            Booking b = this.bookingService.updateBooking(dto,id);
            return ResponseEntity.ok(b);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
