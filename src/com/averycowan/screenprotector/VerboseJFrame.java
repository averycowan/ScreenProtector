package com.averycowan.screenprotector;

import javax.swing.JFrame;
import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.util.List;

public class VerboseJFrame extends JFrame {
    List<AWTEventListener> listeners = new java.util.ArrayList<AWTEventListener>();
    public VerboseJFrame() {
        super();
        this.enableEvents(-1l);
    }
    @Override
    public void processEvent(AWTEvent e) {
        for (AWTEventListener listener : listeners) {
            listener.eventDispatched(e);
        }
        super.processEvent(e);
    }
    public void addAWTEventListener(AWTEventListener listener) {
        listeners.add(listener);
    }
}
