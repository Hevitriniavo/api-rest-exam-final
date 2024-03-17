

package hei.shool.bank.repositories.implementations;


import hei.shool.bank.annotations.Id;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.CrudOperations;
import hei.shool.bank.repositories.Identifiable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class CrudOperationsImplement<T extends Identifiable<ID>, ID> implements CrudOperations<T, ID> {
    private final Class<T> entityClass;
    private final Connection connection;
    private final DatabaseHelper databaseHelper;

    public CrudOperationsImplement(DatabaseHelper databaseHelper, Connection connection) {
        this.databaseHelper = databaseHelper;
        this.connection = connection;
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType parameterizedType) {
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments != null && typeArguments.length > 0) {
                this.entityClass = (Class<T>) typeArguments[0];
            } else {
                throw new IllegalArgumentException("No type argument provided for GenericRepository");
            }
        } else {
            throw new IllegalArgumentException("No type argument provided for GenericRepository");
        }
    }

    @Override
    public T saveOrUpdate(T toSave) {
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {
            boolean isInsert = toSave.getId() == null;
            String query = isInsert ? databaseHelper.queryInsert(entityClass) : databaseHelper.queryUpdate(entityClass);
            stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            setParameters(stmt, toSave, isInsert);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert or update operation failed, no rows affected.");
            }
            if (isInsert) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    ID generatedId = getGeneratedId(generatedKeys);
                    toSave.setId(generatedId);
                } else {
                    throw new SQLException("Insert operation failed, no generated keys obtained.");
                }
            }
            return toSave;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseHelper.closeResources(stmt, generatedKeys);
        }
    }

    @Override
    public T findById(ID id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = databaseHelper.querySelectById(entityClass);
            stmt = connection.prepareStatement(query);
            stmt.setObject(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEntity(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing findById operation", e);
        } finally {
            databaseHelper.closeResources(stmt, rs);
        }
    }


    @Override
    public T deleteById(ID id) {
        PreparedStatement stmt = null;
        T entity = this.findById(id);

        try {
            String query = databaseHelper.queryDeleteById(entityClass);
            stmt = connection.prepareStatement(query);
            stmt.setObject(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return entity;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing deleteById operation", e);
        } finally {
            databaseHelper.closeResources(stmt);
        }
    }


    @Override
    public List<T> findAll() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<T> resultList = new ArrayList<>();

        try {
            String query = databaseHelper.querySelectAll(entityClass);
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                T entity = this.mapResultSetToEntity(rs);
                resultList.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing findAll operation", e);
        } finally {
            databaseHelper.closeResources(stmt, rs);
        }

        return resultList;
    }

    private T mapResultSetToEntity(ResultSet rs) throws SQLException {
        try {
            Constructor<T> constructor = entityClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T entity = constructor.newInstance();
            for (Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);
                String columnName = databaseHelper.toSnackCase(field.getName());
                Object value = rs.getObject(columnName);

                if (value instanceof java.sql.Date && field.getType() == LocalDate.class) {
                    LocalDate localDate = ((java.sql.Date) value).toLocalDate();
                    field.set(entity, localDate);
                } else if (field.getType() == Double.class && value instanceof Float) {
                    Double doubleValue = ((Float) value).doubleValue();
                    field.set(entity, doubleValue);
                } else {
                    field.set(entity, value);
                }
            }

            return entity;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Error while mapping ResultSet to entity", e);
        }
    }



    @Override
    public List<T> findByField(String column, String value) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<T> resultList = new ArrayList<>();

        try {
            String query = databaseHelper.querySelectByFieldName(entityClass, column);
            stmt = connection.prepareStatement(query);
            stmt.setString(1, value);
            rs = stmt.executeQuery();

            while (rs.next()) {
                T entity = mapResultSetToEntity(rs);
                resultList.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing findByField operation", e);
        } finally {
            databaseHelper.closeResources(stmt, rs);
        }

        return resultList;
    }


    private ID getGeneratedId(ResultSet generatedKeys) {
            try {
                return (ID) generatedKeys.getObject(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }



    private void setParameters(PreparedStatement stmt, T toSave, boolean isInsert) {
        int index = 1;
        for (Field field : toSave.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Id.class)) {
                try {
                    stmt.setObject(index++, field.get(toSave));
                } catch (SQLException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (!isInsert) {
            try {
                stmt.setObject(index, toSave.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}



