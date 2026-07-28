package com.minigit.utils;

import java.io.*;

/**
 * Utility class for object serialization
 */
public class SerializationUtil {
    
    /**
     * Serializes an object to a file
     */
    public static void serialize(Object obj, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
        }
    }
    
    /**
     * Deserializes an object from a file
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(File file, Class<T> clazz) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (T) ois.readObject();
        }
    }
}
