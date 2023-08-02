package com.bango.bangoLive.ZegoServices.internalBusiness.audioroom;

import java.util.List;

public interface RoomSeatServiceListener {

    default void onSeatChanged(List<LiveAudioRoomSeat> changedSeats){}
}
