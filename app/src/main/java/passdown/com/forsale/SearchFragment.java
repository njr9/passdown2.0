package passdown.com.forsale;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import passdown.com.forsale.models.Post;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";

    ListView listViewPosts;
    List<Post> postList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        listViewPosts = (ListView)view.findViewById(R.id.listViewPosts);
        postList = new ArrayList<>();
        final EditText searchText = view.findViewById(R.id.editText);
        final String text = searchText.getText().toString();
        Button mSearch = view.findViewById(R.id.Search);
        mSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isEmpty(text)){
                    Toast.makeText(getActivity(), "searching", Toast.LENGTH_SHORT).show();
                    search(text);
                }else {
                    Toast.makeText(getActivity(), "you must fill out search", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return view;
    }

    public void search(final String search){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren() ){
                    Toast.makeText(getActivity(), "testing"+child.getKey(), Toast.LENGTH_SHORT).show();
                    for(DataSnapshot cpost : child.getChildren()) {
                        Toast.makeText(getActivity(), "testing"+cpost.getKey(), Toast.LENGTH_SHORT).show();
                        for (DataSnapshot childPost : cpost.getChildren()) {
                            Toast.makeText(getActivity(), "testing"+childPost.getKey(), Toast.LENGTH_SHORT).show();
                            //Post post = childPost.getValue(Post.class);
                            //if(post.getTitle().equalsIgnoreCase(search)){
                            //postList.add(post);
                            //}
                        }
                    }

                }
                PostList adapter = new PostList(getActivity(), postList);
                listViewPosts.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

    }

    private boolean isEmpty(String string){
        return string.equals("");
    }

    /*@Override
    public void onStart() {
        super.onStart();

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                postList.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Post post = postSnapshot.getValue(Post.class);

                    postList.add(post);
                }

                PostList adapter = new PostList(getActivity(), postList);
                listViewPosts.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
}
