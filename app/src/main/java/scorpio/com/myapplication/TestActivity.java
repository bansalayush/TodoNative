package scorpio.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TestActivity extends AppCompatActivity {
    String files[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        createNewFile("do this do that");
        listFiles();
    }

    public void readFiles(){
        for(int i=0;i<files.length;i++){
            Scanner sc = new Scanner(files[i]);
            while(sc.hasNext()){
                System.out.println(sc.next());
            }
        }
    }

    public void listFiles(){
        File filePath = getFilesDir();
        files = filePath.list();
        for(int i=0;i<files.length;i++){
            System.out.println(files[i]);
        }
    }

    public void createNewFile(String text){
        String filepath = getFilesDir().toString();
        String fileStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filepath + "/" +fileStamp + ".txt");
            writer.write(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
