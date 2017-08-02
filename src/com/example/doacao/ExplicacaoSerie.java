package com.example.doacao;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class ExplicacaoSerie  extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_oquee);
		getActionBar().setTitle("");
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so SHORT
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) 
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
