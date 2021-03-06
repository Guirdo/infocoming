package mx.edu.itch.isc.infocoming.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import mx.edu.itch.isc.infocoming.excepciones.ContrasenaException;
import mx.edu.itch.isc.infocoming.interfacesbd.InterfazBD;
import mx.edu.itch.isc.infocoming.interfacesgraficas.Pantalla;
import mx.edu.itch.isc.infocoming.interfacesgraficas.DMGestionUsuario;

public class ManejadorModificarContrasena implements ActionListener {

    private DMGestionUsuario v;
    private InterfazBD intBD;
    private Pantalla vistaAnterior;

    ManejadorModificarContrasena(InterfazBD intBD) {
        this.v = new DMGestionUsuario();
        this.intBD = intBD;

        v.btnModificar.addActionListener(this);

        v.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == v.btnModificar) {
            try {
                this.manejaEventoModificaContrasena();
            } catch (ContrasenaException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Mensaje de error",JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void manejaEventoModificaContrasena() throws ContrasenaException, SQLException {
        char[] nueva, confirmacion;
        boolean iguales = false;
        nueva = v.tfNuevoContra.getPassword();
        confirmacion = v.tfConfirmarContra.getPassword();
        if (nueva.length == confirmacion.length) {
            for (int i = 0; i < nueva.length; i++) {
                if (nueva[i] != confirmacion[i]) {
                    iguales = false;
                    break;
                } else {
                    iguales = true;
                }

            }

        }
        if (iguales) {
            String usuario;
            String contra = "";
            usuario = (String) v.cbUsuario.getSelectedItem();

            for (char c : nueva) {
                contra += c;
            }
            intBD.actualizar("alter user '" + usuario + "' identified by '" + contra + "'");
            v.tfNuevoContra.setText("");
            v.tfConfirmarContra.setText("");
            JOptionPane.showMessageDialog(null, "Contraseña modificacada con exito","Modificacion exitosa",JOptionPane.INFORMATION_MESSAGE);
        } else {
            throw new ContrasenaException("Las contraseñas no coinciden");
        }
    }

}
