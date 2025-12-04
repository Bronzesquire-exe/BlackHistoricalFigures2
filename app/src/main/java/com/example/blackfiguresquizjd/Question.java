package com.example.blackfiguresquizjd;

public class Question {
   private final String questionText;
   private final boolean RightAnswer;


   public Question(String questionText, boolean RightAnswer){
       this.questionText=questionText;
       this.RightAnswer=RightAnswer;
   }
public String getQuestionText(){
       return questionText;
    }
   public boolean isRightAnswer(){
       return RightAnswer;
   }

}

