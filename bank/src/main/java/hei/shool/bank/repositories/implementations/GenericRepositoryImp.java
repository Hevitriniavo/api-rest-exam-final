package hei.shool.bank.repositories.implementations;

import hei.shool.bank.annotations.Id;
import hei.shool.bank.config.DatabaseParam;
import hei.shool.bank.config.DatabaseSetting;
import hei.shool.bank.repositories.GenericRepository;
import hei.shool.bank.repositories.Identifiable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

@Repository
public class GenericRepositoryImp<T extends Identifiable<ID>, ID> extends DatabaseSetting implements GenericRepository<T, ID>  {
    public GenericRepositoryImp(DatabaseParam config) {
        super(config);
    }

    @Override
    public  T saveOrUpdate(T toSave) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {

            boolean isInsert = toSave.getId() == null;
            String query = isInsert ? this.getQueryInsert(toSave) : this.getQueryUpdate(toSave);

            con = this.getConnection();
            stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int idColumnName = 0;
            Field[] fields = toSave.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    continue;
                }
                idColumnName++;
                Object value = field.get(toSave);
                Class<?> fieldType = field.getType();
                if (value == null) {
                    stmt.setNull(idColumnName, Types.NULL);
                    continue;
                }
                switch (fieldType.getSimpleName()) {
                    case "String" -> stmt.setString(idColumnName, (String) value);
                    case "Integer", "int" -> stmt.setInt(idColumnName, (Integer) value);
                    case "Long", "long" -> stmt.setLong(idColumnName, (Long) value);
                    case "Double", "double" -> stmt.setDouble(idColumnName, (Double) value);
                    case "Date" -> stmt.setTimestamp(idColumnName, new Timestamp(((Date) value).getTime()));
                    case "LocalDateTime" -> stmt.setTimestamp(idColumnName, Timestamp.valueOf((LocalDateTime) value));
                    case "LocalDate" -> stmt.setTimestamp(idColumnName, Timestamp.valueOf(((LocalDate) value).atStartOfDay()));
                    case "Instant" -> stmt.setTimestamp(idColumnName, Timestamp.from((Instant) value));
                    default -> stmt.setNull(idColumnName, Types.BIGINT);
                }

            }

            if (!isInsert) {
                Object idValue = toSave.getId();
                switch (idValue) {
                    case Long l -> stmt.setLong(idColumnName + 1, l);
                    case Integer i -> stmt.setInt(idColumnName + 1, i);
                    case LocalDateTime localDateTime -> stmt.setLong(idColumnName + 1,  localDateTime.toEpochSecond(ZoneOffset.UTC));
                    case null, default -> stmt.setObject(idColumnName + 1, idValue);
                }

                stmt.executeUpdate();
            } else {
                stmt.executeUpdate();
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    ID generatedId = getGeneratedId(generatedKeys, 1);
                    toSave.setId(generatedId);
                }
            }

            return toSave;
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeResources(con, stmt, generatedKeys);
        }
    }

    @Override
    public T findById(ID id) {
        return null;
    }

    @Override
    public T deleteById(ID id) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public List<T> findByField(String column, String value) {
        return null;
    }


    public String getQueryUpdate(T object){
        String className = object.getClass().getSimpleName().toLowerCase() + "s";
        String closeSetUpdate = this.getCloseSetUpdate(object.getClass());
        String idFieldName = null;
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idFieldName = field.getName();
                break;
            }
        }

        if (idFieldName == null) {
            throw new IllegalArgumentException("No fields annotated with @Id found in class" + object.getClass().getName());
        }
        return String.format("UPDATE %s SET %s WHERE %s = ? ", this.toSnackCase(className), closeSetUpdate, this.toSnackCase(idFieldName));
    }



    public String getQueryInsert(T object){
        String className = object.getClass().getSimpleName().toLowerCase() + "s";
        String columns = this.getCloseColumnsInsert(object.getClass());
        String values = this.getCloseValueInsert(object.getClass().getDeclaredFields().length);
        return String.format("INSERT INTO %s (%s) VALUES (%s);", this.toSnackCase(className), columns, values);
    }


    private String toSnackCase(String pascalCaseOrCamelCase) {
        return Arrays.stream(pascalCaseOrCamelCase.split(""))
                .map(string -> (!string.equals(string.toUpperCase()) || pascalCaseOrCamelCase.indexOf(string) == 0) ? string.toLowerCase() : "_" + string.toLowerCase())
                .collect(Collectors.joining(""));
    }


    private String getCloseSetUpdate(Class<?> aClass) {
        StringBuilder closeSetUpdate = new StringBuilder();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Id.class)){
                closeSetUpdate.append(this.toSnackCase(field.getName())).append("=?, ");
            }
        }
        if (!closeSetUpdate.isEmpty()) {
            closeSetUpdate.setLength(closeSetUpdate.length() - 2);
        }
        return closeSetUpdate.toString();
    }


    private String getCloseColumnsInsert(Class<?> aClass) {
        StringBuilder closeColumnsInsert = new StringBuilder();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Id.class)){
                closeColumnsInsert.append(this.toSnackCase(field.getName())).append(", ");
            }
        }
        if (!closeColumnsInsert.isEmpty()) {
            closeColumnsInsert.setLength(closeColumnsInsert.length() - 2);
        }
        return closeColumnsInsert.toString();
    }

    private String getCloseValueInsert(int length) {
        return String.join(",", Collections.nCopies(length - 1, "?"));
    }


    private <ID> ID getGeneratedId(ResultSet generatedKeys, int columnIndex) throws SQLException {
        Object id = generatedKeys.getObject(columnIndex);
        if (id instanceof Long) {
            return (ID) id;
        } else if (id instanceof Integer) {
            return (ID) Long.valueOf((Integer) id);
        } else {
            throw new IllegalArgumentException("Unsupported ID type : " + id.getClass());
        }
    }

    private void closeResources(Connection con, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
          this.closeConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
