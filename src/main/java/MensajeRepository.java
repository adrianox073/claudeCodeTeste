import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MensajeRepository {

    /** BD en memoria; DB_CLOSE_DELAY=-1 la mantiene viva mientras dure la JVM. */
    public static final String URL_MEMORIA = "jdbc:h2:mem:untitled;DB_CLOSE_DELAY=-1";

    private final String url;

    public MensajeRepository(String url) {
        this.url = url;
        try (Connection c = conectar(); Statement st = c.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS mensaje ("
                    + "id IDENTITY PRIMARY KEY, "
                    + "texto VARCHAR(1000), "
                    + "resultado VARCHAR(1000) NOT NULL, "
                    + "fecha TIMESTAMP NOT NULL)");
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo crear la tabla mensaje", e);
        }
    }

    public Mensaje guardar(String texto, String resultado) {
        LocalDateTime fecha = LocalDateTime.now();
        String sql = "INSERT INTO mensaje (texto, resultado, fecha) VALUES (?, ?, ?)";
        try (Connection c = conectar();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, texto);
            ps.setString(2, resultado);
            ps.setTimestamp(3, Timestamp.valueOf(fecha));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                return new Mensaje(keys.getLong(1), texto, resultado, fecha);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo guardar el mensaje", e);
        }
    }

    public List<Mensaje> listar() {
        String sql = "SELECT id, texto, resultado, fecha FROM mensaje ORDER BY id";
        try (Connection c = conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Mensaje> mensajes = new ArrayList<>();
            while (rs.next()) {
                mensajes.add(new Mensaje(
                        rs.getLong("id"),
                        rs.getString("texto"),
                        rs.getString("resultado"),
                        rs.getTimestamp("fecha").toLocalDateTime()));
            }
            return mensajes;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron listar los mensajes", e);
        }
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
