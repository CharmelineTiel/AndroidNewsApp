package charmelinetiel.androidnewsapp.helpers;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import charmelinetiel.androidnewsapp.R;
import charmelinetiel.androidnewsapp.models.NewsItem;

/**
 * Created by Tiel on 26-9-2017.
 */

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {


    private final List<NewsItem> mItems;
    private final NewsItemListener mListener;

    //listener interface
    public interface NewsItemListener {

        void onItemClick(int position);
    }

    public NewsItemAdapter(List<NewsItem> items, NewsItemListener listener){

        mItems = items;
        mListener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView description;
        public ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.list_item_image);
            title = itemView.findViewById(R.id.list_item_title);
            description = itemView.findViewById(R.id.list_item_description);
        }
    }

    public NewsItem getItem(int position){

        return mItems.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(

            LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_view, parent, false
            ));
        }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        NewsItem node = getItem(position);

        //fill the views in the viewholder with content
        holder.title.setText(node.Title);
        holder.description.setText(node.Description);
        Random rnd = new Random();
        int color = Color.argb(255,rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(265));
        holder.image.setBackgroundColor(color);

        //
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mListener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}
