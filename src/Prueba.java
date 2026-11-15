public class Prueba {
    public static void main(String[] args) {
        String x = null;
        System.out.println(describir(x));
    }

    static String describir(String x) {
        if (x == null || x.isEmpty()) {
            return "vacio";
        }
        return x;
    }
}
