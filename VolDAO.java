package vol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolDAO {
    private final String url = "jdbc:mysql://localhost:3306/aeroport";
    private final String user = "root";
    private final String password = "";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Vol> getAllVols() {
        List<Vol> vols = new ArrayList<>();
        String sql = "SELECT * FROM vol";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                vols.add(new Vol(
                    rs.getString("numero_vol"),
                    rs.getString("ville_depart"),
                    rs.getString("ville_arrivee")
                ));
            }

        } catch (SQLException e) {
            System.out.println(" Erreur lors de la récupération des vols.");
            e.printStackTrace();
        }

        return vols;
    }

    public void ajouterVol(Vol vol) {
        String sql = "INSERT INTO vol (numero_vol, ville_depart, ville_arrivee) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vol.getNumeroVol());
            ps.setString(2, vol.getVilleDepart());
            ps.setString(3, vol.getVilleArrivee());
            ps.executeUpdate();

            System.out.println(" Vol ajouté avec succès.");

        } catch (SQLException e) {
            System.out.println(" Erreur lors de l'ajout du vol.");
            e.printStackTrace();
        }
    }

    public void modifierDestination(String numeroVol, String nouvelleDestination) {
        String sql = "UPDATE vol SET ville_arrivee = ? WHERE numero_vol = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nouvelleDestination);
            ps.setString(2, numeroVol);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println(" Destination modifiée avec succès.");
            } else {
                System.out.println(" Aucun vol trouvé avec ce numéro.");
            }

        } catch (SQLException e) {
            System.out.println(" Erreur lors de la modification de la destination.");
            e.printStackTrace();
        }
    }

    public Vol chercherVol(String numeroVol) {
        String sql = "SELECT * FROM vol WHERE numero_vol = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroVol);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Vol(
                    rs.getString("numero_vol"),
                    rs.getString("ville_depart"),
                    rs.getString("ville_arrivee")
                );
            }

        } catch (SQLException e) {
            System.out.println(" Erreur lors de la recherche du vol.");
            e.printStackTrace();
        }
        return null;
    }
}
