package br.com.logica;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tagedition.R;

public class FileAdapter extends BaseAdapter{
	
	private List<File> files;
	private LayoutInflater mInflater;
	
	public FileAdapter(Context context, List<File> files) {
		mInflater = LayoutInflater.from(context);
		this.files = files;
	}
	
	@Override
	public int getCount() {
		return files.size();
	}

	@Override
	public Object getItem(int arg0) {
		return files.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int posicao, View view, ViewGroup arg2) {
		view = mInflater.inflate(R.layout.item_list, null); 
		
		TextView tipo = (TextView)view.findViewById(R.id.tipoArquivo);
		if (this.files.get(posicao).isDirectory()){
			tipo.setText("diretorio");
		}else{
			tipo.setText("musica");
		}
		
		
		TextView nome = (TextView)view.findViewById(R.id.nomeArquivo);
		nome.setText(files.get(posicao).getName());
		
		return view;
	}
}
