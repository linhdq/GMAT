package org.iliat.gmat.fragment;

import android.support.annotation.Nullable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.github.lzyzsd.circleprogress.ArcProgress;
import org.iliat.gmat.R;
import org.iliat.gmat.adapter.ListTypeQuestionAdapter;
import org.iliat.gmat.model.QuestionModel;
import org.iliat.gmat.model.QuestionTypeModel;
import org.iliat.gmat.model.TypeQuestionModel;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SumaryFragment extends BaseFragment {
    private ListView listTypeQuestion;
    private ArrayList<TypeQuestionModel> arrayList;
    private Realm realm;
    private RealmResults<QuestionModel> results;
    private RealmQuery<QuestionModel> query;
    private RealmResults<QuestionTypeModel> resultTypes;
    private RealmQuery<QuestionTypeModel> queryType;
    private int totalAnswered;
    private int totalRightAnswer;
    private int totalQuestion;
    private ArcProgress arcProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sumary,container,false);
        listTypeQuestion = (ListView) view.findViewById(R.id.ltv_type_question);
        arcProgress=(ArcProgress) view.findViewById(R.id.sumary_arc_progress);
        initControl();
        getDataForArcProgress();
        getTypeQuestion();
        getSumaryTypeOfQuestion();
        listTypeQuestion.setAdapter(new ListTypeQuestionAdapter(view.getContext(), arrayList));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initControl() {

        arrayList = new ArrayList<>();
        realm = Realm.getDefaultInstance();
    }

    private void getSumaryTypeOfQuestion(){
        query = realm.where(QuestionModel.class);
        for (int i = 0; i < resultTypes.size(); i++) {
            query.equalTo("type", resultTypes.get(i).getCode());
            results = query.findAll();
            totalAnswered = totalRightAnswer = 0;
            for (int j = 0; j < results.size(); j++) {
                if (results.get(j).getUserAnswer() != 0) {
                    totalAnswered++;
                }
                if (results.get(j).getUserAnswer() == results.get(j).getRightAnswerIndex()) {
                    totalRightAnswer++;
                }
            }
            arrayList.add(new TypeQuestionModel(resultTypes.get(i).getDetail(), results.size(), totalAnswered, totalRightAnswer));
        }
    }
    private void getDataForArcProgress(){
        query=realm.where(QuestionModel.class);
        results=query.findAll();
        totalQuestion=results.size();
        arcProgress.setMax(totalQuestion);
        query.notEqualTo("userAnswer",0);
        results=query.findAll();
        totalAnswered=results.size();
        arcProgress.setProgress(totalAnswered);
        arcProgress.setBottomText(totalAnswered+"/"+totalQuestion);
    }


    private void getTypeQuestion(){
        queryType=realm.where(QuestionTypeModel.class);
        resultTypes=queryType.findAll();
    }
}
