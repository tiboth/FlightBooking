package persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BookingJdbcRepository implements IRepository<Integer, Booking> {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public BookingJdbcRepository(Properties props){
        logger.info("Initializing BookingJdbcRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from bookings")) {
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
    public void save(Booking entity) {
        logger.traceEntry("saving booking {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into bookings values (?,?,?,?,?,?)")){
            preStmt.setInt(1,entity.getId());
            preStmt.setInt(2,entity.getFlightId());
            preStmt.setString(3,entity.getClientName());
            preStmt.setString(4,entity.getClientAddress());
            preStmt.setString(5,entity.getTourists());
            preStmt.setInt(6,entity.getNrSeats());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting booking with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from bookings where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Booking entity) {
        logger.traceEntry("updating booking with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update bookings set id=?,flightId=?,clientName=?,clientAddress=?,tourists=?,nrSeats=? where id=?")){
            preStmt.setInt(1,entity.getId());
            preStmt.setInt(2,entity.getFlightId());
            preStmt.setString(3,entity.getClientName());
            preStmt.setString(4,entity.getClientAddress());
            preStmt.setString(5,entity.getTourists());
            preStmt.setInt(6,entity.getNrSeats());
            preStmt.setInt(7,entity.getId());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Booking findOne(Integer integer) {
        logger.traceEntry("finding task with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from bookings where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    int flightId = result.getInt("flightId");
                    String clientName = result.getString("clientName");
                    String clientAddress = result.getString("clientAddress");
                    String tourists = result.getString("tourists");
                    int nrSeats = result.getInt("nrSeats");

                    Booking booking = new Booking(id, flightId, clientName, clientAddress, tourists, nrSeats);

                    logger.traceExit(booking);
                    return booking;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No booking found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<Booking> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Booking> bookings = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from booking")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int flightId = result.getInt("flightId");
                    String clientName = result.getString("clientName");
                    String clientAddress = result.getString("clientAddress");
                    String tourists = result.getString("tourists");
                    int nrSeats = result.getInt("nrSeats");

                    Booking booking = new Booking(id, flightId, clientName, clientAddress, tourists, nrSeats);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(bookings);
        return bookings;
    }



}
