package info63.iut.pixelartdesign.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import java.util.List;

import info63.iut.pixelartdesign.R;

public class ImageAdapter extends ArrayAdapter<String> {
    private Activity activity;
    private List<String> listImagePath;

    public ImageAdapter(Activity activity, List<String> listImagePath){
        super(activity, R.layout.image_list_item, listImagePath);
        this.activity = activity;
        this.listImagePath = listImagePath;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.image_list_item, null, false);

        ImageButton imageButton = view.findViewById(R.id.image_item);
        String s = listImagePath.get(position);
        Bitmap bm = BitmapFactory.decodeFile(s);
        imageButton.setImageBitmap(bm);

        return view;
    }

    public void setListImagePath(List<String> listImagePath) {
        this.listImagePath = listImagePath;
    }
}
