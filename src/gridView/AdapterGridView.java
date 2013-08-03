package gridView;

import java.util.ArrayList;

import com.lolchampionsvoices.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterGridView extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<ItemGridView> itens;

    public AdapterGridView(Context context, ArrayList<ItemGridView> itens) {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
        
    }

    /**
     * Retorna a quantidade de itens
     *
     * @return
     */
    public int getCount() {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public ItemGridView getItem(int position) {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        //Pega o item de acordo com a posção.
        ItemGridView item = getItem(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.hq_item, null);
        

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        
        TextView tv = ((TextView)(view.findViewById(R.id.grid_item_text)));
        Log.i("AdaterListView: getView()", "TextView: "+tv);
        if(tv != null){
        	tv.setText(item.getTexto());
        }
        ImageView iv = ((ImageView)view.findViewById(R.id.grid_item_image));
        Log.i("AdaterListView: getView()", "ImageView: "+iv);
        
//        if(tv!= null){
//        	iv.setImageDrawable(item.getIconeImg());
//        }  
        //TODO Mexi aqui, descomentar em cima quando tiver os icones

        return view;
    }
}
