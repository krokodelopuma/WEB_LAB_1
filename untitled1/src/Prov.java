public class Prov {
    public static boolean popod(int x, float y, int r) {
        return pp(x, y, r) || mp(x, y, r) || mm(x, y, r);
    }

    private static boolean pp(int x, float y, int r) {
        return x >= 0 && y >= 0 && x <= r && y <= (r-x)/2;
    }

    private static boolean mp(int x, float y, int r) {
        return x <= 0 && y >= 0 && x >= -r && y <= r/2;
    }

    private static boolean mm(int x, float y, int r) {
        return x <= 0 && y <= 0 && x >= -r && y >= -r && ((Math.pow(x, 2) + Math.pow(y, 2) - Math.pow(r, 2)) <= 0);
    }
}
