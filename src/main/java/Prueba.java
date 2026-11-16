public class Prueba {
    public static void main(String[] args) {
        MensajeRepository repo = new MensajeRepository(MensajeRepository.URL_MEMORIA);

        for (String x : new String[] {null, "", "hola"}) {
            String resultado = describir(x);
            System.out.println(resultado);
            repo.guardar(x, resultado);
        }

        System.out.println("--- Guardados en H2 ---");
        for (Mensaje m : repo.listar()) {
            System.out.println(m);
        }
    }

    static String describir(String x) {
        if (x == null || x.isEmpty()) {
            return "vacio";
        }
        return x;
    }
}
