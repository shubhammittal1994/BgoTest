package com.bango.bangoLive.fragments.Wallet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.fragments.Wallet.Adapter.AdapterCard;
import com.bango.bangoLive.fragments.Wallet.Model.CardModel;
import com.bango.bangoLive.fragments.Wallet.Model.OrderModel;
import com.bango.bangoLive.fragments.Wallet.Model.SavedCard;

import java.util.ArrayList;

public class CardFragment extends Fragment {
    private View view;

    private EditText editCardNumber, editHolderName, editExpiryDate, editCvv, emailId,name,add_line_one,add_line_two,pincode;
    private ArrayList<Integer> image;
    private RecyclerView recyclerCard;
    private ImageView imageBack;
    private Button payButton;
    private int save = 0;
    private SwitchCompat switch1;
    String dataProductId, orderId, customerId;
    private TextView addCard;

    SharedPreferences sharedpreferences;

    String profileName,profileId,profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_card, container, false);
        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");

        if (getArguments() != null)
        {
            dataProductId = getArguments().getString("purchasedId");
        }

        findIds();
        getOrderId();
        getCardDetail();
        clicks();
        return view;
    }



    private void getCardDetail() {

        new ApiViewModel().getSaveCard(requireActivity(), profileId).observe(requireActivity(), new Observer<SavedCard>() {
            @Override
            public void onChanged(SavedCard cardDetails) {
                if (cardDetails.getStatus() == 1) {
                    addCard.setVisibility(View.GONE);
                    recyclerCard.setVisibility(View.VISIBLE);

                    recyclerCard.setAdapter(new AdapterCard(cardDetails.getDetails(), new AdapterCard.GetCardDetails() {
                        @Override
                        public void getCardDetails(SavedCard.Detail detail) {
                            Dialog itemView = new Dialog(requireContext());

                            itemView.setContentView(R.layout.card_layout_two);

                            itemView.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            TextView txtCardName, txtExpiry, txtName;
                            EditText txtcvv;
                            AppCompatButton payButton;
                            ImageView cardImage, img1;
                            txtcvv = itemView.findViewById(R.id.txt6);
                            payButton = itemView.findViewById(R.id.payButton);
                            txtCardName = itemView.findViewById(R.id.txt1);
                            txtExpiry = itemView.findViewById(R.id.txt4);
                            txtName = itemView.findViewById(R.id.txt5);
                            cardImage = itemView.findViewById(R.id.cardImage);
                            img1 = itemView.findViewById(R.id.img1);

                            txtCardName.setText(detail.getCardNumber());
                            txtcvv.requestFocus();
                            txtExpiry.setText(detail.getExpMonth() + "/" + detail.getExpYear());
                            if (detail.getCardType().equalsIgnoreCase("visa")) {
                                cardImage.setImageResource(R.drawable.master);
                                img1.setImageResource(R.drawable.visa_icon);
                            } else {
                                cardImage.setImageResource(R.drawable.visa);
                                img1.setImageResource(R.drawable.mastercard);
                            }

                            payButton.setOnClickListener(view -> {


                                if (editCvv.getText().toString().isEmpty() || editCvv.getText().toString().length() < 3 || !editCvv.getText().toString().equalsIgnoreCase(detail.getCvv())) {

                                    txtcvv.setError("Invalid Cvv");

                                    txtcvv.requestFocus();

                                } else {

                                    Dialog viewDetails_box = new Dialog(requireContext());
                                    viewDetails_box.setContentView(R.layout.dialog_are_you_sure);
                                    viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
                                    Window window = viewDetails_box.getWindow();
                                    viewDetails_box.setCanceledOnTouchOutside(true);
                                    window.setGravity(Gravity.CENTER);
                                    WindowManager.LayoutParams wlp = window.getAttributes();
                                    wlp.gravity = Gravity.CENTER;
                                    wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                                    window.setAttributes(wlp);
                                    TextView txtMessage = viewDetails_box.findViewById(R.id.txtMessage);
                                    Button btnRecharge = viewDetails_box.findViewById(R.id.btnRecharge);
                                    Button btnCancel = viewDetails_box.findViewById(R.id.btnCancel);
                                    btnCancel.setOnClickListener(viexw->{
                                        viewDetails_box.dismiss();
                                        itemView.dismiss();
                                    });
                                    txtMessage.setText("Are You Sure Want To Recharge");
                                    btnRecharge.setText("Confirm");

                                    btnRecharge.setOnClickListener(viesw->{
                                        itemView.dismiss();
                                        viewDetails_box.dismiss();
                                        Bundle bundle = new Bundle();
                                        Fragment fragment = new PaymentProcessFragment();
                                        bundle.putString("customerId", detail.getCustomerId());
                                        bundle.putString("orderID", orderId);

                                        fragment.setArguments(bundle);

                                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container, fragment).addToBackStack(null).commit();

                                    });


                                }


                            });

                            itemView.show();


                        }

                        @Override
                        public void remove(SavedCard.Detail detail) {
                            new AlertDialog.Builder(requireContext())
                                    .setTitle("Delete entry")
                                    .setMessage("Are you sure you want to delete this Card")

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation


                                            new ApiViewModel().removeSavedCard(requireActivity(), detail.getId()).observe(requireActivity(), new Observer<SavedCard>() {
                                                @Override
                                                public void onChanged(SavedCard savedCard) {
                                                    if (savedCard.getStatus().equals("1")) {
                                                        getCardDetail();
                                                    }
                                                    dialog.dismiss();
                                                    Toast.makeText(requireContext(), "" + savedCard.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        }
                    }));
                }
            }
        });


    }

    private void getOrderId() {

        new ApiViewModel().orderDetails(requireActivity(), profileId, dataProductId).observe(requireActivity(), new Observer<OrderModel>() {
            @Override
            public void onChanged(OrderModel orderModel) {
                if (orderModel.getStatus() == 1) {
                    orderId = orderModel.getData().getOrderId();
                }
            }
        });


    }

    private void clicks() {


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    save = 1;
                } else {
                    save = 0;
                }
            }
        });

        imageBack.setOnClickListener(view -> {

            requireActivity().onBackPressed();
        });

        payButton.setOnClickListener(views -> {

            if (editHolderName.getText().toString().isEmpty()) {
                editHolderName.requestFocus();
                editHolderName.setError("please enter valid name");
            }

            else if (emailId.getText().toString().isEmpty() || !emailId.getText().toString().contains("@")) {
                emailId.requestFocus();
                emailId.setError("please enter valid email");
            }  else if (add_line_one.getText().toString().isEmpty()) {
                add_line_one.requestFocus();
                add_line_one.setError("please enter valid address");
            }
            else if (editCardNumber.getText().toString().trim().length() <= 16 || editCardNumber.getText().toString().trim().isEmpty()) {

                editCardNumber.requestFocus();
                editCardNumber.setError("Invalid Card Number");

            }
            else if (editExpiryDate.getText().toString().isEmpty() || !editExpiryDate.getText().toString().contains("/")) {
                editExpiryDate.requestFocus();
                editExpiryDate.setError("please enter valid date");
            } else if (editCvv.getText().toString().isEmpty()) {
                editCvv.requestFocus();
                editCvv.setError("please enter valid cvv");
            } else {

                String[] datas = editExpiryDate.getText().toString().trim().split("/", 2);
                if (Integer.parseInt(datas[0]) > 12) {
                    editExpiryDate.setError("format should be mm/yy");
                    editExpiryDate.requestFocus();
                } else {
                    payButton.setClickable(false);

                    Dialog viewDetails_box = new Dialog(requireContext());
                    viewDetails_box.setContentView(R.layout.dialog_are_you_sure);
                    viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
                    Window window = viewDetails_box.getWindow();
                    viewDetails_box.setCanceledOnTouchOutside(true);
                    window.setGravity(Gravity.CENTER);
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;
                    wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                    window.setAttributes(wlp);
                    TextView txtMessage = viewDetails_box.findViewById(R.id.txtMessage);
                    Button btnRecharge = viewDetails_box.findViewById(R.id.btnRecharge);
                    Button btnCancel = viewDetails_box.findViewById(R.id.btnCancel);
                    btnCancel.setOnClickListener(view->{
                        viewDetails_box.dismiss();
                        payButton.setClickable(true);
                    });
                    txtMessage.setText("Are You Sure Want To Recharge");
                    btnRecharge.setText("Confirm");

                    btnRecharge.setOnClickListener(view->{
                        String card = editCardNumber.getText().toString().trim().replace(" ", "");
                        new ApiViewModel().regeisterCard(requireActivity(), Long.parseLong(card),  Integer.parseInt(datas[0]),Integer.parseInt(datas[1]), Integer.parseInt(editCvv.getText().toString()),add_line_one.getText().toString(), editHolderName.getText().toString(), save, profileId, emailId.getText().toString()).observe(requireActivity(), new Observer<CardModel>() {
                            @Override
                            public void onChanged(CardModel cardModel) {

                                if (cardModel.getStatus() == 1) {
                                    payButton.setClickable(true);
                                    Bundle bundle = new Bundle();
                                    Fragment fragment = new PaymentProcessFragment();
                                    bundle.putString("customerId", cardModel.getData().getCustomerId());
                                    bundle.putString("orderID", orderId);

                                    fragment.setArguments(bundle);

                                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container, fragment).addToBackStack(null).commit();

                                } else {
                                    Toast.makeText(requireContext(), "Try Again" + cardModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                viewDetails_box.dismiss();

                            }
                        });

                    });

                    //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    viewDetails_box.show();
                    viewDetails_box.setCanceledOnTouchOutside(true);

                }


            }


        });


        editCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (i1 == 0) {
                    if (editCardNumber.getText().toString().length() == 4) {
                        editCardNumber.setText(editCardNumber.getText().toString() + " ");

                        editCardNumber.setSelection(editCardNumber.getText().toString().length());


                    } else if (editCardNumber.getText().toString().length() == 9) {
                        editCardNumber.setText(editCardNumber.getText().toString() + " ");
                        editCardNumber.setSelection(editCardNumber.getText().toString().length());

                    } else if (editCardNumber.getText().toString().length() == 14) {
                        editCardNumber.setText(editCardNumber.getText().toString() + " ");
                        editCardNumber.setSelection(editCardNumber.getText().toString().length());

                    }
                }


                if (editCardNumber.getText().toString().length() > 18) {
                    emailId.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        editExpiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (i1 == 0) {
                    if (editExpiryDate.getText().toString().length() == 2) {
                        editExpiryDate.setText(editExpiryDate.getText().toString() + "/");

                        editExpiryDate.setSelection(editExpiryDate.getText().toString().length());


                    } else if (editExpiryDate.getText().toString().length() == 5) {
                        editExpiryDate.setText(editExpiryDate.getText().toString() + " ");
                        editExpiryDate.setSelection(editExpiryDate.getText().toString().length());

                    }
                } else {
                    if (editExpiryDate.getText().toString().length() == 0) {
                        editHolderName.requestFocus();
                    }
                }


                if (editExpiryDate.getText().toString().length() >= 5) {
                    editCvv.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        editCvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (i1 == 1) {
                    if (editCvv.getText().toString().length() == 0) {
                        editExpiryDate.requestFocus();
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }

    private void setDialog() {
        Dialog viewDetails_box = new Dialog(requireContext());
        viewDetails_box.setContentView(R.layout.dialog_are_you_sure);
        viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
        Window window = viewDetails_box.getWindow();
        viewDetails_box.setCanceledOnTouchOutside(true);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        TextView txtMessage = viewDetails_box.findViewById(R.id.txtMessage);
        Button btnRecharge = viewDetails_box.findViewById(R.id.btnRecharge);
        txtMessage.setText("Are You Sure Want To Recharge");
        btnRecharge.setText("Confirm");

        btnRecharge.setOnClickListener(view->{

        });

        //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewDetails_box.show();
        viewDetails_box.setCanceledOnTouchOutside(true);
    }

    private void findIds() {


        editCardNumber = view.findViewById(R.id.editCardNumber);
        addCard = view.findViewById(R.id.addCard);
        payButton = view.findViewById(R.id.payButton);
        switch1 = view.findViewById(R.id.switch1);
        emailId = view.findViewById(R.id.emailId);
        imageBack = view.findViewById(R.id.imageBack);
        editHolderName = view.findViewById(R.id.editHolderName);
        editExpiryDate = view.findViewById(R.id.editExpiryDate);
        editCvv = view.findViewById(R.id.editCvv);
        recyclerCard = view.findViewById(R.id.recyclerCard);
      //  name = view.findViewById(R.id.name);
        add_line_one = view.findViewById(R.id.add_line_one);
        image = new ArrayList<>();


    }



}