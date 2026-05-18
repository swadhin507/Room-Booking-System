package com.project.services;

import com.project.dao.RoomRepository;
import com.project.entities.Room;
import com.project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {


    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms(){
        List<Room> rooms = this.roomRepository.findAll();

        for(Room room: rooms){

            if(room.getType() != null){
                room.setType(room.getType().toUpperCase());
            }
            if(room.getStatus()!= null){
                room.setStatus(room.getStatus().toUpperCase());
            }
        }
        return rooms;
    }

    public Room getRoomById(long id){
            return this.roomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Room not found"));
    }

    public Room addRoom(Room room){
        return this.roomRepository.save(room);
    }

    public void deleteRoom(long id){
       Room room = roomRepository.findById(id)
               .orElseThrow(()->new ResourceNotFoundException("Room not found"));
    }

    public void deleteAll() {
        this.roomRepository.deleteAll();
    }

    public Room updateRoom(Room room,long id){
        room.setId(id);
        return this.roomRepository.save(room);
    }


}
