package com.abjlab.swipelist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.translationX;
import static android.R.attr.width;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.abjlab.swipelist.R.drawable.beer;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Beer> beers;
    private RecyclerView beerList;
    private FloatingActionButton fab;
    private Paint paint = new Paint();
    private  BeerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar lista
        loadList();

        beerList = (RecyclerView) findViewById(R.id.beerList);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        beerList.setHasFixedSize(true);

        adapter = new BeerAdapter(beers);
        adapter.onClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = beerList.getChildPosition(view);
                Beer beer = beers.get(pos);
                Toast.makeText(getApplicationContext(), beer.getName(), Toast.LENGTH_SHORT).show();

                // adapter.notifyItemRemoved(pos);
            }
        });
        beerList.setAdapter(adapter);
        beerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        beerList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        beerList.setItemAnimator(new DefaultItemAnimator());
        swipeHandler();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddBeerActivity.class);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Beer beer = new Beer();
            beer.setName(data.getStringExtra("nombre"));
            beer.setDesc(data.getStringExtra("desc"));
            beer.setBeerIcon(data.getIntExtra("icono", 0));
            int size = beers.size();
            beers.add(size, beer);
            adapter.notifyItemInserted(size);
        }
    }

    private void swipeHandler() {
        ItemTouchHelper.SimpleCallback touchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                BeerAdapter adapter = (BeerAdapter) beerList.getAdapter();

                if (direction == ItemTouchHelper.LEFT) {
                    Beer beer = beers.get(pos);
                    beers.remove(pos);
                    adapter.notifyItemRemoved(pos);
                    beers.add(pos, beer);
                    adapter.notifyItemInserted(pos);
                    //Toast.makeText(getApplicationContext(), "Almacenada en favoritos " + beer.getName() ,Toast.LENGTH_SHORT).show();
                    Snackbar.make(beerList, "Almacenada en favoritos " + beer.getName(), Snackbar.LENGTH_SHORT).show();
                } else {
                    beers.remove(pos);
                    adapter.notifyItemRemoved(pos);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                float translationX = Math.max(dX, (-1) * viewHolder.itemView.getWidth() / 2);
                Bitmap icono;

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    if (dX > 0) {
                        paint.setColor(Color.parseColor("#F44336"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        icono = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_delete_forever_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icono, null,icon_dest,paint);
                    } else {
                        paint.setColor(Color.parseColor("#FF4081"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        icono = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_favorite_border_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icono, null,icon_dest,paint);

                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper helper = new ItemTouchHelper(touchCallback);
        helper.attachToRecyclerView(beerList);
    }

    private void loadList() {
        if (beers == null) {
            beers = new ArrayList<Beer>();
            beers.add(new Beer(R.drawable.run, "Mahou", "Meao de gato, huye"));
            beers.add(new Beer(R.drawable.food, "Estrella galicia", "Aceptable, se deja beber"));
            beers.add(new Beer(beer, "Judas", "Cojonuda, menudos cebollones he pillado bebiendo este manjar"));
            beers.add(new Beer(beer, "Alhambra", "A veces es digerible"));
            beers.add(new Beer(R.drawable.food, "Los picatostes de Ioritz", "Pican pero habrá que probarlos con cerveza"));
            beers.add(new Beer(R.drawable.run, "San Miguel", "Venenosa"));
            beers.add(new Beer(R.drawable.run, "Cruzcampo", "Me sirves esto y te escupo"));
            beers.add(new Beer(beer, "Paulaner", "No esta mal"));
        }
    }
}
