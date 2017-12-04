package passdown.com.forsale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import passdown.com.forsale.models.Post;
import passdown.com.forsale.util.UniversalImageLoader;

public class PostFragment extends Fragment{

    private static final String TAG = "PostFragment";

    //widgets
    private ImageView mPostImage;
    private EditText mTitle, mDescription, mPrice, mCountry, mStateProvince, mCity, mContactEmail;
    private Button mPost;
    private ProgressBar mProgressBar;

    //vars
    private String title, description, price, country, stateProvince, city, email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        mPostImage = view.findViewById(R.id.post_image);
        mTitle = view.findViewById(R.id.input_title);
        mDescription = view.findViewById(R.id.input_description);
        mPrice = view.findViewById(R.id.input_price);
        mCountry = view.findViewById(R.id.input_country);
        mStateProvince = view.findViewById(R.id.input_state_province);
        mCity = view.findViewById(R.id.input_city);
        mContactEmail = view.findViewById(R.id.input_email);
        mPost = view.findViewById(R.id.btn_post);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init();

        return view;
    }

    private void init(){

        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Posting...");
                if(!isEmpty(mTitle.getText().toString())
                        && !isEmpty(mDescription.getText().toString())
                        && !isEmpty(mPrice.getText().toString())
                        && !isEmpty(mCountry.getText().toString())
                        && !isEmpty(mStateProvince.getText().toString())
                        && !isEmpty(mCity.getText().toString())
                        && !isEmpty(mContactEmail.getText().toString())){
                    upload();
                }else{
                    Toast.makeText(getActivity(), "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void upload(){

        //Displays a message that the post is currently being uploaded.
        Toast.makeText(getActivity(), "Uploading! Please wait...", Toast.LENGTH_SHORT).show();

        //Gets an instance of the users reference
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() +
                "/Posts/");

        //Gets a unique key for this specific post
        String postId = databaseUsers.push().getKey();

        //Creates a new book posting
        Post post = new Post(postId,
                FirebaseAuth.getInstance().getCurrentUser().getUid(),mTitle.getText().toString(),
                mDescription.getText().toString(), mPrice.getText().toString(),
                mCountry.getText().toString(), mStateProvince.getText().toString(),
                mCity.getText().toString(), mContactEmail.getText().toString());

        //Puts the information onto the database
        databaseUsers.child(postId).setValue(post);

        Toast.makeText(getActivity(), "Post complete!", Toast.LENGTH_SHORT).show();

        resetFields();
    }

    private void resetFields(){
        UniversalImageLoader.setImage("", mPostImage);
        mTitle.setText("");
        mDescription.setText("");
        mPrice.setText("");
        mCountry.setText("");
        mStateProvince.setText("");
        mCity.setText("");
        mContactEmail.setText("");
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Return true if the @param is null
     * @param string
     * @return
     */
    private boolean isEmpty(String string){
        return string.equals("");
    }
}
