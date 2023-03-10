package com.cohelp.task_for_stu.ui.adpter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.biz.QuestionBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.vo.Question;
import com.cohelp.task_for_stu.utils.BasicUtils;
import com.cohelp.task_for_stu.utils.T;
import com.leon.lfilepickerlibrary.utils.StringUtils;

import java.util.List;

public class ManagerQuestionAdapter extends RecyclerView.Adapter<ManagerQuestionAdapter.QuestionItemViewHolder> {
    List<Question> questions;
    Context context;
    private LayoutInflater eInflater;
    QuestionBiz questionBiz;
    public ManagerQuestionAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questions = questionList;
        this.eInflater = LayoutInflater.from(context);
        questionBiz = new QuestionBiz();
    }

    @NonNull
    @Override
    public QuestionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = eInflater.inflate(R.layout.item_manager_base_task, parent, false);
        return new ManagerQuestionAdapter.QuestionItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionItemViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.title.setText(question.getContext().getTitle());
        holder.author.setText(question.getUser().getNickName());
        holder.content.setText(question.getContext().getContentDesc());
        holder.state.setText(question.getContext().getCurrentState());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionItemViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView author;
        public TextView content;
        public TextView state;
        public Button jug;
        public Button delete;
        public QuestionItemViewHolder(@NonNull View itemView){
            //TODO ????????????????????????????????????????????????????????????
            super(itemView);
            title = itemView.findViewById(R.id.id_tv_title);
            author = itemView.findViewById(R.id.id_tv_author);
            content = itemView.findViewById(R.id.id_tv_content);
            state = itemView.findViewById(R.id.id_tv_state);
            jug = itemView.findViewById(R.id.id_btn_jug);
            delete = itemView.findViewById(R.id.id_btn_del);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //???????????????????????????
                    Question question = questions.get(getLayoutPosition());
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(question.getContext().getTitle() + "???????????????");
                    TextView textView = new TextView(view.getContext());
                    textView.setText(question.getContext().getContentDesc());
                    ScrollView scrollView = new ScrollView(view.getContext());
                    scrollView.addView(textView);
                    builder.setView(scrollView);
                    builder.setPositiveButton("?????????",null);
                    builder.show();
                }
            });

            jug.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Question question = questions.get(getLayoutPosition());
                    if(!question.getContext().getCurrentState().equals(Config.WAIT_PASSED)){
                        T.showToast("????????????????????????????????????^^");
                        return;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("????????????");
                    EditText editText = new EditText(view.getContext());
                    editText.setHint("???????????????????????????????????????????????????????????????");
                    builder.setView(editText);
                    builder.setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            BasicUtils.startLoadingProgress(view.getContext());
                            questionBiz.save(question, new CommonCallback<String>() {
                                @Override
                                public void onError(Exception e) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(e.getMessage());
                                }

                                @Override
                                public void onSuccess(String response) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(response);
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(StringUtils.isEmpty(editText.getText().toString())){
                                T.showToast("????????????????????????");
                                return;
                            }
                            BasicUtils.startLoadingProgress(view.getContext());
                            question.getContext().setCurrentState(Config.NOT_PASSED);
                            question.getContext().setStateMsg(editText.getText().toString());
                            questionBiz.save(question, new CommonCallback<String>() {
                                @Override
                                public void onError(Exception e) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(e.getMessage());
                                }

                                @Override
                                public void onSuccess(String response) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(response);
                                }
                            });
                        }
                    });
                    builder.show();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("????????????");
                    builder.setMessage("?????????????????????????????????????????????????????????????????????");
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            BasicUtils.startLoadingProgress(view.getContext());
                            Question question = questions.get(getLayoutPosition());
                            questionBiz.deleteQuestion(question.getContext().getId(), new CommonCallback<String>() {
                                @Override
                                public void onError(Exception e) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(e.getMessage());
                                }

                                @Override
                                public void onSuccess(String response) {
                                    BasicUtils.stopLoadingProgress();
                                    T.showToast(response);
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("??????",null);
                    builder.show();
                }
            });
        }
    }
}
