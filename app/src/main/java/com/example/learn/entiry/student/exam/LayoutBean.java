package com.example.learn.entiry.student.exam;

import java.util.List;

public class LayoutBean {
    private int layouttype;
    private String examName;
    private String teacherName;
    private int totalScore;
    private String score;
    private String questionId;
    private String title;
    private String question;
    private String standardanswer;
    private String answer;
    private String myScore;
    private int myTotalScore;
    private List<Option> options;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getLayouttype() {
        return layouttype;
    }

    public void setLayouttype(int layouttype) {
        this.layouttype = layouttype;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStandardanswer() {
        return standardanswer;
    }

    public void setStandardanswer(String standardanswer) {
        this.standardanswer = standardanswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMyScore() {
        return myScore;
    }

    public void setMyScore(String myScore) {
        this.myScore = myScore;
    }

    public int getMyTotalScore() {
        return myTotalScore;
    }

    public void setMyTotalScore(int myTotalScore) {
        this.myTotalScore = myTotalScore;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "LayoutBean{" +
                "layouttype=" + layouttype +
                ", examName='" + examName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", totalScore=" + totalScore +
                ", title='" + title + '\'' +
                ", question='" + question + '\'' +
                ", options=" + options +
                '}';
    }
}
