package frontend;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public abstract class AncestorListenerAdapter implements AncestorListener {
    @Override
    public void ancestorAdded(AncestorEvent event) {}

    @Override
    public void ancestorRemoved(AncestorEvent event) {}

    @Override
    public void ancestorMoved(AncestorEvent event) {}
}
