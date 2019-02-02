package com.example.learn.entiry.student.exam;

import java.util.List;

public class ExamBean {
    private int examId;
    private String examName;
    private String teacherName;
    private String totalScore;
    private List<Question> questions;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
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

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "ExamBean{" +
                "examId=" + examId +
                ", examName='" + examName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", totalScore='" + totalScore + '\'' +
                ", questions=" + questions.get(0).toString() +
                '}';
    }
}
