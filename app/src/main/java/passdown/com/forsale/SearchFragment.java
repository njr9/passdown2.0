package passdown.com.forsale;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

        /*listViewPosts = (ListView)view.findViewById(R.id.listViewPosts);
        postList = new ArrayList<>();*/
        return view;
    }

    public void search(){

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
