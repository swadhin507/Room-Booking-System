package com.project.service;

import com.project.enums.BookingStatus;
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
import java.time.LocalDate;

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

        // VALIDATE DATES
        if(!dto.getCheckOut().isAfter(dto.getCheckIn())){
            throw new InvalidBookingDateException(
                    "Check-out must be after check-in");
        }
        // PREVENT PAST BOOKINGS
        if(dto.getCheckIn().isBefore(LocalDate.now())){
            throw new InvalidBookingDateException(
                    "Check-in cannot be in the past");
        }

        // FIND GUEST
        Guest guest = guestRepository
                .findById(dto.getGuestId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found"));

        // FIND ROOM
        Room room = roomRepository
                .findById(dto.getRoomId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Room not found"));

        // CHECK ROOM OPERATIONAL STATUS
        if(room.getStatus() != RoomStatus.AVAILABLE){
            throw new RoomUnavailableException(
                    "Room is not available for booking");
        }

        // CHECK DATE OVERLAP CONFLICTS
        List<Booking> conflicts =
                bookingRepository.findConflictingBookings(
                        dto.getRoomId(),
                        dto.getCheckIn(),
                        dto.getCheckOut()
                );

        if(!conflicts.isEmpty()){
            throw new RoomUnavailableException(
                    "Room already booked for selected dates");
        }

        // CREATE BOOKING
        Booking booking = new Booking();

        booking.setGuest(guest);
        booking.setRoom(room);
        booking.setCheckIn(dto.getCheckIn());
        booking.setCheckOut(dto.getCheckOut());
        booking.setStatus(BookingStatus.CONFIRMED);

        // SAVE BOOKING
        return bookingRepository.save(booking);
    }
    @Transactional
    public void cancelAllBookings(){

        List<Booking> bookings = bookingRepository.findAll();

        for(Booking booking : bookings){
            booking.setStatus(BookingStatus.CANCELLED);
        }

        bookingRepository.saveAll(bookings);
    }
    @Transactional
    public Booking cancelBooking(long id){

        // FIND BOOKING
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found"));

        // PREVENT REPEATED CANCELLATION
        if(booking.getStatus() == BookingStatus.CANCELLED){
            throw new RoomUnavailableException(
                    "Booking is already cancelled");
        }

        // CANCEL BOOKING
        booking.setStatus(BookingStatus.CANCELLED);

        // SAVE UPDATED BOOKING
        return bookingRepository.save(booking);
    }
    @Transactional
    public Booking updateBooking(BookingRequestDto dto, long id){

        // FIND EXISTING BOOKING
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found"));
        if(existingBooking.getStatus() == BookingStatus.CANCELLED){
            throw new RoomUnavailableException(
                    "Cancelled booking cannot be updated");
        }
        // VALIDATE DATES
        if(!dto.getCheckOut().isAfter(dto.getCheckIn())){
            throw new InvalidBookingDateException(
                    "Check-out must be after check-in");
        }

        // PREVENT PAST BOOKINGS
        if(dto.getCheckIn().isBefore(LocalDate.now())){
            throw new InvalidBookingDateException(
                    "Check-in cannot be in the past");
        }

        // FIND GUEST
        Guest guest = guestRepository.findById(dto.getGuestId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found"));

        // FIND ROOM
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Room not found"));

        // CHECK ROOM OPERATIONAL STATUS
        if(room.getStatus() != RoomStatus.AVAILABLE){
            throw new RoomUnavailableException(
                    "Room is not available for booking");
        }

        // CHECK DATE OVERLAP CONFLICTS
        List<Booking> conflicts =
                bookingRepository.findConflictingBookingsForUpdate(
                        dto.getRoomId(),
                        id,
                        dto.getCheckIn(),
                        dto.getCheckOut()
                );

        if(!conflicts.isEmpty()){
            throw new RoomUnavailableException(
                    "Room already booked for selected dates");
        }

        // UPDATE BOOKING
        existingBooking.setGuest(guest);
        existingBooking.setRoom(room);
        existingBooking.setCheckIn(dto.getCheckIn());
        existingBooking.setCheckOut(dto.getCheckOut());

        // SAVE UPDATED BOOKING
        return bookingRepository.save(existingBooking);
    }
}
