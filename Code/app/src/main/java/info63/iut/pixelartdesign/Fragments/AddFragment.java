package info63.iut.pixelartdesign.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import info63.iut.pixelartdesign.Accessors.FileAccessor;
import info63.iut.pixelartdesign.Accessors.IMediaFiles;
import info63.iut.pixelartdesign.Adapter.ImageAdapter;
import info63.iut.pixelartdesign.CameraFiles.CameraActivity;
import info63.iut.pixelartdesign.Fragments.Dialogs.SuppressionDialogFragment;
import info63.iut.pixelartdesign.R;

public class AddFragment extends Fragment{

    private List<String> imageButtonList = new ArrayList<>();
    private ListView listViewImage;
    private ImageAdapter adapter;
    private IMediaFiles fileAccessor = new FileAccessor();
    private File directoryImage = fileAccessor.getPublicAlbumStorageDir(CameraActivity.ALBUM_NAME);

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
        if (directoryImage.exists()) imageButtonList = fileAccessor.chargementPathImages();

        if (adapter != null){
            adapter.setListImagePath(imageButtonList);
        }else{
            adapter = new ImageAdapter(getActivity(), imageButtonList);
        }
        listViewImage.setAdapter(adapter);

        listViewImage.setClickable(true);
        listViewImage.setLongClickable(true);
        final Fragment here = this;

        listViewImage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SuppressionDialogFragment df = new SuppressionDialogFragment();
                df.setTargetFragment(here, SuppressionDialogFragment.REQUEST_CODE_DIALOG);
                //SuppressionDialogFragment.newInstance(position).show(here.getFragmentManager(), "suppressDialog");
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                df.setArguments(bundle);
                df.show(here.getFragmentManager(), "dialog");
                return true;
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SuppressionDialogFragment.REQUEST_CODE_DIALOG){
            // TODO: debug
            if (directoryImage.exists()) {
                Log.d("delete", "onActivityResult: " + imageButtonList.toString());
                imageButtonList = fileAccessor.chargementPathImages();
                Log.d("delete", "onActivityResult: " + imageButtonList.toString());
            }
            adapter.setListImagePath(imageButtonList);
            listViewImage.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (directoryImage.exists()) imageButtonList = fileAccessor.chargementPathImages();
        adapter.setListImagePath(imageButtonList);
        listViewImage.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
