package com.bango.bangoLive.themes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bango.bangoLive.R;
import com.bango.bangoLive.themes.adapter.ThemeTabLayoutAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LiveThemeFragment extends BottomSheetDialogFragment {
    private TabLayout themeTablayout;
    private ViewPager themeViewPager;
    private ImageView liveThemeGalleryImg;
    private int RequestCode = 77;
    private String imagePath = "";
    private String imageTheme = "";
    private Uri imageUri;
    public static String liveHostBackImg = "";
    Bitmap bmp;
   // List<GetLiveGalleryRoot.Detail> purchaseGalleries;

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference userLiveBackImgRef = firebaseDatabase.getReference().child("userLiveBackImgRef");


    // instance for firebase storage and StorageReference
//    FirebaseStorage storage;
//    StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_theme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findIds(view);
      //  clicks(view);
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();

//        new Mvvm().getAppliedGalleryModelLiveData(requireActivity(),AppConstants.USER_ID).observe(requireActivity(), new Observer<GetAppliedGalleryModel>() {
//            @Override
//            public void onChanged(GetAppliedGalleryModel getAppliedGalleryModel) {
//                if (getAppliedGalleryModel != null){
//                    if (getAppliedGalleryModel.getStatus()==1){
//                        if (!getAppliedGalleryModel.getDetails().getImage().isEmpty()){
//                            imageTheme = getAppliedGalleryModel.getDetails().getImage();
//                        }else {
//
//                        }
//                    }
//                }
//            }
//        });

        themeTablayout.addTab(themeTablayout.newTab().setText("Theme"));
        themeTablayout.addTab(themeTablayout.newTab().setText("Store"));
        themeTablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ThemeTabLayoutAdapter adapter = new ThemeTabLayoutAdapter(getChildFragmentManager(), requireContext(), themeTablayout.getTabCount());
        themeViewPager.setAdapter(adapter);

        themeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(themeTablayout));
        themeTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                themeViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

//    private void clicks(View view) {
//
//        liveThemeGalleryImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                purchaseThemeDialogbox();
//            }
//        });
//    }

//    private void purchaseThemeDialogbox() {
//        Dialog dialog_share = new Dialog(requireContext());
//        dialog_share.setContentView(R.layout.purchase_gallery_theme_dialog_box);
//        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        dialog_share.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog_share.setCanceledOnTouchOutside(true);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(dialog_share.getWindow().getAttributes());
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog_share.getWindow().setAttributes(layoutParams);
//        Window window = dialog_share.getWindow();
//        window.setGravity(Gravity.BOTTOM);
//        dialog_share.show();
//
//        purchaseGalleries = new ArrayList<>();
//
//        RecyclerView purchaseGalleryRv = dialog_share.findViewById(R.id.purchaseGalleryRv);
//        ImageView purchaseCloseDialog = dialog_share.findViewById(R.id.purchaseCloseDialog);
//        LinearLayout purchaseGalleryNodataLinearyout = dialog_share.findViewById(R.id.purchaseGalleryNodataLinearyout);
//        ImageView openGallery = dialog_share.findViewById(R.id.openGallery);
//        purchaseGalleryNodataLinearyout.setVisibility(View.GONE);
//
//
////        new Mvvm().getGallery(requireActivity(), AppConstants.USER_ID).observe(requireActivity(), new Observer<GetLiveGalleryRoot>() {
////            @Override
////            public void onChanged(GetLiveGalleryRoot getLiveGalleryRoot) {
////                if (getLiveGalleryRoot != null) {
////                    if (getLiveGalleryRoot.getStatus() == 1) {
////                        purchaseGalleries = getLiveGalleryRoot.getDetails();
////                        PurchaseGalleryRVAdapter purchaseGalleryRVAdapter = new PurchaseGalleryRVAdapter(purchaseGalleries, requireContext(), new PurchaseGalleryRVAdapter.Callback() {
////                            @Override
////                            public void buyGallery(GetLiveGalleryRoot.Detail purchaseGallery, TextView textView) {
////                                buyGalleryThemeApi(purchaseGallery, textView);
////                            }
////
////                            @Override
////                            public void sendGallery(GetLiveGalleryRoot.Detail purchaseGallery) {
////                                sendGalleryThemeApi(purchaseGallery);
////                            }
////
////                            @Override
////                            public void showVisibleCheck(boolean b) {
////                                if (b) {
////                                    openGallery.setVisibility(View.VISIBLE);
////                                } else {
////                                    openGallery.setVisibility(View.GONE);
////                                }
////                            }
////                        });
////                        purchaseGalleryRv.setAdapter(purchaseGalleryRVAdapter);
////                    } else {
////                    }
////                } else {
////                    Toast.makeText(requireContext(), "Technical issue", Toast.LENGTH_SHORT).show();
////                }
////            }
////        });
////
//
////        openGallery.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
//////                Intent intent = new Intent(Intent.ACTION_PICK);
//////                intent.setType("image/*");
//////
//////                startActivityForResult(intent, RequestCode);
////                ImagePicker.Companion.with(LiveThemeFragment.this)
////                        .crop()                    //Crop image(Optional), Check Customization for more option
////                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
////                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
////                        .start();
////
////            }
////        });
//
//
//        purchaseCloseDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog_share.dismiss();
//            }
//        });
//
//    }

    private void findIds(View view) {
        themeTablayout = view.findViewById(R.id.themeTablayout);
        themeViewPager = view.findViewById(R.id.themeViewPager);
        liveThemeGalleryImg = view.findViewById(R.id.liveThemeGalleryImg);
    }

  //  @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
////        if (requestCode == RequestCode && resultCode == RESULT_OK) {
//        if (resultCode == Activity.RESULT_OK) {
//            assert data != null;
//            Uri image = data.getData();
//            this.imageUri = image;
////            coverInfoUserImg.setImageURI(image);
//            this.imagePath = RealPathUtil.getRealPath(requireContext(), image);
//            //sumit
//     //       uploadBackImg(imagePath);
////            ContentResolver contentResolver = getActivity().getContentResolver();
////            Cursor cursor = contentResolver.query(image, null, null, null, null);
////
////            if (cursor != null) {
////                while (cursor.moveToNext()) {
////                    @SuppressLint("Range") File file = new File(cursor.getString(cursor.getColumnIndex("_data")));
//////                    coverInfoUserImg.setImageURI(image);
//////                    this.stringCoverPhotoPath = file.getPath().toString();
////                }
////                cursor.close();
////            }
//        } else if (resultCode == ImagePicker.RESULT_ERROR) {
//            Toast.makeText(requireContext(), ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void uploadBackImg(String imagePath) {
//
//        if (imagePath != null) {
//
//            // Defining the child of storageReference
////            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
//            StorageReference ref = storageReference.child("images/").child(liveHostBackImg);
//            try {
//                bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
//            } catch (Exception e) {
//
//            }
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
//            byte[] data = baos.toByteArray();
//            //uploading the image
//            UploadTask uploadTask2 = ref.putBytes(data);
//
////             adding listeners on upload
////             or failure of image
//            uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    if (getContext() != null) {
//                        Toast.makeText(requireContext(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();
//                    }
//                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//
//                            Log.d("CALLACTIVITY", "backImg: " + uri);
//                            userLiveBackImgRef.child(liveHostBackImg).setValue(uri + ".jpg").addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                }
//                            });
//                        }
//                    });
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(requireContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
//    }

//    private void buyGalleryThemeApi(GetLiveGalleryRoot.Detail detail, TextView textView) {
//        if (!imagePath.isEmpty()) {
//            new Mvvm().purchaseGallery(requireActivity(), CommonUtils.getRequestBodyText(detail.getId()), CommonUtils.getRequestBodyText(AppConstants.USER_ID),CommonUtils.getFileData(imagePath,"image")).observe(requireActivity(), new Observer<PurchaseGalleryRoot>() {
//
//                @Override
//                public void onChanged(PurchaseGalleryRoot purchaseGalleryRoot) {
//                    if (purchaseGalleryRoot != null) {
//                        if (purchaseGalleryRoot.getStatus() == 1) {
//                            if (getContext() != null) {
//                                textView.setText("Bought");
//                                uploadBackImg(imagePath);
//                                Toast.makeText(requireContext(), "" + purchaseGalleryRoot.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            if (getContext() != null) {
//                                Toast.makeText(requireContext(), "" + purchaseGalleryRoot.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//
////                        LiveThemeFragment.this.dismiss();
//                        }
//                    } else {
//                        Toast.makeText(requireContext(), "Technical issue", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }else {
//            Toast.makeText(requireContext(), "Select image from gallery", Toast.LENGTH_SHORT).show();
//        }
//
//    }

//    private void sendGalleryThemeApi(GetLiveGalleryRoot.Detail detail) {
//
//        Bundle bundle = new Bundle();
//        bundle.putString("galleryId", detail.getId());
//        SendGalleryDialogFragment themesFragment = new SendGalleryDialogFragment();
//        themesFragment.setArguments(bundle);
//        themesFragment.show(getActivity().getSupportFragmentManager(), themesFragment.getTag());
//    }

    @Override
    public void onResume() {
        super.onResume();


    }
}