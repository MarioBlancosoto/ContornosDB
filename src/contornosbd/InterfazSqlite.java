package contornosbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class InterfazSqlite {

    static String url = "/home/mbs/NetBeansProjects/ContornosBD/Alumno.db";
    private Connection conectar;
    private Statement st;
    ResultSet resultado;
    String dato1, dato2, dato3, dato4;
    int contar;

    /**
     * Recibe por parámetro la dirección en la cual se haya la base de datos
     *
     * @param url dirección donde se encuentra la base de datos para la conexión
     */
    public InterfazSqlite(String url) {

        this.url = url;

    }

    public InterfazSqlite() {
    }

    /**
     * Conecta con la base de datos a traves de una url dada por parámetro
     *
     * @return true si la conexión se realizón con éxito y false si falló
     */
    public boolean conectar() {

        try {
            conectar = DriverManager.getConnection("jdbc:sqlite:" + url);

            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    /**
     * Inserta una fila en la tabla,recibe 3 parámetros correspondientes a 3
     * atributos de la misma también nos dice el número de filas insertadas
     *
     * @param dato1 corresponde al dni (clave primaria , no se puede repetir)
     * @param dato2 corresponde al nombre
     * @param dato3 corresponde al apellido
     *
     */
    public void insertar(String dato1, String dato2, String dato3) {
        try {
            PreparedStatement ps = conectar.prepareStatement("Insert into Alumno(dni,nombre,apellidos) "
                    + "values(?,?,?)");
            this.contarFilas();
            ps.setString(1, dato1);
            ps.setString(2, dato2);
            ps.setString(3, dato3);
            ps.execute();
            contar = ps.getUpdateCount();
            System.out.println(contar + " Fila/s Insertadas");
        } catch (SQLException ex) {
            System.out.println("Error al insertar la fila " + ex.getMessage());
        }
    }

    /**
     * Borra filas de la tabla pasándole la clave primaria de la fila a borrar
     * nos dice el número de filas borradas con rowcount
     *
     * @param dato1 clave por la que borraremos el campo
     */
    public void borrar(String dato1) {
        try {
            Statement st = conectar.createStatement();
            st.execute("DELETE from Alumno where dni =" + "'" + dato1 + "'");
            contar = st.getUpdateCount();
            System.out.println(contar + " Fila/s Borradas");
        } catch (SQLException ex) {
            System.out.println("El dni introducido es erroneo " + ex.getMessage());
        }

    }

    /**
     * Muestra todos los campos de la base creada anteriormente implementado
     * Contarfilas(ver método contarFilas)
     */
    public void mostrar() {

        try {
            PreparedStatement ver = conectar.prepareStatement("Select * from Alumno");
            resultado = ver.executeQuery();
            System.out.println("insertado con exito");
            while (resultado.next()) {
                System.out.println("dni : " + resultado.getString("dni"));
                System.out.println("nombre : " + resultado.getString("nombre"));
                System.out.println("apellidos : " + resultado.getString("apellidos"));

                this.contarFilas();
            }
        } catch (SQLException ex) {
            System.out.println("Error al insertar : " + ex.getMessage());
        }

    }

    /**
     * Modifica un registro de la tabla basándose en el dni(clave primaria) para
     * poder borrar el registro exacto también nos muestra el número de
     * registros modificados
     *
     * @param dato1 nuevo dni a aplicar al campo
     * @param dato2 nuevo nombre a aplicar al campo
     * @param dato3 nuevos apellidos a aplicar al campo
     * @param dato4 clave primaria que señala cual queremos modificar (dni)
     */
    public void modificar(String dato1, String dato2, String dato3, String dato4) {

        try {
            PreparedStatement actualiza = conectar.prepareStatement("update Alumno set dni ='" + dato1 + "',nombre ='" + dato2 + "',apellidos ='" + dato3 + "' where dni=" + dato4);
            actualiza.execute();

            contar = actualiza.getUpdateCount();
            System.out.println(contar + "- " + " Registro/s actualizado/s");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el registro, verifique que ha introducido bien los datos a actualizar: " + ex.getMessage());
        }

    }

    /**
     * Se usa para contar el total de filas en una base de datos,usada en el
     * método mostrar (ver método mostrar)
     */
    public void contarFilas() {

        try {
            Statement st = conectar.createStatement();
            ResultSet result = st.executeQuery("Select count (*) as rowcount from Alumno");
            result.next();
            int cuenta = result.getInt("rowcount");
            System.out.println(cuenta + " - " + "Filas en total");
        } catch (SQLException ex) {
            System.out.println("Error de conexión : " + ex.getMessage());
        }
    }
}
