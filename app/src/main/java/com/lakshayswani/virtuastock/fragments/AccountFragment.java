package com.lakshayswani.virtuastock.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.lakshayswani.virtuastock.R;
import com.lakshayswani.virtuastock.ui.Dashboard;
import com.lakshayswani.virtuastock.ui.LoginActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener {

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * The constant CAMERA_REQUEST.
     */
    protected static final int CAMERA_REQUEST = 0;
    /**
     * The constant GALLERY_REQUEST.
     */
    protected static final int GALLERY_REQUEST = 1;
    /**
     * The Bitmap.
     */
    Bitmap bitmap;
    /**
     * The Uri.
     */
    Uri uri;
    /**
     * The Pic intent.
     */
    Intent picIntent = null;

    /**
     * The Fragment.
     */
    static AccountFragment fragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText username;
    private EditText newPassword;
    private EditText newPasswordConfirm;
    private Button submitUpdates;
    private ImageView logOut;

    private ImageView header_cover_image;
    private ImageButton user_profile_photo;

    private OnFragmentInteractionListener mListener;

    private GoogleSignInOptions gso;

    private GoogleApiClient mGoogleApiClient;

    /**
     * Instantiates a new Account fragment.
     */
    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
// TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        if (fragment == null) {
            fragment = new AccountFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        username = (EditText) view.findViewById(R.id.username);
        newPassword = (EditText) view.findViewById(R.id.newPassword);
        newPasswordConfirm = (EditText) view.findViewById(R.id.newPasswordConfirm);
        submitUpdates = (Button) view.findViewById(R.id.submitUpdates);
        logOut = (ImageView) view.findViewById(R.id.logOut);

        username.setTypeface(Dashboard.robotoLight);
        newPassword.setTypeface(Dashboard.robotoLight);
        newPasswordConfirm.setTypeface(Dashboard.robotoLight);
        submitUpdates.setTypeface(Dashboard.robotoLight);

        user_profile_photo = (ImageButton) view.findViewById(R.id.user_profile_photo);
        header_cover_image = (ImageView) view.findViewById(R.id.header_cover_image);

        if (gso == null)
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.web_client_id))
                    .requestEmail()
                    .build();

        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

        if (Dashboard.currentUser.getDisplayName() != null)
            username.setText(Dashboard.currentUser.getDisplayName());

        if (savedInstanceState == null)
            Dashboard.storageReference.child(Dashboard.currentUser.getUid()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                        try {
                            Picasso.with(getActivity().getApplicationContext()).load(task.getResult()).into(header_cover_image);
                        } catch (Exception e) {
                            Log.e("ACCOUNT_FRAGMENT", e.getMessage());
                        }
                }
            });
        else if (savedInstanceState.getParcelable(getResources().getString(R.string.profilePic)) == null)
            Dashboard.storageReference.child(Dashboard.currentUser.getUid()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        try {
                            Picasso.with(getActivity().getApplicationContext()).load(task.getResult()).into(header_cover_image);
                        } catch (Exception e) {
                            Log.e("ACCOUNT_FRAGMENT", e.getMessage());
                        }
                    }
                }
            });
        else
            header_cover_image.setImageBitmap((Bitmap) savedInstanceState.getParcelable(getResources().getString(R.string.profilePic)));

        user_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDilog();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage(getResources().getString(R.string.t_logout_msg));
                alertDialogBuilder.setPositiveButton(getResources().getString(R.string.t_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        try {
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                    Intent in = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(in);
                                    getActivity().finish();
                                }
                            });
                        } catch (Exception e) {
                            Log.e("GOOGLE", e.getMessage());
                            Intent in = new Intent(getActivity(), LoginActivity.class);
                            startActivity(in);
                            getActivity().finish();
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton(getResources().getString(R.string.t_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialogBuilder.setTitle(getResources().getString(R.string.t_logout));
                alertDialogBuilder.show();
            }
        });

        submitUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = null;
                if (username.getText().toString().equalsIgnoreCase("")) {
                    newUsername = "Not Set";
                } else {
                    newUsername = username.getText().toString();
                }
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newUsername)
                        .build();
                Dashboard.currentUser.updateProfile(profileUpdates);
                if ((newPassword.getText().toString().equalsIgnoreCase("")) || (newPasswordConfirm.getText().toString().equalsIgnoreCase(""))) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.t_profile_updated), Toast.LENGTH_SHORT).show();
                } else if (newPassword.getText().toString().equalsIgnoreCase(newPasswordConfirm.getText().toString())) {
                    Dashboard.currentUser.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.t_profile_updated), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.t_passwords_not_match), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    /**
     * On button pressed.
     *
     * @param uri the uri
     */
// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        /**
         * On fragment interaction.
         *
         * @param uri the uri
         */
// TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        try {
            outState.putParcelable(getResources().getString(R.string.profilePic), ((BitmapDrawable) header_cover_image.getDrawable()).getBitmap());
        } catch (Exception e) {
            Log.e("Saving State", e.getMessage());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private void startDilog() {
        AlertDialog.Builder myAlertDilog = new AlertDialog.Builder(getActivity());
        myAlertDilog.setTitle(getResources().getString(R.string.upload_pic));
        myAlertDilog.setMessage(getResources().getString(R.string.upload_option));
        myAlertDilog.setPositiveButton(getResources().getString(R.string.gallary), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                picIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
                picIntent.setType("image/*");
                picIntent.putExtra(getResources().getString(R.string.return_data), true);
                startActivityForResult(picIntent, GALLERY_REQUEST);
            }
        });
        myAlertDilog.setNegativeButton(getResources().getString(R.string.Camera), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(picIntent, CAMERA_REQUEST);
            }
        });
        myAlertDilog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    uri = data.getData();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    try {
                        BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, options);
                        options.inSampleSize = calculateInSampleSize(options, 400, 400);
                        options.inJustDecodeBounds = false;
                        Bitmap image = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, options);
                        header_cover_image.setImageBitmap(image);
                        Dashboard.updateProfilePic(uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.t_cancel),
                            Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.t_cancel),
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(getResources().getString(R.string.data))) {
                    bitmap = (Bitmap) data.getExtras().get(getResources().getString(R.string.data));
                    uri = getImageUri(getActivity(), bitmap);
                    File finalFile = new File(getRealPathFromUri(uri));
                    header_cover_image.setImageBitmap(bitmap);
                    Dashboard.updateProfilePic(uri);
                } else if (data.getExtras() == null) {

                    Toast.makeText(getActivity().getApplicationContext(),
                            getResources().getString(R.string.t_nothing_to_retrieve), Toast.LENGTH_SHORT)
                            .show();

                    BitmapDrawable thumbnail = new BitmapDrawable(
                            getResources(), data.getData().getPath());
                    header_cover_image.setImageDrawable(thumbnail);


                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.t_cancel),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getRealPathFromUri(Uri tempUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(tempUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * Calculate in sample size int.
     *
     * @param options   the options
     * @param reqWidth  the req width
     * @param reqHeight the req height
     * @return the int
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private Uri getImageUri(Activity activity, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, getResources().getString(R.string.title), null);
        return Uri.parse(path);
    }
}
