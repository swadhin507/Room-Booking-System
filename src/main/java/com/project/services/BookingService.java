package com.project.services;

import com.project.dao.BookingRepository;
import com.project.dao.GuestRepository;
import com.project.dao.RoomRepository;
import com.project.dto.BookingRequestDto;
import com.project.entities.Booking;
import com.project.entities.Guest;
import com.project.entities.Room;
import com.project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {


    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository,RoomRepository roomRepository,GuestRepository guestRepository){
        this.bookingRepository = bookingRepository;
        this.roomRepository= roomRepository;
        this.guestRepository= guestRepository;
    }



    public List<Booking> getAll() {
        return this.bookingRepository.findAll();
    }

    public Booking getById(long id) {

            return this.bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    public Booking addBooking(BookingRequestDto dto){

        Guest guest = guestRepository
                .findById(dto.getGuestId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));

        Room room = roomRepository
                .findById(dto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        Booking booking = new Booking();

        booking.setGuest(guest);
        booking.setRoom(room);
        booking.setCheckIn(dto.getCheckIn());
        booking.setCheckOut(dto.getCheckOut());

        return bookingRepository.save(booking);
    }

    public void deleteAll(){
        this.bookingRepository.deleteAll();
    }
    public void deleteById(long id){
       Booking booking = bookingRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
       bookingRepository.delete(booking);
    }
    public Booking updateBooking(BookingRequestDto dto,long id){
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));
        Guest guest = guestRepository.findById(dto.getGuestId())
                .orElseThrow(()-> new ResourceNotFoundException("Guest not found"));
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        existingBooking.setGuest((guest));
        existingBooking.setRoom(room);
        existingBooking.setCheckIn(dto.getCheckIn());
        existingBooking.setCheckOut(dto.getCheckOut());

        return bookingRepository.save(existingBooking);
    }
}
