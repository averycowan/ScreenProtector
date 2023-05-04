package com.averycowan.screenprotector;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class LockMechanism {
    static {
        String libname = "nativelock";
        String libfilename = "lib" + libname + ".dylib";
        URL resource = LockMechanism.class.getResource(libfilename);
        if (resource == null) {
            System.err.println("Unable to find resource at path " + libfilename.toString());
            System.exit(1);
        }
        System.out.println("Loading native library from " + resource);
        try {
            InputStream stream = resource.openStream();
            Path path = Files.createTempFile(null, "dylib");
            Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
            System.load(path.toAbsolutePath().toString());
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void lock() {
        LockMechanism.nativeLock();
    }

    private static native void nativeLock();

    public static class Listener implements java.awt.event.AWTEventListener {
        @Override
        public void eventDispatched(AWTEvent event) {
            System.out.println(event);
            if (event instanceof WindowEvent) {
                WindowEvent windowEvent = (WindowEvent) event;
                if (windowEvent.getID() == WindowEvent.WINDOW_ACTIVATED) {
                    return;
                }
            }
            System.exit(0);
        }
    }
}
