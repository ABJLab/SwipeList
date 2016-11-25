package com.abjlab.swipelist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Beer[] beers = new Beer[]{
      new Beer(R.drawable.run, "Mahou", "Meao de gato, huye"),
            new Beer(R.drawable.food, "Estrella galicia", "Aceptable, se deja beber"),
            new Beer(R.drawable.beer, "Judas", "Cojonuda, menudos cebollones he pillado bebiendo este manjar"),
            new Beer(R.drawable.beer, "Alhambra", "A veces es digerible"),
            new Beer(R.drawable.food, "Los picatostes de Ioritz", "Pican pero habrá que probarlos con cerveza"),
            new Beer(R.drawable.run, "San Miguel", "Venenosa"),
            new Beer(R.drawable.run, "Cruzcampo", "Me sirves esto y te escupo"),
            new Beer(R.drawable.beer, "Paulaner", "No esta mal")
    };
    private ListView beerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BeerAdapter adapter = new BeerAdapter(this, R.layout.beer_list_item, beers);
        beerList = (ListView) findViewById(R.id.beerList);
        beerList.setAdapter(adapter);
        beerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Beer currentBeer = (Beer) adapterView.getItemAtPosition(pos);
                Toast.makeText(getApplicationContext(), currentBeer.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    class BeerAdapter extends ArrayAdapter<Beer>{

        Beer[] birras;

        public BeerAdapter(Context context, int resource, Beer[] birras) {
            super(context, resource, birras);
            this.birras = birras;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item;
            ViewHolder holder;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                item = inflater.inflate(R.layout.beer_list_item, null);
                holder = new ViewHolder();
                holder.iBeer = (ImageView) item.findViewById(R.id.imageBeer);
                holder.lblTitle = (TextView) item.findViewById(R.id.itemTitle);
                holder.lblDesc = (TextView) item.findViewById(R.id.itemDesc);

                item.setTag(holder);
            }else{
                item = convertView;
                holder = (ViewHolder) item.getTag();
            }

            holder.lblTitle.setText(birras[position].getName());
            holder.lblDesc.setText(birras[position].getDescription());
            Drawable imageDrawable = getResources().getDrawable(birras[position].getBeerIcon());
            holder.iBeer.setImageDrawable(imageDrawable);

            return (item);
        }
    }

    static class ViewHolder{
        TextView lblTitle;
        TextView lblDesc;
        ImageView iBeer;
    }
}
