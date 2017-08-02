package com.example.doacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	TextView txt_oquee;
	EditText edit_serie,edit_marca,edit_modelo,edit_endereco,edit_numero,edit_bairro,edit_complemento,edit_cidade,edit_telefone1,edit_telefone2,edit_nome;
	Button btn_confirmar;
	Animation shake;
	JSONObject jObject;
	JSONParser jParser;
	ProgressDialog progressdialog;
	
	private final String url = "http://doacaoetec.hol.es/cadastrar_doacao.php"; 
	String serie,marca,modelo,endereco,numero,bairro,complemento,cidade,telefone1,telefone2,nome;
	String endereco_juncao;
	String resultado;
	ControladorBanco manipulador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // chamar metodo super
    	super.onCreate(savedInstanceState);
        // extender layout xml
    	setContentView(R.layout.activity_main);
    	getActionBar().hide();

    	// instanciar objeto JSONParser
    	jParser = new JSONParser();
        // pegar edittexts
    	edit_serie = (EditText)findViewById(R.id.edit_serie);
        edit_marca = (EditText)findViewById(R.id.edit_marca);
        edit_modelo = (EditText)findViewById(R.id.edit_modelo);
        edit_endereco = (EditText)findViewById(R.id.edit_endereco);
        edit_numero = (EditText)findViewById(R.id.edit_numero);
        edit_bairro = (EditText)findViewById(R.id.edit_bairro);
        edit_complemento = (EditText)findViewById(R.id.edit_complemento);
        edit_cidade = (EditText)findViewById(R.id.edit_cidade);
        edit_telefone1 = (EditText)findViewById(R.id.edit_telefone1);
        edit_telefone2 = (EditText)findViewById(R.id.edit_telefone2);
        edit_nome = (EditText)findViewById(R.id.edit_nome); 
        txt_oquee = (TextView)findViewById(R.id.txt_oquee);
        txt_oquee.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				Intent intent  = new Intent(getApplicationContext(), ExplicacaoSerie.class);
				startActivity(intent);
			}
		});
        // pegar botao e setar clicklistener
        btn_confirmar = (Button)findViewById(R.id.btn_confirmar);
        btn_confirmar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// strings pegando conteudo das edittexts
				serie = edit_serie.getText().toString();
				marca = edit_marca.getText().toString();
				modelo = edit_modelo.getText().toString();
				endereco = edit_endereco.getText().toString();
				numero = edit_numero.getText().toString();
				bairro = edit_bairro.getText().toString();
				complemento = edit_complemento.getText().toString();
				cidade = edit_cidade.getText().toString();
				telefone1 = edit_telefone1.getText().toString();
				telefone2 = edit_telefone2.getText().toString();
				nome = edit_nome.getText().toString();
				// verificar se variaveis são nulas
				if(serie != null && marca != null && modelo != null && endereco != null && numero != null && bairro != null && cidade != null && telefone1 != null && nome != null)
				{
					// verificar se variaveis possuem conteudo
					if(!serie.equals("") && !marca.equals("") && !modelo.equals("") && !endereco.equals("") && !numero.equals("") && !bairro.equals("") && !cidade.equals("") && !telefone1.equals("") && !nome.equals(""))
					{
						if(isOnline())
						{
							if(edit_telefone2.toString().equals(""))
							{
								endereco_juncao = "";
								manipulador = new ControladorBanco(getApplicationContext());
								if(!edit_complemento.getText().toString().equals(""))
								{
									endereco_juncao = "Rua: " + endereco;
									endereco_juncao += " Número: " + numero;
									endereco_juncao += " Bairro: " + bairro;
									endereco_juncao += " Complemento " + complemento;
									endereco_juncao += " Cidade: " + cidade;
								}
								else
								{
									endereco_juncao = "Rua: " + endereco;
									endereco_juncao += " Número: " + numero;
									endereco_juncao += " Bairro: " + bairro;
									endereco_juncao += " Cidade: " + cidade;
								}
								Log.d("endereco_juncao", endereco_juncao);
								resultado = manipulador.cadastraDoacao(serie,marca, modelo,endereco_juncao, telefone1,nome);
								if(resultado.equals("sucesso"))
								{
									new Confirmar().execute();
								}	
								else if(resultado.equals("existe"))
								{
									Toast.makeText(getApplicationContext(), "Esse celular já foi cadastrado nesse sistema de doação.", Toast.LENGTH_SHORT).show();
								}
								else
								{
									Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
								}
							}
							else
							{
								endereco_juncao = "";
								manipulador = new ControladorBanco(getApplicationContext());
								if(!edit_complemento.getText().toString().equals(""))
								{
									endereco_juncao = "Rua: " + endereco;
									endereco_juncao += " Número: " + numero;
									endereco_juncao += " Bairro: " + bairro;
									endereco_juncao += " Complemento " + complemento;
									endereco_juncao += " Cidade: " + cidade;
								}
								else
								{
									endereco_juncao = "Rua: " + endereco;
									endereco_juncao += " Número: " + numero;
									endereco_juncao += " Bairro: " + bairro;
									endereco_juncao += " Cidade: " + cidade;
								}
								Log.d("endereco_juncao", endereco_juncao);
								resultado = manipulador.cadastraDoacao(serie,marca, modelo,endereco, telefone1,nome,telefone2);
								if(resultado.equals("sucesso"))
								{
									new Confirmar().execute();
								}
								else if(resultado.equals("existe"))
								{
									edit_serie.requestFocus();
									final Dialog d = new Dialog(MainActivity.this);
									d.setTitle("Erro");
									d.setContentView(R.layout.dlg_message);
									TextView txt_message = (TextView)d.findViewById(R.id.txt_message);
									txt_message.setText("Esse celular já foi registrado nesse sistema de doação.");
									Button btn_ok = (Button)d.findViewById(R.id.btn_ok);
									btn_ok.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) 
										{
											d.dismiss();
											edit_endereco.setText("");
											edit_numero.setText("");
											edit_bairro.setText("");
											edit_complemento.setText("");
											edit_cidade.setText("");
											edit_telefone1.setText("");
											edit_telefone2.setText("");
											edit_nome.setText("");
										}
									});
									d.show();								
								}
								else
								{
									final Dialog d = new Dialog(MainActivity.this);
									d.setTitle("Erro de conexão");
									d.setContentView(R.layout.dlg_message);
									TextView txt_message = (TextView)d.findViewById(R.id.txt_message);
									txt_message.setText("Um relatório de erro foi enviado. Lamentamos pelo incoveniente.");
									Button btn_ok = (Button)d.findViewById(R.id.btn_ok);
									btn_ok.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) 
										{
											d.dismiss();
											edit_serie.setText("");
											edit_marca.setText("");
											edit_modelo.setText("");
											edit_endereco.setText("");
											edit_numero.setText("");
											edit_bairro.setText("");
											edit_complemento.setText("");
											edit_cidade.setText("");
											edit_telefone1.setText("");
											edit_telefone2.setText("");
											edit_nome.setText("");
										}
									});
									d.show();
								}
							}
							// COLOCAR AKI
						}
						else
						{
							final Dialog d = new Dialog(MainActivity.this);
							d.setTitle("Sem conexão");
							d.setContentView(R.layout.dlg_message);
							TextView txt_message = (TextView)d.findViewById(R.id.txt_message);
							txt_message.setText("Não foi possível conectar-se ao servidor.\n Verifique sua conexão com a internet");
							Button btn_ok = (Button)d.findViewById(R.id.btn_ok);
							btn_ok.setOnClickListener(new OnClickListener()
							{
								@Override
								public void onClick(View v) 
								{
									d.dismiss();
									
								}
							});
							d.show();
						}
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Preencha todos os campos marcados com * corretamente.", Toast.LENGTH_LONG).show();
					}	
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Preencha todos os campos marcados com * corretamente.", Toast.LENGTH_LONG).show();
				}
			}
		});
    }

    class Confirmar extends AsyncTask<Void,Void,JSONObject>
    {
    	public Confirmar()
    	{
    	}

    	public void onPreExecute()
    	{
    		super.onPreExecute();
    		progressdialog = ProgressDialog.show(MainActivity.this,"", "Carregando...",true);
    	}
		@Override
		protected JSONObject doInBackground(Void... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// adicionar marca,modelo,endereco, telefone
			params.add(new BasicNameValuePair("cd_serie", serie));
			params.add(new BasicNameValuePair("nm_marca", marca));
			params.add(new BasicNameValuePair("nm_modelo", modelo));
			params.add(new BasicNameValuePair("ds_endereco", endereco_juncao));
			params.add(new BasicNameValuePair("cd_telefone1", telefone1));
			params.add(new BasicNameValuePair("nm_doador", nome));
			if(!edit_telefone2.getText().toString().equals(""))
			{
				params.add(new BasicNameValuePair("cd_telefone2", telefone2));
			}
			try
			{
				jObject = jParser.makeHttpRequest(url, "GET", params);
			}
			catch(Exception ex)
			{
				Log.e("Exception ","httprequest");
			}
			return jObject;
		}
    	
		protected void onPostExecute(JSONObject jObject)
		{
			progressdialog.dismiss();
			if(jObject != null)
			{
				try
				{
					if(jObject.getString("resultado").equals("existe"))
					{
						edit_serie.requestFocus();
						final Dialog d = new Dialog(MainActivity.this);
						d.setTitle("Erro");
						d.setContentView(R.layout.dlg_message);
						TextView txt_message = (TextView)d.findViewById(R.id.txt_message);
						txt_message.setText("Esse celular já foi cadastrado nesse sistema de doação.");
						Button btn_ok = (Button)d.findViewById(R.id.btn_ok);
						btn_ok.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) 
							{
								d.dismiss();
								edit_endereco.setText("");
								edit_numero.setText("");
								edit_bairro.setText("");
								edit_complemento.setText("");
								edit_cidade.setText("");
								edit_telefone1.setText("");
								edit_telefone2.setText("");
								edit_nome.setText("");
							}
						});
						d.show();	
						}
					else if(jObject.getString("resultado").equals("sim"))
					{
						final Dialog d = new Dialog(MainActivity.this);
						d.setTitle("Mensagem");
						d.setContentView(R.layout.dlg_message);
						TextView txt_message = (TextView)d.findViewById(R.id.txt_message);
						txt_message.setText("Registro de doação efetuado  com sucesso.\nEntraremos em contato.Obrigado!");
						Button btn_ok = (Button)d.findViewById(R.id.btn_ok);
						btn_ok.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) 
							{
								d.dismiss();
								edit_serie.setText("");
								edit_marca.setText("");
								edit_modelo.setText("");
								edit_endereco.setText("");
								edit_numero.setText("");
								edit_bairro.setText("");
								edit_complemento.setText("");
								edit_cidade.setText("");
								edit_telefone1.setText("");
								edit_telefone2.setText("");
								edit_nome.setText("");
							}
						});
						d.show();
						
					}
					else
					{
						final Dialog d = new Dialog(MainActivity.this);
						d.setTitle("Erro de conexão");
						d.setContentView(R.layout.dlg_message);
						TextView txt_message = (TextView)d.findViewById(R.id.txt_message);
						txt_message.setText("Um relatório de erro foi enviado. Lamentamos pelo incoveniente.");
						Button btn_ok = (Button)d.findViewById(R.id.btn_ok);
						btn_ok.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) 
							{
								d.dismiss();
								edit_serie.setText("");
								edit_marca.setText("");
								edit_modelo.setText("");
								edit_endereco.setText("");
								edit_numero.setText("");
								edit_bairro.setText("");
								edit_complemento.setText("");
								edit_cidade.setText("");
								edit_telefone1.setText("");
								edit_telefone2.setText("");
								edit_nome.setText("");
							}
						});
						d.show();
					}
				}
				catch(Exception ex)
				{
					final Dialog d = new Dialog(MainActivity.this);
					d.setTitle("Erro desconhecido");
					d.setContentView(R.layout.dlg_message);
					TextView txt_message = (TextView)d.findViewById(R.id.txt_message);
					txt_message.setText("Um relatório sobre o erro foi enviado. Lamentamos pelo incoveniente.");
					Button btn_ok = (Button)d.findViewById(R.id.btn_ok);
					btn_ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) 
						{
							d.dismiss();
							edit_serie.setText("");
							edit_marca.setText("");
							edit_modelo.setText("");
							edit_endereco.setText("");
							edit_numero.setText("");
							edit_bairro.setText("");
							edit_complemento.setText("");
							edit_cidade.setText("");
							edit_telefone1.setText("");
							edit_telefone2.setText("");
							edit_nome.setText("");
						}
					});
					d.show();
				}
			}
			else
			{
				final Dialog d = new Dialog(MainActivity.this);
				d.setTitle("Erro desconhecido");
				d.setContentView(R.layout.dlg_message);
				TextView txt_message = (TextView)d.findViewById(R.id.txt_message);
				txt_message.setText("Um relatório sobre o erro foi enviado. Lamentamos pelo incoveniente.");
				Button btn_ok = (Button)d.findViewById(R.id.btn_ok);
				btn_ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) 
					{
						d.dismiss();
						edit_serie.setText("");
						edit_marca.setText("");
						edit_modelo.setText("");
						edit_endereco.setText("");
						edit_numero.setText("");
						edit_bairro.setText("");
						edit_complemento.setText("");
						edit_cidade.setText("");
						edit_telefone1.setText("");
						edit_telefone2.setText("");
						edit_nome.setText("");
					}
				});
				d.show();
			}
		}
    }
    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); } 
          catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so SHORT
        // as you specify a parent activity in AndroidManifest.xml.
     
        return super.onOptionsItemSelected(item);
    }
}
