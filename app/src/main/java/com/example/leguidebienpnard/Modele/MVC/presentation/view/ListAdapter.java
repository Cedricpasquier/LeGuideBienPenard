package com.example.leguidebienpnard.Modele.MVC.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leguidebienpnard.Modele.MVC.presentation.controler.MainController;
import com.example.leguidebienpnard.Modele.MVC.presentation.model.Objet;
import com.example.leguidebienpnard.Modele.MVC.presentation.model.User;
import com.example.leguidebienpnard.R;

import java.util.List;
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<Objet> values;
    private User user;
    private MainController controller;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView text;
        public CheckBox checkBox;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            text = v.findViewById(R.id.firstLine);
            checkBox = v.findViewById(R.id.checkBox);
        }
    }


    public void add(int position, Objet item) {
        values.add(position, item);
        notifyItemInserted(position);
    }


    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    public ListAdapter(User user, MainController controller) {
        this.values = user.getListeObjets();
        this.user = user;
        this.controller = controller;
    }


    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.pattern_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, final int position) {

        holder.text.setText(values.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDetails(position,v);
            }
        });

        if(values.get(position).getOwn() == 0){
            holder.checkBox.setChecked(false);
        } else {
            holder.checkBox.setChecked(true);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for(Objet o: user.getListeObjets()){
                        if(o.getName().equals(values.get(position).getName())){
                            o.setOwn(1);
                            controller.makeApiCallPost(user.getListeObjets());
                        }
                    }
                } else {
                    for(Objet o: user.getListeObjets()){
                        if(o.getName().equals(values.get(position).getName())){
                            o.setOwn(0);
                            controller.makeApiCallPost(user.getListeObjets());
                        }
                    }
                }

            }
        });
    }

    public void navigateToDetails(int position, View v){
        Bundle bundle = new Bundle();
        bundle.putString("nameObject", values.get(position).getName());
        Navigation.findNavController(v).navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
    }


    @Override
    public int getItemCount() {
        return values.size();
    }


}
