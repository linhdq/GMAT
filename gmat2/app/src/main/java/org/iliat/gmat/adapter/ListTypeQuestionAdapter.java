package org.iliat.gmat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.iliat.gmat.R;
import org.iliat.gmat.model.TypeQuestionModel;

import java.util.ArrayList;

/**
 * Created by MrBom on 5/13/2016.
 */
public class ListTypeQuestionAdapter extends BaseAdapter {

    private Context context = null;
    private ArrayList<TypeQuestionModel> list = null;
    private LayoutInflater inflater = null;

    public ListTypeQuestionAdapter(Context context, ArrayList<TypeQuestionModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        }
        View view = inflater.inflate(R.layout.item_question_type_on_list, null);
        TypeQuestionModel typeQuestionModel = list.get(position);
        if (view != null && typeQuestionModel != null) {
            TextView txtPackName = (TextView) view.findViewById(R.id.sumary_pack_name);
            TextView txtTotalQues = (TextView) view.findViewById(R.id.sumary_total_question);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.sumary_progress);
            txtPackName.setText(typeQuestionModel.getTypeName());
            txtTotalQues.setText(typeQuestionModel.getTotalQuestion()+" questions");
            progressBar.setMax(typeQuestionModel.getTotalQuestion());
            progressBar.setProgress(typeQuestionModel.getTotalRightAnswer());
        }
        return view;
    }
}
