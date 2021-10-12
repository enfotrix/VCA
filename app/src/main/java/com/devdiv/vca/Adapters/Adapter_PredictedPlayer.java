package com.devdiv.vca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devdiv.vca.Model.Model_PredictedPlayer;
import com.devdiv.vca.R;

import java.util.ArrayList;

public class Adapter_PredictedPlayer extends RecyclerView.Adapter<Adapter_PredictedPlayer.ViewHolder> {

    Context context;
    ArrayList<Model_PredictedPlayer> modelPredictedPlayerArrayList;

    public Adapter_PredictedPlayer(Context context, ArrayList<Model_PredictedPlayer> modelPredictedPlayerArrayList) {

        this.context = context;
        this.modelPredictedPlayerArrayList = modelPredictedPlayerArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_mysquad, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Model_PredictedPlayer model_mySquad = modelPredictedPlayerArrayList.get(position);

        holder.txt_century.setText(model_mySquad.getCentury());
        holder.txt_highscore.setText(model_mySquad.getHighscore());
        holder.txt_playerage.setText(model_mySquad.getPlayerage());
        holder.txt_strikeRate.setText(model_mySquad.getStriekrate());
        holder.txt_fifty.setText(model_mySquad.getFifyt());
        holder.txt_playername.setText(model_mySquad.getPlayername());
        holder.txt_matchplay.setText(model_mySquad.getMatchplay());
        holder.txt_wickets.setText(model_mySquad.getWickets());
        holder.txt_totalruns.setText(model_mySquad.getTotalruns());
        holder.txt_avg.setText(model_mySquad.getEconomy());

    }

    @Override
    public int getItemCount() {
        return modelPredictedPlayerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_matchplay, txt_totalruns, txt_century, txt_fifty,
                txt_highscore, txt_strikeRate, txt_wickets, txt_playername, txt_playerage, txt_avg;
        LinearLayout lay_stats;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            txt_playerage = itemView.findViewById(R.id.txt_playerage);
            txt_playername = itemView.findViewById(R.id.txt_playername);
            txt_wickets = itemView.findViewById(R.id.txt_wickets);
            txt_strikeRate = itemView.findViewById(R.id.txt_strikeRate);
            txt_highscore = itemView.findViewById(R.id.txt_highscore);
            txt_fifty = itemView.findViewById(R.id.txt_fifty);
            txt_century = itemView.findViewById(R.id.txt_century);
            txt_totalruns = itemView.findViewById(R.id.txt_totalruns);
            txt_matchplay = itemView.findViewById(R.id.txt_matchplay);
            txt_avg = itemView.findViewById(R.id.txt_avg);
            lay_stats = itemView.findViewById(R.id.lay_stats);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lay_stats.getVisibility() == View.VISIBLE) {
                        // Its visible
                        lay_stats.setVisibility(View.GONE);
                    } else {
                        // Either gone or invisible
                        lay_stats.setVisibility(View.VISIBLE);
                    }
                }
            });


        }
    }
}
