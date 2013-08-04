package database;

import android.content.Context;
import android.database.Cursor;

public class RepositorioLoL extends RepositorioGenerico {

	public RepositorioLoL(Context ctx) {
		super(ctx);
		super.abrir();
	}
	
	public Cursor query(String SQL)
	{
		Cursor c=null;
		//abre o banco, consulta e fecha
		
		c=super.db.rawQuery(SQL, null);
		return c;
		
	}

}
