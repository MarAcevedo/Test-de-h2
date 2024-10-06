import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Animal {

    private static final String SQL_DROP_CREATE = "DROP TABLE IF EXISTS ANIMALES; CREATE " +
            "TABLE ANIMALES (ID INT PRIMARY KEY, " +
            " NOMBRE VARCHAR (100) NOT NULL," +
            " TIPO VARCHAR (100) NOT NULL)";

    private static final  String SQL_INSERT = "INSERT INTO ANIMALES VALUES " +
            "(1 , 'Pelusa', 'gato'),(2, 'Gaston', 'perro'),(3, 'Sheldon', 'tortuga')";

    private static final String SQL_SELECT = "SELECT * FROM ANIMALES";

    private static final String SQL_DELETE = "DELETE FROM ANIMALES WHERE ID=3";

    private static final Logger LOGGER = Logger.getLogger(Animal.class);
    public static void main(String[] args){

        Connection connection = null;

        try{
            connection =getConnection();

            Statement statement = connection.createStatement();

            statement.execute(SQL_DROP_CREATE);
            statement.execute(SQL_INSERT);

            //Consulta
            //Donde se guarda "Result Set"
            ResultSet rs = statement.executeQuery(SQL_SELECT);

            //Imprimir consola
            while(rs.next()){
                System.out.println("Nombre: "+ rs.getNString(2) + " - "
                        + "Tipo: " +rs.getNString(3));
                LOGGER.info("Nombre: "+ rs.getNString(2) + " - "
                        + "Tipo: " +rs.getNString(3));
            }

            statement.execute(SQL_DELETE);

            LOGGER.warn("Se borro el animal con el ID = 3");

            rs = statement.executeQuery(SQL_SELECT);

            System.out.println("--------------------------------------------------");

            while (rs.next()){
                System.out.println("Nombre: "+ rs.getNString(2) + " - "
                        + "Tipo: " +rs.getNString(3));
                LOGGER.info("Nombre: "+ rs.getNString(2) + " - "
                        + "Tipo: " +rs.getNString(3));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                e.printStackTrace();

            }
        }


    }
    public static Connection getConnection() throws Exception{
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/AccesoBD","sa", "sa");
    }
}
