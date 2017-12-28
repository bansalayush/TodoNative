package scorpio.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    Button saveButton,deleteButton;
    EditText noteText;
    Intent i ;
    int requestCode=0 ;
    int notePosition;

    @Override
    public void onBackPressed() {
        onSave();
        super.onBackPressed();
    }

    public void onSave(){
        i = new Intent();
        if(requestCode == 1003)
        {
            i.putExtra("NOTEPOSITION",notePosition);

        }
        String text = noteText.getText().toString();

        i.putExtra("NOTETEXT",text);
        setResult(1002,i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        saveButton = (Button)findViewById(R.id.saveButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        noteText = (EditText)findViewById(R.id.editfield);

        i = getIntent();

        //if note is selected
        requestCode = i.getExtras().getInt("REQUESTCODE");
        if(requestCode == 1003){
            notePosition = i.getExtras().getInt("NOTEPOSITION");
            String note = i.getExtras().getString("NOTETEXT");
            noteText.setText(note);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("delete button clicked");
                if(requestCode == 1003)//edit
                {
                    i = getIntent();
                    notePosition = i.getExtras().getInt("NOTEPOSITION");
                    i.putExtra("NOTEPOSITION",notePosition);
                    setResult(1004,i);
                }
                if(requestCode == 1001)//add
                {
                    i = getIntent();
                    setResult(1004,i);

                }
                finish();


            }
        });
    }
}
