package com.example.doacao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ControladorBanco {

	private Banco banco;
	private Cursor cursor;
	private SQLiteDatabase db;
	private long resultado;
	
	public ControladorBanco(Context context)
	{
		this.banco = new Banco(context);
	}
	

	public String cadastraDoacao(String cd_serie, String nm_marca, String nm_modelo, String ds_endereco, String cd_telefone1, String nm_doador)
	{
		String query = "SELECT cd_serie FROM tb_doacao WHERE cd_serie = '"+ cd_serie + "'";
		db = banco.getReadableDatabase();
		cursor = db.rawQuery(query, null);
		if(cursor.getCount() < 1)
		{
			ContentValues contentvalues = new ContentValues();
			contentvalues.put("cd_serie", cd_serie);
			contentvalues.put("nm_marca", nm_marca);
			contentvalues.put("nm_modelo", nm_modelo);
			contentvalues.put("ds_endereco", ds_endereco);
			contentvalues.put("cd_telefone", cd_telefone1);
			contentvalues.put("nm_doador", nm_doador);
			resultado = db.insertOrThrow("tb_doacao",null, contentvalues);
			if(resultado != -1)
			{
				db.close();
				return "sucesso";
			}
			else
			{
				db.close();
				return "erro";
			}
		}
		else
		{
			return "existe";
		}
	}
	public String cadastraDoacao(String cd_serie, String nm_marca, String nm_modelo, String ds_endereco, String cd_telefone1, String nm_doador, String cd_telefone2)
	{
		String query = "SELECT cd_serie FROM tb_doacao WHERE cd_serie = '"+ cd_serie + "'";
		db = banco.getReadableDatabase();
		cursor = db.rawQuery(query, null);
		if(cursor.getCount() < 1)
		{
			ContentValues contentvalues = new ContentValues();
			contentvalues.put("cd_serie", cd_serie);
			contentvalues.put("nm_marca", nm_marca);
			contentvalues.put("nm_modelo", nm_modelo);
			contentvalues.put("ds_endereco", ds_endereco);
			contentvalues.put("cd_telefone1", cd_telefone1);
			contentvalues.put("nm_doador", nm_doador);
			contentvalues.put("cd_telefone2", cd_telefone2);
			resultado = db.insertOrThrow("tb_doacao",null, contentvalues);
			if(resultado != -1)
			{
				db.close();
				return "sucesso";
			}
			else
			{
				db.close();
				return "erro";
			}
		}
		else
		{
			return "existe";
		}
	}
}
