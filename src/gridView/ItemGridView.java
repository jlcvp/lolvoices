package gridView;

import caixamagica.MjolnirImg;
import android.graphics.drawable.Drawable;

/**
 * Class that implements an Item for a list view
 * @author LeU
 *
 */
public class ItemGridView {

    private String texto;
    private MjolnirImg icone;

    public ItemGridView(String texto, byte[] iconeBytes) {
        this.texto = texto;
        //this.icone = new MjolnirImg(iconeBytes);
        icone = null;
    }
    
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public Drawable getIconeImg()
    {
    	return icone.getDrawable();
    }

}
	
	
	
	
	
	


