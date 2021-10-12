package com.devdiv.vca.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.devdiv.vca.Activities.ActivityEditProfile;
import com.devdiv.vca.Activities.ActivityLogin;
import com.devdiv.vca.LottieDialog;
import com.devdiv.vca.R;
import com.devdiv.vca.Utils;
import com.devdiv.vca.predictor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class FragmentProfile extends Fragment {

    private RelativeLayout lay_logout,lay_update;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private Utils utils;


    private ImageView imageprofile;

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    private TextView txt_studentFullName, txt_email, txt_phone;
    private TextView txt_country, txt_qualifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txt_studentFullName = view.findViewById(R.id.txt_studentFullName);
        txt_email = view.findViewById(R.id.txt_email);
        txt_phone = view.findViewById(R.id.txt_phone);
        txt_country = view.findViewById(R.id.txt_country);
        lay_update = view.findViewById(R.id.lay_update);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();
        utils = new Utils(getContext());



        ///////////////////// getTeacherprofile ///////////////
        getData();

        /////////////////////  logout ///////////////////////
        lay_logout = view.findViewById(R.id.lay_logout);
        lay_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();

            }
        });

        //////////////////// update profile/////////////
        lay_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityEditProfile.class));
            }
        });

        ///////////////////// uploadimage ///////////////////////
        imageprofile = view.findViewById(R.id.imageprofile);
        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgProfile();

            }
        });

        return view;
    }

    private void getData() {

        final LottieDialog lottie = new LottieDialog(getContext());
        lottie.show();
        db.collection("Users").document(utils.getToken()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        String teacher_RegNoFromDb = document.getString("email");
                        String teacher_FullNameFromDb = document.getString("name");
                        String teacher_FatherNameFromDb = document.getString("number");
                        String student_homeAddressFromDb = document.getString("country");

                        String student_profilePicFromDb = document.getString("user_picture");

                        txt_email.setText(teacher_RegNoFromDb);
                        txt_studentFullName.setText(teacher_FullNameFromDb);
                        txt_phone.setText(teacher_FatherNameFromDb);
                        txt_country.setText(student_homeAddressFromDb);

                        if (student_profilePicFromDb != null) {
                            Glide.with(getContext()).load(student_profilePicFromDb).into(imageprofile);
                        }


                        lottie.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                        lottie.dismiss();
                    }
                });

    }

    public void logout() {

        MaterialDialog mDialog = new MaterialDialog.Builder(this.getActivity())
                .setTitle("Logout")
                .setMessage("Are you sure want to logout!")
                .setCancelable(false)
                .setPositiveButton("Logout", R.drawable.ic_baseline_logout_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        utils.logout();
                        startActivity(new Intent(getContext(), ActivityLogin.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_baseline_cancel_presentation_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    private void imgProfile() {
        MaterialDialog mDialog = new MaterialDialog.Builder(this.getActivity())
                .setTitle("Upload")
                .setMessage("Are you sure want to Upload Image!")
                .setCancelable(false)
                .setPositiveButton("Upload", R.drawable.ic_baseline_file_upload_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {


                        chooseImage();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_baseline_cancel_presentation_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    /////////////////////////  IMAGE  //////////////////
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageprofile.setImageBitmap(bitmap);

                updateUser(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    ///////////////////////////////////////////////////////////////

    private void updateUser(Uri filePath) {


        final LottieDialog lottie = new LottieDialog(getContext());
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();


        StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Map<String, Object> m = new HashMap<>();
                                m.put("user_picture", uri.toString());


                                db.collection("Users").document(utils.getToken()).update(m)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                progressDialog.dismiss();
                                                lottie.dismiss();
                                                Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                                getData();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                lottie.dismiss();
                                                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //Toast.makeText(ActivityNominee.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploading Data " + (int) progress + "%");
                    }
                });


    }
}