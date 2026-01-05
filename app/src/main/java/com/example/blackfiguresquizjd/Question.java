package com.example.blackfiguresquizjd;

public class Question {
    private String questionText;
    private boolean rightAnswer;
    private String hint;


    public Question() {
    }


    public Question(String questionText, boolean rightAnswer) {
        this.questionText = questionText;
        this.rightAnswer = rightAnswer;
    }


    public Question(String questionText, boolean rightAnswer, String hint) {
        this.questionText = questionText;
        this.rightAnswer = rightAnswer;
        this.hint = hint;
    }


    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public boolean isRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}