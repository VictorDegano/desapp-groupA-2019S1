package ar.edu.unq.desapp.grupoa.exception.account;

public class NoLoanOnCourseException extends RuntimeException{

    public NoLoanOnCourseException(){
        super("Can't pay a quota if there is no loan on course");
    }
}
