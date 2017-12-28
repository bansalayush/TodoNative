package scorpio.com.myapplication;

/**
 * Created by sahusoft on 07/12/17.
 */

public class Note {
    private String noteText;
    private String filename;

    public Note(){

    }

    public Note(String noteText,String filename){
        this.noteText = noteText;
        this.filename = filename;
    }
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getNote(){
        return noteText;
    }

    public void setNoteText(String noteText){
        this.noteText = noteText;
    }
}
