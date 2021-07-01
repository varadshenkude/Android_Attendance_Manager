package com.swarajya.schoolAttendance.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.swarajya.schoolAttendance.R;

public class PreStdAct extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 1;

    private Button chooseImage, btnUploadImage;
    private TextView viewGallery;
    private ImageView imgPreview;
//    private EditText imgDescription;
    private ProgressBar uploadProgress;
    public static Uri uris;
    private Uri imgUrl;
    private String user;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_std_act);
        uploadProgress = findViewById(R.id.uploadProgress);
        chooseImage = findViewById(R.id.chooseImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);
//        user = getIntent().getStringExtra(user);
        viewGallery = findViewById(R.id.viewGallery);
        viewGallery.setVisibility(View.INVISIBLE);

        user = adminlogin.user;
        imgPreview = findViewById(R.id.imgPreview);

        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        viewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("\nUser",user);
                if (user.equals("teacher")){
                    Intent intent = new Intent(PreStdAct.this, addteacher.class);
                    intent.putExtra("imgUri",uris);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(PreStdAct.this, addstudent.class);
                    intent.putExtra("imgUri", uris);
                    startActivity(intent);
                    finish();

                }
            }
        });
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(PreStdAct.this, "Upload in progress", Toast.LENGTH_LONG).show();
                } else {
                    uploadImage();
                }
            }
        });


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChoose();
            }
        });
    }

    private void showFileChoose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUrl = data.getData();

            Picasso.with(this).load(imgUrl).into(imgPreview);
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        if (imgUrl != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imgUrl));

            mUploadTask = fileReference.putFile(imgUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgress.setProgress(0);
                                }
                            }, 500);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
//                                    Upload upload = new Upload(imgDescription.getText().toString().trim(), uri.toString());
//                                    String uploadID = mDatabaseRef.push().getKey();
//                                    mDatabaseRef.child(uploadID).setValue(upload);
//                                    setImgUrl(uri);
                                    uris = uri;
                                    Toast.makeText(PreStdAct.this, "Upload successfully", Toast.LENGTH_LONG).show();
//                                    imgPreview.setImageResource(R.drawable.ic_student);
                                    viewGallery.setVisibility(View.VISIBLE);
                                    imgPreview.setImageURI(imgUrl);
//                                    imgDescription.setText("");
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PreStdAct.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadProgress.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(PreStdAct.this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
