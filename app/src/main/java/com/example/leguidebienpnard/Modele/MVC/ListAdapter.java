package com.example.leguidebienpnard.Modele.MVC;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leguidebienpnard.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.leguidebienpnard.Modele.MVC.FirstFragment.BASE_URL;
import static com.example.leguidebienpnard.Modele.MVC.MainActivity.userName;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<Objet> values;
    private Context context;
    private User user;
    public FragmentManager f_manager;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
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

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(User user, Context context) {
        this.values = user.getListeObjets();
        this.user = user;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.pattern_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.text.setText(values.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("nameObject", values.get(position).getName());
                Navigation.findNavController(v).navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
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
                            makeApiCallPost(user.getListeObjets());
                        }
                    }
                } else {
                    for(Objet o: user.getListeObjets()){
                        if(o.getName().equals(values.get(position).getName())){
                            o.setOwn(0);
                            makeApiCallPost(user.getListeObjets());
                        }
                    }
                }

            }
        });
    }

    public void makeApiCallPost(List<Objet> listeObjet){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GbpApi gbpApi = retrofit.create(GbpApi.class);

        Call<ResponseBody> call = gbpApi.postUserList(userName,listeObjet);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Donnée sauvegardée",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(context,"Une erreur est survenue",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"Pas de connexion internet",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }


}
