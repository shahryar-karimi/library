package ir.shahryar.library.Exception;

public class AlreadyExistPack extends Exception{
    public AlreadyExistPack() {
        super("This user already has an active pack");
    }
}
