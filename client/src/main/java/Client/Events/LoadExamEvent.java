package Client.Events;

import Entities.SchoolOwned.ExamForm;

public class LoadExamEvent {

    private ExamForm examForm;
    private final String screen;

    public LoadExamEvent(ExamForm examForm, String screen) {
        this.examForm = examForm;
        this.screen = screen;
    }

    public ExamForm getExamForm() {
        return examForm;
    }

    public void setExamForm(ExamForm examForm) {
        this.examForm = examForm;
    }
}