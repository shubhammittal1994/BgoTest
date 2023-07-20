package com.bango.bangoLive.audioLiveStreamingAdapterClasses;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.databinding.LayoutMultiUserLiveListBinding;
import com.bango.bangoLive.fragments.Gifts.Model.GoLiveModelClass;
import com.bango.bangoLive.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MultiLiveAudioAdapter extends RecyclerView.Adapter<MultiLiveAudioAdapter.ViewHolder> {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference emojiRef = firebaseDatabase.getReference().child("emojiRef");
    private final DatabaseReference lockSeat = firebaseDatabase.getReference().child("lockSeat");
    private final DatabaseReference adminLiveRef = firebaseDatabase.getReference().child("adminLiveRef");
    private final DatabaseReference ref = firebaseDatabase.getReference().child("userInfo");

    private String id = App.getSharedpref().getString("id");


    private Context context;
    private List<GoLiveModelClass> goLiveModelClasses;
    private Click click;
    public static String directHostId;
    private TextView sitOnSeatTv, openSeatTv, micTV, kickOutTv, ProfileTv, inviteAudienceTv;
    public static String adminId = "0", liveType, peerUid ="", volume = "",profileAdminCheck="0";

    private final int minVolume = 10;
    private final int maxVolume = 140;

    //this is for emoji
    public static String userId, emoji, hostId, status;

    public MultiLiveAudioAdapter(Context context, List<GoLiveModelClass> goLiveModelClasses, Click click) {
        this.context = context;
        this.goLiveModelClasses = goLiveModelClasses;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutMultiUserLiveListBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (goLiveModelClasses.get(position).getSvga() != null) {
//            Toast.makeText(context, "Adapter :-"+ goLiveModelClasses.get(position).getSvga(), Toast.LENGTH_SHORT).show();
            CommonUtils.setAnimation(holder.itemView.getContext(),goLiveModelClasses.get(position).getSvga(), holder.binding.profieFrame);
        }else {
            Toast.makeText(context, "svga null", Toast.LENGTH_SHORT).show();
        }

        if (!goLiveModelClasses.get(position).getUserID().equalsIgnoreCase("")){

            seatLock(holder, position);
            //this for emoji if user joined live
            if (goLiveModelClasses.get(position).getUserID().equalsIgnoreCase(userId) && userId != null) {

                Toast.makeText(context, "user not joined 1", Toast.LENGTH_SHORT).show();
                holder.binding.emojiImg.setVisibility(View.VISIBLE);
                Glide.with(context).load(emoji).into(holder.binding.emojiImg);

                Log.d("ADAPTER", "emoji: " + emoji);

                new Handler().postDelayed(() -> holder.binding.emojiImg.setVisibility(View.GONE), 5000);
                emoji = null;
                emojiRef.child(hostId).removeValue();

            } else if (hostId != null) {

                //if user not join live but send emoji
                Toast.makeText(context, "user not joined", Toast.LENGTH_SHORT).show();
//                click.showEmojiBackToActivity(emoji,userId,hostId);
                emojiRef.child(hostId).child("status").setValue("1");
            }

            Glide.with(context).load(goLiveModelClasses.get(position).getImage()).into(holder.binding.imgUserProfile);
            holder.binding.txtUserName.setText((!goLiveModelClasses.get(position).getName().equalsIgnoreCase("")) ? goLiveModelClasses.get(position).getName() : "User not set name");
            holder.binding.rlMic.setVisibility(View.VISIBLE);

        } else {
            holder.binding.rlMic.setVisibility(View.GONE);
//          holder.binding.txtUserName.setText("Seat No:- " + (position + 1));
            holder.binding.txtUserName.setText(" " + (position + 1));
            seatLock(holder, position);
        }

        holder.binding.rlMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goLiveModelClasses.get(position).getMute().equalsIgnoreCase("1")) {
                    click.muteMic(goLiveModelClasses.get(position), holder.binding.imgMic, "0");
                } else {
                    click.muteMic(goLiveModelClasses.get(position), holder.binding.imgMic, "1");
                }
            }
        });

        if (goLiveModelClasses.get(position).getMute().equalsIgnoreCase("1")) {
            holder.binding.imgMic.setImageResource(R.drawable.ic_baseline_mic_24);
            holder.binding.imgMic.setVisibility(View.GONE);

        } else {
            holder.binding.imgMic.setVisibility(View.VISIBLE);
            holder.binding.imgMic.setImageResource(R.drawable.ic_baseline_mic_off_24);
            holder.binding.voiceIndicationLottie.setVisibility(View.GONE);
            holder.binding.voiceIndicationLottie.cancelAnimation();
        }
    }

    @Override
    public int getItemCount() {
        return goLiveModelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutMultiUserLiveListBinding binding;
        public ViewHolder(@NonNull LayoutMultiUserLiveListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface Click {
        void setOnUserKickListener(GoLiveModelClass goLiveModelClass, String admin);

        void setOnShowUserProfile(GoLiveModelClass goLiveModelClass, int adminStatus, String adminIdThroughCallback);

        void muteMic(GoLiveModelClass goLiveModelClass, AppCompatImageView muteImg, String muteStatus);

        void showEmojiBackToActivity(String emoji, String userId, String hostId);

        void selectSeat(GoLiveModelClass goLiveModelClass, String positon);

        void lockSeat(GoLiveModelClass goLiveModelClass, String positon);

        void inviteForSeat(String position);

    }


    private void seatLock(ViewHolder holder, int position) {
        checkAdmin();
        lockSeat.child(directHostId).child("s" + String.valueOf(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    if (snapshot.getValue().toString().equalsIgnoreCase("1")) {
                        holder.binding.imgChair.setImageResource(R.drawable.lock);
                    } else if (snapshot.getValue().toString().equalsIgnoreCase("0")) {
                        holder.binding.imgChair.setImageResource(R.drawable.chair);
                        lockSeat.child(directHostId).child("s" + String.valueOf(position)).removeValue();
                    } else {
                        holder.binding.imgChair.setImageResource(R.drawable.chair);
                    }
                    holder.binding.imgUserProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (directHostId.equalsIgnoreCase(id) || adminId.equalsIgnoreCase(id)) {
                                //1 means seat locked
                                if (snapshot.getValue().toString().equalsIgnoreCase("1")) {
                                    dialogbox(holder, position, "1");
                                } else {
                                    dialogbox(holder, position, "0");
                                }
//                                if(goLiveModelClasses.get(position).getUserID().equalsIgnoreCase(adminId) && adminId.equalsIgnoreCase(AppConstants.USER_ID)){
//                                    click.selectSeat(goLiveModelClasses.get(position), String.valueOf(position));
//                                    Toast.makeText(context,"both are admin exits", Toast.LENGTH_SHORT).show();
//                                }
                            } else {
                                if (snapshot.getValue().toString().equalsIgnoreCase("1")) {
                                } else {
                                    if (!goLiveModelClasses.get(position).getUserID().equalsIgnoreCase("")) {
                                    } else {
                                        click.selectSeat(goLiveModelClasses.get(position), String.valueOf(position));
                                    }
                                }
                            }
                        }
                    });

                } else {
                    holder.binding.imgUserProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (directHostId.equalsIgnoreCase(id) || adminId.equalsIgnoreCase(id)) {
                                if (!goLiveModelClasses.get(position).getUserID().equalsIgnoreCase("")) {
                                    //this for when double admin you and other admin profile
                                    adminLiveRef.child(directHostId).child(goLiveModelClasses.get(position).getUserID()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                profileAdminCheck = snapshot.child("adminId").getValue().toString();
                                                Toast.makeText(context, "otherUserAdmin "+profileAdminCheck, Toast.LENGTH_SHORT).show();
                                                if (adminId.equalsIgnoreCase(id)){
                                                    click.setOnShowUserProfile(goLiveModelClasses.get(position), 1, profileAdminCheck);
                                                }else if (directHostId.equalsIgnoreCase(id)){
                                                    dialogbox(holder, position, "0");
                                                }
                                            }else{
                                                profileAdminCheck="";
                                                dialogbox(holder, position, "0");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }else{
                                    dialogbox(holder, position, "0");
                                }

//                                if(goLiveModelClasses.get(position).getUserID().equalsIgnoreCase(adminId) && adminId.equalsIgnoreCase(AppConstants.USER_ID)){
//                                    click.selectSeat(goLiveModelClasses.get(position), String.valueOf(position));
//                                    Toast.makeText(context,"both are admin not exits", Toast.LENGTH_SHORT).show();
//                                }

                            } else {
                                if (goLiveModelClasses.get(position).getUserID().equalsIgnoreCase(id)) {
                                    dialogbox(holder, position, "2"); //2 means want to stand from the seat that user who dont have a admin and host
                                } else {
                                    if (!goLiveModelClasses.get(position).getUserID().equalsIgnoreCase("")) {
                                        int adminInt = 0;
                                        if (goLiveModelClasses.get(position).getUserID().equalsIgnoreCase(adminId)) {
                                            adminInt = 1;
                                            Toast.makeText(context, "admin11", Toast.LENGTH_SHORT).show();
                                        } else {
                                            adminInt = 0;
                                            Toast.makeText(context, "not admin22", Toast.LENGTH_SHORT).show();
                                        }
                                        click.setOnShowUserProfile(goLiveModelClasses.get(position), adminInt, adminId);
                                    } else {
                                        click.selectSeat(goLiveModelClasses.get(position), String.valueOf(position));
                                    }

//                                    if(goLiveModelClasses.get(position).getUserID().equalsIgnoreCase(adminId) && adminId.equalsIgnoreCase(AppConstants.USER_ID)){
//                                        click.selectSeat(goLiveModelClasses.get(position), String.valueOf(position));
//                                        Toast.makeText(context,"both are admin not exits", Toast.LENGTH_SHORT).show();
//                                    }

                                }

                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void checkAdmin() {

        adminLiveRef.child(directHostId).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    adminId = snapshot.child("adminId").getValue().toString();
                }else{
                    adminId="";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void dialogbox(ViewHolder holder, int position, String s) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.click_on_live_users_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.show();

        openSeatTv = dialog.findViewById(R.id.openSeatTv);
        sitOnSeatTv = dialog.findViewById(R.id.sitOnSeatTv);
        micTV = dialog.findViewById(R.id.micTV);
        kickOutTv = dialog.findViewById(R.id.kickOutTv);
        ProfileTv = dialog.findViewById(R.id.ProfileTv);
        inviteAudienceTv = dialog.findViewById(R.id.inviteAudienceTv);

        if (s.equalsIgnoreCase("2")) {
            openSeatTv.setVisibility(View.GONE);
            inviteAudienceTv.setVisibility(View.GONE);
            sitOnSeatTv.setVisibility(View.VISIBLE);

        } else {
            openSeatTv.setVisibility(View.VISIBLE);
            inviteAudienceTv.setVisibility(View.VISIBLE);
            sitOnSeatTv.setVisibility(View.VISIBLE);
        }

        if (!goLiveModelClasses.get(position).getUserID().equalsIgnoreCase("")) {

            if (adminId.equalsIgnoreCase(id) || directHostId.equalsIgnoreCase(id)) {

                openSeatTv.setVisibility(View.GONE);
                inviteAudienceTv.setVisibility(View.GONE);
                ProfileTv.setVisibility(View.VISIBLE);
                kickOutTv.setVisibility(View.VISIBLE);
                micTV.setVisibility(View.VISIBLE);
                sitOnSeatTv.setVisibility(View.VISIBLE);


                sitOnSeatTv.setText("Stand");

                //mute unmute mic
                if (goLiveModelClasses.get(position).getMute().equalsIgnoreCase("1")) {
                    holder.binding.imgMic.setImageResource(R.drawable.ic_baseline_mic_24);
                    micTV.setText("Mute mic");

                } else {
                    holder.binding.imgMic.setImageResource(R.drawable.ic_baseline_mic_off_24);
                    micTV.setText("Unmute mic");
                }

                ProfileTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (adminId.equalsIgnoreCase(id)) {

                            Toast.makeText(context, "admin", Toast.LENGTH_SHORT).show();
                            click.setOnShowUserProfile(goLiveModelClasses.get(position), 1, adminId);

                        } else if (directHostId.equalsIgnoreCase(id)) {
                            Toast.makeText(context, "host admin", Toast.LENGTH_SHORT).show();
                            click.setOnShowUserProfile(goLiveModelClasses.get(position), 0, id);
                        } else {
                            Toast.makeText(context, "user admi", Toast.LENGTH_SHORT).show();
                            click.setOnShowUserProfile(goLiveModelClasses.get(position), 0, adminId);
                        }

                    }
                });

            } else {

                openSeatTv.setVisibility(View.GONE);
                inviteAudienceTv.setVisibility(View.GONE);


                if (goLiveModelClasses.get(position).getUserID().equalsIgnoreCase(id)) {
                    sitOnSeatTv.setVisibility(View.VISIBLE);
                    sitOnSeatTv.setText("Stand");
                } else {

                    int adminInt = 0;
                    if (goLiveModelClasses.get(position).getUserID().equalsIgnoreCase(adminId)) {
                        adminInt = 1;
                        Toast.makeText(context, "admin", Toast.LENGTH_SHORT).show();
                    } else {
                        adminInt = 0;
                        Toast.makeText(context, "not admin 12", Toast.LENGTH_SHORT).show();
                    }
                    click.setOnShowUserProfile(goLiveModelClasses.get(position), adminInt, adminId);
                }
            }
            micTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (adminId.equalsIgnoreCase(id) || directHostId.equalsIgnoreCase(id)) {

                        if (goLiveModelClasses.get(position).getMute().equalsIgnoreCase("1")) {
                            click.muteMic(goLiveModelClasses.get(position), holder.binding.imgMic, "0");
                            dialog.dismiss();
                        } else {
                            click.muteMic(goLiveModelClasses.get(position), holder.binding.imgMic, "1");
                            dialog.dismiss();
                        }
                    }
                }
            });

            kickOutTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (adminId.equalsIgnoreCase(id) || directHostId.equalsIgnoreCase(id)) {

                        click.setOnUserKickListener(goLiveModelClasses.get(position), adminId);
                        dialog.dismiss();
                    }
                }
            });
        } else {
            if (adminId.equalsIgnoreCase(id) || directHostId.equalsIgnoreCase(id)) {

                if (adminId.equalsIgnoreCase(id)) {
                    sitOnSeatTv.setText("Sit");
                } else {
                    if (sitOnSeatTv.getText().toString().equalsIgnoreCase("Sit")) {
                        sitOnSeatTv.setVisibility(View.GONE);
                    } else if (directHostId.equalsIgnoreCase(id)) {
                        sitOnSeatTv.setVisibility(View.VISIBLE);
                    }
                }
                if (s.equalsIgnoreCase("1")) {
                    openSeatTv.setText("Open");
                } else {
                    openSeatTv.setText("Close");
                }
            } else {
                sitOnSeatTv.setText("Sit");
            }
            //lock open seat
            openSeatTv.setOnClickListener(view -> {
                if (s.equalsIgnoreCase("1")) {
                    lockSeat.child(directHostId).child("s" + String.valueOf(position)).setValue("0");
                    dialog.dismiss();
                } else {
                    lockSeat.child(directHostId).child("s" + String.valueOf(position)).setValue("1"); //1 for true
                    dialog.dismiss();
                }
            });

            inviteAudienceTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.inviteForSeat(String.valueOf(position));
                }
            });
        }

        sitOnSeatTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sitOnSeatTv.getText().toString().equalsIgnoreCase("Sit")) {
                    if (!directHostId.equalsIgnoreCase(id)) {
                        click.selectSeat(goLiveModelClasses.get(position), String.valueOf(position));
                        dialog.dismiss();
                    } else {
                    }
                } else {
                    if (adminId.equalsIgnoreCase(id) || directHostId.equalsIgnoreCase(id)
                            || goLiveModelClasses.get(position).getUserID().equalsIgnoreCase(id)) {

                        ref.child(directHostId).child(liveType).child(directHostId).child("multiLiveRequest").child(goLiveModelClasses.get(position).getUserID())
                                .child("requestStatus").setValue("2");
                        dialog.dismiss();
                    }
                }
            }
        });
    }

}
