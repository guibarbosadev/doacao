package com.example.doacao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Banco extends SQLiteOpenHelper 
{
	public Banco(Context context)
	{
		super(context, "db_banco.db",null,1);
	}
	
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE tb_doacao("
				+ "cd_serie text not null primary key,"
				+ "nm_marca text not null,"
				+ "nm_modelo text not null,"
				+ "ds_endereco text not null,"
				+ "cd_telefone1 text not null,"
				+ "cd_telefone2 text not null,"
				+ "nm_doador text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("updating db", "old version: " + oldVersion + ", new version: " +newVersion);

	}

}
