package com.project.service;

import com.project.enums.RoomStatus;
import com.project.exception.InvalidBookingDateException;
import com.project.exception.RoomUnavailableException;
import com.project.repository.BookingRepository;
import com.project.repository.GuestRepository;
import com.project.repository.RoomRepository;
import com.project.dto.BookingRequestDto;
import com.project.entities.Booking;
import com.project.entities.Guest;
import com.project.entities.Room;
import com.project.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Booking addBooking(BookingRequestDto dto){


        Guest guest = guestRepository
                .findById(dto.getGuestId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));

        Room room = roomRepository
                .findById(dto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        if(room.getStatus()==RoomStatus.BOOKED){
            throw new RoomUnavailableException(("Room Already Booked"));
        }
        Booking booking = new Booking();

        booking.setGuest(guest);
        booking.setRoom(room);
        if(dto.getCheckOut().isBefore(dto.getCheckIn())){
            throw new InvalidBookingDateException("Check-out cannot be before check-in");
        }
        booking.setCheckIn(dto.getCheckIn());
        booking.setCheckOut(dto.getCheckOut());

        room.setStatus(RoomStatus.BOOKED);
        roomRepository.save(room);
        return bookingRepository.save(booking);
    }

    public void deleteAll(){
        this.bookingRepository.deleteAll();
    }
    public void deleteById(long id){
       Booking booking = bookingRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
       Room room = booking.getRoom();
       room.setStatus(RoomStatus.AVAILABLE);
       roomRepository.save(room);
       bookingRepository.delete(booking);
    }
    @Transactional
    public Booking updateBooking(BookingRequestDto dto,long id){
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));
        Guest guest = guestRepository.findById(dto.getGuestId())
                .orElseThrow(()-> new ResourceNotFoundException("Guest not found"));
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        if(room.getStatus()== RoomStatus.BOOKED&& !existingBooking.getRoom().getId().equals(room.getId())){
            throw new RoomUnavailableException("Room Already Booked");
        }

        existingBooking.setGuest((guest));
        existingBooking.setRoom(room);
        if(dto.getCheckOut().isBefore(dto.getCheckIn())) {
            throw new InvalidBookingDateException("Check-out cannot be before check-in");
        }
        existingBooking.setCheckIn(dto.getCheckIn());
        existingBooking.setCheckOut(dto.getCheckOut());


        return bookingRepository.save(existingBooking);
    }
}
