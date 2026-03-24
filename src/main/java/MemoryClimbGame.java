import java.util.List;

public class MemoryClimbGame implements IObservable{
    private Board board;
    private Route route;
    private RouteGenerationStrategy strategy;
    private List<IObserver> observers;


    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    public void notifyObservers(){
        for (IObserver o : observers){
            o.update();
        }
    }
}
