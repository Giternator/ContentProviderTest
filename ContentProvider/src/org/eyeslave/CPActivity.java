package org.eyeslave;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CPActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button show = (Button) findViewById(R.id.button1);
	show.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub

		String text = NotesDB.getInstance().getTextFromTitle(getContentResolver(), "abeer");
		TextView tv = (TextView) findViewById(R.id.textview1);
		tv.setText(text);
	    }
	});

	Button insert = (Button) findViewById(R.id.button2);
	insert.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		NotesDB.getInstance().addNewNote(getContentResolver(), "abeer", "abeer tumi kemon acho?");
	    }
	});

	Button refresh = (Button) findViewById(R.id.button3);
	refresh.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {

		NotesDB.getInstance().refreshCache(getContentResolver());
	    }
	});

        
        
    }
}