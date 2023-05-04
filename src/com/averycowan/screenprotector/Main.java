package com.averycowan.screenprotector;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.util.concurrent.locks.Lock;

public class Main {
    public static final int WINDOW_SIZE = 100;

    public static final int WAIT_TIME_MS = 1500;

    public static void main(String[] args) {
        VerboseJFrame frame = new VerboseJFrame();
        frame.setSize(WINDOW_SIZE, WINDOW_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screensize);
        frame.setBackground(new Color(0, 0, 0, 128));
        frame.setVisible(true);
        // Wait the specified amount of time before showing the frame
        System.out.println("Waiting " + WAIT_TIME_MS + "ms before showing frame");
        try {
            Thread.sleep(WAIT_TIME_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Set location to around the mouse
        frame.setVisible(false);
        frame.setBackground(new Color(0, 0, 0, 0));
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        Point windowLocation = new Point(mouseLocation.x - WINDOW_SIZE / 2, mouseLocation.y - WINDOW_SIZE / 2);
        frame.setLocation(windowLocation);
        System.out.println("Showing frame");
        frame.setVisible(true);
        Point windowLocationAfter = frame.getLocationOnScreen();
        // Check that the window was correctly moved
        if (!windowLocationAfter.equals(windowLocation)) {
            System.err.println("Window was not moved to the correct location");
            System.err.println("Expected: " + windowLocation);
            System.err.println("Actual: " + windowLocationAfter);
            System.err.println("Mouse location: " + mouseLocation);
            System.exit(1);
        }
        // Sleep for 200ms to allow the window to be drawn
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Adding listener");
        // The listener calls System.exit(0)
        frame.addAWTEventListener(new LockMechanism.Listener());
        // On shutdown, trigger the lock
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LockMechanism.lock();
            }
        });
    }
}