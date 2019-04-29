package ar.edu.unq.desapp.grupoa.exception;

public class LoanOnCourseException extends RuntimeException {

    public LoanOnCourseException(){
        super("Can't take a loan if there is another already on course");
    }

}
