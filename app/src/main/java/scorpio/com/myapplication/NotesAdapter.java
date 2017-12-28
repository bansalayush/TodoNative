package scorpio.com.myapplication;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sahusoft on 07/12/17.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MViewHolder>{

    private List<Note> notesList;

    public class MViewHolder extends RecyclerView.ViewHolder{
        public TextView title;

        public MViewHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
        }
    }
    public NotesAdapter(List<Note> notesList){
        this.notesList = notesList;
    }

    @Override
    public NotesAdapter.MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_row,parent,false);
        return new MViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.MViewHolder holder, int position) {
        Note note = notesList.get(position);
        System.out.println(note.getNote());
        holder.title.setText(note.getNote());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
