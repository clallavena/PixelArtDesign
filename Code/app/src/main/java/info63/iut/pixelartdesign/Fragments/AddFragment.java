package info63.iut.pixelartdesign.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import info63.iut.pixelartdesign.Adapter.ImageAdapter;
import info63.iut.pixelartdesign.CameraFiles.CameraActivity;
import info63.iut.pixelartdesign.R;

public class AddFragment extends Fragment {

    private TextView mTextMessage;
    private List<String> imageButtonList = new ArrayList<>();
    private ListView listViewImage;
    private ImageAdapter adapter;

    public static AddFragment newInstance(){
        return new AddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        listViewImage = view.findViewById(R.id.list_item);

        //Charger les images dans une liste de string
        File directoryImage = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), CameraActivity.ALBUM_NAME);
        for (String s :
                directoryImage.list()) {
            imageButtonList.add(directoryImage.getPath() + "/" + s);
        }

        adapter = new ImageAdapter(getActivity(), imageButtonList);
        listViewImage.setAdapter(adapter);

        final FloatingActionButton buttonCapture = (FloatingActionButton) view.findViewById(R.id.add_button);
        buttonCapture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CameraActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
