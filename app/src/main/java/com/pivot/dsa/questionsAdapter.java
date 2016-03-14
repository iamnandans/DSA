package com.pivot.dsa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by shanthan on 3/13/2016.
 */
public class questionsAdapter extends RecyclerView.Adapter<questionsAdapter.myViewHolder> {
    private LayoutInflater inflater;
    List<QuestionOption> qOptions = Collections.emptyList();

    public questionsAdapter(Context context, List<QuestionOption> qOptions) {
        inflater = LayoutInflater.from(context);
        this.qOptions = qOptions;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.question_custom_row,parent,false);
        myViewHolder vHolder = new myViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        QuestionOption qOpt = qOptions.get(position);

        holder.optionNo.setText(qOpt.optionName);
        holder.optionValue.setText(qOpt.optionValue);
    }

    @Override
    public int getItemCount() {
        return qOptions.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView optionNo;
        TextView optionValue;

        public myViewHolder(View itemView) {
            super(itemView);
            optionNo = (TextView) itemView.findViewById(R.id.optionNo);
            optionValue = (TextView) itemView.findViewById(R.id.optionValue);
        }
    }
}
