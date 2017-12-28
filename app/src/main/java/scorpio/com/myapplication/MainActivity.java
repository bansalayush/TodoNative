package scorpio.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

//on edit : 1003
//on add  : 1001
public class MainActivity extends AppCompatActivity {
    private List<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotesAdapter mAdapter;
    private Intent i;
    private Button addNote;
    private String filename = "geekyantstodo";
    String files [];
    String dirPath = null;


    public void listFiles(){
        File filePath = getFilesDir();
        files = filePath.list();
        for(int i=0;i<files.length;i++){
            System.out.println("========>>>>>>>"+files[i]);
        }
    }

    public void readFiles(){
        File f ;
        for(int i=0;i<files.length;i++){
            String text = "";
            f = new File(dirPath + "/"+files[i] );
            Scanner sc = null;
            try {
                sc = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while(sc.hasNext("[\\d\\D\\n]*")){
                text = text+sc.nextLine()+"\n";
                Log.d("hasNextLine withpattern",text);

            }
            System.out.println("==> "+text);
            noteList.add(new Note(text,files[i]));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dirPath = getFilesDir().toString();

        listFiles();
        readFiles();

        Toast.makeText(getApplicationContext(),"onCreate",Toast.LENGTH_LONG).show();
        addNote = (Button)findViewById(R.id.addnote);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mAdapter = new NotesAdapter(noteList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Note note = noteList.get(position);
                i = new Intent(MainActivity.this,EditActivity.class);
                i.putExtra("REQUESTCODE",1003);
                i.putExtra("NOTEPOSITION",position);
                i.putExtra("NOTETEXT",note.getNote());
                startActivityForResult(i,1003);
                Toast.makeText(getApplicationContext(), note.getNote() + " is selected!", Toast.LENGTH_SHORT).show();
            }

        }));
        recyclerView.setAdapter(mAdapter);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this,EditActivity.class);
                i.putExtra("REQUESTCODE",1001);
                startActivityForResult(i,1001);

            }
        });
        mAdapter.notifyDataSetChanged();

    }

    public void appendText(int position,String notedata){
        Note note = noteList.get(position);
        String filename = note.getFilename();
        System.out.println("12323=>>>>>>"+filename);
        File f = new File(dirPath + "/" + filename);
       PrintWriter writer = null;
        try {
            writer = new PrintWriter(f);
            Log.d("Appending text",notedata);
            writer.write(notedata);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        note.setNoteText(notedata);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1002){
            String notedata = data.getExtras().getString("NOTETEXT");
            //on add
            if(requestCode == 1001){
                //System.out.println(resultCode + " " + notedata);
                Toast.makeText(getApplicationContext(),"hey there",Toast.LENGTH_LONG).show();
                addNote(notedata);
            }
            //on edit
            if(requestCode == 1003){
                int position = data.getExtras().getInt("NOTEPOSITION");
                appendText(position,notedata);
            }
        }
        if(resultCode == 1004){
            if(requestCode == 1003){
                int position = data.getExtras().getInt("NOTEPOSITION");
                removeNoteFile(position);

            }
            if(requestCode == 1001){
                Toast.makeText(getApplicationContext(),"Nothing is added",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void removeNoteFile(int position){
        Note note = noteList.get(position);

        String filepath = dirPath + "/" +  note.getFilename();
        System.out.println(filepath);
        File f =  new File(filepath);
        f.delete();
        noteList.remove(position);
        mAdapter.notifyDataSetChanged();

    }

    private void addNote(String text){
        String fileStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filepath = dirPath + "/" + fileStamp + ".txt";
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filepath);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Note note = new Note(text,filepath);
        noteList.add(note);
        mAdapter.notifyDataSetChanged();
    }
}
