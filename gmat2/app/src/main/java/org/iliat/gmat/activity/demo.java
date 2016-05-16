package org.iliat.gmat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.github.lzyzsd.circleprogress.ArcProgress;

import org.iliat.gmat.R;
import org.iliat.gmat.adapter.ListTypeQuestionAdapter;
import org.iliat.gmat.constant.Constant;
import org.iliat.gmat.model.QuestionModel;
import org.iliat.gmat.model.TypeQuestionModel;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class demo extends AppCompatActivity {
    private ListView listTypeQuestion;
    private ArrayList<TypeQuestionModel> arrayList;
    private Realm realm;
    private RealmResults<QuestionModel> results;
    private RealmQuery<QuestionModel> query;
    private int totalAnswered;
    private int totalRightAnswer;
    private int totalQuestion;
    private ArcProgress arcProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sumary);
        initControl();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataForArcProgress();
        getSumaryTypeOfQuestion();
        listTypeQuestion.setAdapter(new ListTypeQuestionAdapter(this, arrayList));
    }

    private void initControl() {
        listTypeQuestion = (ListView) findViewById(R.id.ltv_type_question);
        arcProgress=(ArcProgress) findViewById(R.id.sumary_arc_progress);
        arrayList = new ArrayList<>();
        realm = Realm.getDefaultInstance();
    }

    private void getSumaryTypeOfQuestion(){
        query = realm.where(QuestionModel.class);
        for (int i = 0; i < Constant.QUESTION_TYPE.length; i++) {
            query.equalTo("type", Constant.QUESTION_TYPE[i]);
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
            arrayList.add(new TypeQuestionModel(Constant.QUESTION_TYPE[i], results.size(), totalAnswered, totalRightAnswer));
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
}
