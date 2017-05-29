package contornosbd;

import javax.swing.JOptionPane;

public class ContornosBD {

    public static void main(String[] args) {

        InterfazSqlite sq = new InterfazSqlite();

        if (sq.conectar()) {
            System.out.println("Conexión realizada Correctamente");

        } else {

            System.out.println("Error al Conectar ");
        }

        int select;
        do {

            select = Integer.parseInt(JOptionPane.showInputDialog(null, "Selección \n 1. Insertar Alumno \n 2.Mostrar Alumnos \n 3.Borrar Alumno \n 4.Modificar"));
            switch (select) {

                case 1:
                    sq.insertar(JOptionPane.showInputDialog("Dni"), JOptionPane.showInputDialog("nombre"), JOptionPane.showInputDialog("Apellidos"));
                    break;
                case 2:
                    sq.mostrar();
                    break;
                case 3:
                    sq.borrar(JOptionPane.showInputDialog("Inserta el dni del alumno a borrar"));
                    break;
                case 4:
                    sq.modificar(JOptionPane.showInputDialog("Inserta el nuevo dni "), JOptionPane.showInputDialog(" Inserta el nuevo nombre "), JOptionPane.showInputDialog("Inserta los nuevos apellidos"), JOptionPane.showInputDialog("Inserta el dni del alumno a modificar"));
                    break;

            }

        } while (select != 0);
    }
}
