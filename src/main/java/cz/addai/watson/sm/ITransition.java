package cz.addai.watson.sm;

public interface ITransition {
    IState transit(IState source);
}
