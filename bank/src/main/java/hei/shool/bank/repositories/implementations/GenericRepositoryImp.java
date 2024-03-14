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
                    case "String":
                        stmt.setString(idColumnName, (String) value);
                        break;
                    case "Integer":
                    case "int":
                        stmt.setInt(idColumnName, (Integer) value);
                        break;
                    case "Long":
                    case "long":
                        stmt.setLong(idColumnName, (Long) value);
                        break;
                    case "Double":
                    case "double":
                        stmt.setDouble(idColumnName, (Double) value);
                        break;
                    case "Date":
                        stmt.setTimestamp(idColumnName, new Timestamp(((Date) value).getTime()));
                        break;
                    case "LocalDateTime":
                        stmt.setTimestamp(idColumnName, Timestamp.valueOf((LocalDateTime) value));
                        break;
                    case "LocalDate":
                        stmt.setTimestamp(idColumnName, Timestamp.valueOf(((LocalDate) value).atStartOfDay()));
                        break;
                    case "Instant":
                        stmt.setTimestamp(idColumnName, Timestamp.from((Instant) value));
                        break;
                    default:
                        stmt.setNull(idColumnName, Types.BIGINT);
                }
            }
            if (!isInsert){
                stmt.setLong(idColumnName + 1, (Long) toSave.getId());
            }
            stmt.executeUpdate();

            if (isInsert) {
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
        return String.format("UPDATE %s SET %s WHERE id = ? ", this.toSnackCase(className), closeSetUpdate);
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
            if (!field.getName().equals("id")){
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
            throw new IllegalArgumentException("Type d'ID non pris en charge : " + id.getClass());
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
