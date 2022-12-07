package com.averycowan.screenprotector;

import java.awt.*;
import java.awt.event.WindowEvent;

public class LockMechanism {
    public static void lock() {
        // Run the applescript
        String[] command = {
                "osascript",
                "-e",
                "tell app \"System Events\" to key code 12 using {control down, command down}"
        };
        try {
            System.err.println("Locking screen");
            Process p = Runtime.getRuntime().exec(command);
            p.getInputStream().transferTo(System.out);
            p.getErrorStream().transferTo(System.err);
            p.waitFor();
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Listener implements java.awt.event.AWTEventListener {
        @Override
        public void eventDispatched(AWTEvent event) {
            if (event instanceof WindowEvent) {
                WindowEvent windowEvent = (WindowEvent) event;
                if (windowEvent.getID() == WindowEvent.WINDOW_ACTIVATED) {
                    return;
                }
            }
            System.out.println(event);
            LockMechanism.lock();
            System.exit(0);
        }
    }
}
