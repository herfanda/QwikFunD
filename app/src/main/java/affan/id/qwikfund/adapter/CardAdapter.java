package affan.id.qwikfund.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import affan.id.qwikfund.R;
import affan.id.qwikfund.util.ListItem;

/**
 * Created by Herfanda on 10/3/2017 AD.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    private List<ListItem> items;
    private View view;
    private ViewHolder viewHolder;

    public CardAdapter(String[] names, String[] urls, Bitmap[] images){
        super();
        items = new ArrayList<ListItem>();
        for(int i =0; i<names.length; i++){
            ListItem item = new ListItem();
            item.setName(names[i]);
            item.setUrl(urls[i]);
            item.setImage(images[i]);
            items.add(item);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (view == null){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_card_view, parent, false);
            viewHolder = new ViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem list =  items.get(position);
        holder.imageView.setImageBitmap(list.getImage());
        holder.textViewName.setText(list.getName());
        holder.textViewUrl.setText(list.getUrl());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewUrl;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewUrl = (TextView) itemView.findViewById(R.id.textViewUrl);

        }
    }
}
