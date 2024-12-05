package frontend;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * Abstract adapter class for receiving ancestor events
 * Easy way to handle ancestor events without having to implement all the methods of the AncestorListener interface
 */
public abstract class AncestorListenerAdapter implements AncestorListener {
    /**
     * invoked when an ancestor is added to the component hierarchy
     * @param event providing details about the added ancestor
     */
    @Override
    public void ancestorAdded(AncestorEvent event) {}
    /**
     * invoked when an ancestor is removed from the component hierarchy
     * @param event providing details about the removed ancestor
     */

    @Override
    public void ancestorRemoved(AncestorEvent event) {}
    /**
     * invoked when an ancestor is removed from the component hierarchy
     * @param event providing details about the moved ancestor
     */

    @Override
    public void ancestorMoved(AncestorEvent event) {}
}
