package charmelinetiel.androidnewsapp.helpers;

import android.content.Context;
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
    private LayoutInflater mInflater;


    public NewsItemAdapter(Context context, List<NewsItem> items){

        mInflater = LayoutInflater.from(context);
        mItems = items;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        TextView description;
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_item_title);
            image = itemView.findViewById(R.id.list_item_image);
            description = itemView.findViewById(R.id.list_item_description);
        }
    }


    public NewsItem getItem(int position){

        return mItems.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.news_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        NewsItem node = getItem(position);

        holder.title.setText(node.Title);
        holder.title.setText(node.Description);
        Random rnd = new Random();
        int color = Color.argb(255,rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(265));
        holder.image.setBackgroundColor(color);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

//
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        NewsItem node = mItems.get(position);
//
//        ViewHolder holder;
//
//        if(convertView == null){
//
//            convertView = mInflater.inflate(R.layout.news_item_view, null);
//            holder = new ViewHolder(convertView);
//            holder.image = convertView.findViewById(R.id.list_item_image);
//            holder.title = convertView.findViewById(R.id.list_item_title);
//            holder.description = convertView.findViewById(R.id.list_item_description);
//        }else{
//
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.title.setText(node.Title);
//        holder.description.setText(node.Description);
//        convertView.setTag(holder);
//        return convertView;
//    }


//    public int getCount() {
//        return 0;
//    }


}
