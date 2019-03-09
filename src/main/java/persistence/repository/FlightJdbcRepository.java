package persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.model.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FlightJdbcRepository implements IRepository<Integer, Flight> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public FlightJdbcRepository(Properties props){
        logger.info("Initializing FlightRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as SIZE from flights")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(Flight entity) {
        logger.traceEntry("saving flight {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into flights (departureDate, departureTime, airport, destination, places) values (?,?,?,?,?)")){
            preStmt.setString(1,entity.getDepartureDate());
            preStmt.setString(2,entity.getDepartureTime());
            preStmt.setString(3,entity.getAirport());
            preStmt.setString(4,entity.getDestination());
            preStmt.setInt(5,entity.getPlaces());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting flight with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from flights where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    //@Override
    public void deleteAll( ) {
        logger.traceEntry("deleting all flight");
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from flights")) {
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Flight entity) {
        logger.traceEntry("updating flight with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update flights set departureDate=?,departureTime=?,airport=?,destination=?,places=? where id=?")){
            preStmt.setString(1,entity.getDepartureDate());
            preStmt.setString(2,entity.getDepartureTime());
            preStmt.setString(3,entity.getAirport());
            preStmt.setString(4,entity.getDestination());
            preStmt.setInt(5,entity.getPlaces());
            preStmt.setInt(6,integer);

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Flight findOne(Integer integer) {
        logger.traceEntry("finding flight with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from flights where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String date = result.getString("departureDate");
                    String hour = result.getString("departureTime");
                    String airport = result.getString("airport");
                    String destination = result.getString("destination");
                    int places = result.getInt("places");

                    Flight flight = new Flight(id, date, hour, airport, destination, places);
                    logger.traceExit(flight);
                    return flight;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No flight found with id {}", integer);

        return null;
    }

    public Iterable<Flight> findAllFlightsWithDestinationAndDate(String destination, String departureDate) {
        logger.traceEntry("finding flight with destination {} and date {}",destination, departureDate);
        Connection con=dbUtils.getConnection();
        List<Flight> flights=new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from flights where destination=? and departureDate=? ")){
            preStmt.setString(1,destination);
            preStmt.setString(2,departureDate);

            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String date = result.getString("departureDate");
                    String hour = result.getString("departureTime");
                    String airport = result.getString("airport");
                    String dest = result.getString("destination");
                    int places = result.getInt("places");

                    Flight flight = new Flight(id, date, hour, airport, dest, places);
                    flights.add(flight);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit(flights);
        return flights;
    }

    public Iterable<Flight> findAllFlightsWithDestinationAndDateAndTime(String destination, String departureDate, String departureTime) {
        logger.traceEntry("finding flight with destination {} and date {} and hour {}",destination, departureDate, departureTime);
        Connection con=dbUtils.getConnection();
        List<Flight> flights=new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from flights where destination=? and departureDate=? and departureTime=?")){
            preStmt.setString(1,destination);
            preStmt.setString(2,departureDate);
            preStmt.setString(3,departureTime);

            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String date = result.getString("departureDate");
                    String hour = result.getString("departureTime");
                    String airport = result.getString("airport");
                    String dest = result.getString("destination");
                    int places = result.getInt("places");

                    Flight flight = new Flight(id, date, hour, airport, dest, places);
                    flights.add(flight);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit(flights);
        return flights;
    }

    @Override
    public Iterable<Flight> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Flight> flights=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from flights")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String date = result.getString("departureDate");
                    String hour = result.getString("departureTime");
                    String airport = result.getString("airport");
                    String destination = result.getString("destination");
                    int places = result.getInt("places");

                    Flight flight = new Flight(id, date, hour, airport, destination, places);
                    flights.add(flight);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(flights);
        return flights;
    }
}
