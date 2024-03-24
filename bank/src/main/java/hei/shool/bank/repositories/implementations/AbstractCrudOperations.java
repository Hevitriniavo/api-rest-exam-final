

package hei.shool.bank.repositories.implementations;


import hei.shool.bank.annotations.Id;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.helpers.Paginate;
import hei.shool.bank.repositories.CrudOperations;
import hei.shool.bank.repositories.Identifiable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractCrudOperations<T extends Identifiable<ID>, ID> implements CrudOperations<T, ID> {
    private final Class<T> entityClass;
    private final Connection connection;
    private final DatabaseHelper databaseHelper;

    public AbstractCrudOperations(DatabaseHelper databaseHelper, Connection connection) {
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
                    return this.findById(generatedId).orElse(null);
                } else {
                    throw new SQLException("Insert operation failed, no generated keys obtained.");
                }
            }
            return toSave;
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing saveOrUpdate operation", e);
        } finally {
            databaseHelper.closeResources(stmt, generatedKeys);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = databaseHelper.querySelectById(entityClass);
            stmt = connection.prepareStatement(query);
            stmt.setObject(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToEntity(rs));
            } else {
                return Optional.empty();
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
        Optional<T> entityOptional = this.findById(id);

        try {
            if (entityOptional.isPresent()) {
                String query = databaseHelper.queryDeleteById(entityClass);
                stmt = connection.prepareStatement(query);
                stmt.setObject(1, id);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    return entityOptional.get();
                }
            }
            return null;
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
            System.out.println("Error while executing findAll operation "+ e);
        } finally {
            databaseHelper.closeResources(stmt, rs);
        }

        return resultList;
    }


    @Override
    public Optional<T> findByField(String column, String value) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = databaseHelper.querySelectByFieldName(entityClass, column);
            stmt = connection.prepareStatement(query);
            stmt.setObject(1, value);
            rs = stmt.executeQuery();

            if (rs.next()) {
                T entity = mapResultSetToEntity(rs);
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            System.out.println("Error while executing findByField operation "+ e);
        } finally {
            databaseHelper.closeResources(stmt, rs);
        }

        return Optional.empty();
    }

    @Override
    public Paginate<T> pagination(Long pageNumber, Long pageSize) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<T> resultList = new ArrayList<>();

        try {
            String query = databaseHelper.querySelectAll(entityClass) + " LIMIT ? OFFSET ?";
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, pageSize);
            stmt.setLong(2, (pageNumber - 1) * pageSize);
            rs = stmt.executeQuery();

            while (rs.next()) {
                T entity = this.mapResultSetToEntity(rs);
                resultList.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing pagination operation", e);
        } finally {
            databaseHelper.closeResources(stmt, rs);
        }

        long totalRowCount = getTotalRowCount();
        int totalPages = (int) Math.ceil((double) totalRowCount / pageSize);
        Long nextPage = (pageNumber < totalPages) ? pageNumber + 1 : -1;
        Long previousPage = (pageNumber > 1) ? pageNumber - 1 : -1;

        return new Paginate<>(totalRowCount, nextPage, previousPage, resultList);
    }



    @Override
    public Long getTotalRowCount() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = databaseHelper.getQueryCount(entityClass);
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0L;
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing getTotalRowCount operation", e);
        } finally {
            databaseHelper.closeResources(stmt, rs);
        }
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
                    Object value = field.get(toSave);
                    if (value instanceof Enum) {
                        stmt.setString(index++, value.toString());
                    } else {
                        stmt.setObject(index++, value);
                    }
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

    private T mapResultSetToEntity(ResultSet rs) throws SQLException {
        try {
            Constructor<T> constructor = entityClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T entity = constructor.newInstance();
            for (Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);
                String columnName = databaseHelper.toSnackCase(field.getName());
                Object value = rs.getObject(columnName);

                if (value != null) {
                    if (field.getType().isEnum()) {
                        Enum<?> enumValue = Enum.valueOf((Class<Enum>) field.getType(), value.toString());
                        field.set(entity, enumValue);
                    } else if (field.getType() == BigDecimal.class && value instanceof Float) {
                        BigDecimal bigDecimalValue = BigDecimal.valueOf((Float) value);
                        field.set(entity, bigDecimalValue);
                    }else if (value instanceof java.sql.Date && field.getType() == LocalDate.class) {
                        LocalDate localDate = ((java.sql.Date) value).toLocalDate();
                        field.set(entity, localDate);
                    } else if (value instanceof java.sql.Timestamp && field.getType() == LocalDateTime.class) {
                        LocalDateTime localDateTime = ((java.sql.Timestamp) value).toLocalDateTime();
                        field.set(entity, localDateTime);
                    } else if (field.getType() == Double.class && value instanceof BigDecimal) {
                        Double doubleValue = ((BigDecimal) value).doubleValue();
                        field.set(entity, doubleValue);
                    } else if (field.getType() == Double.class && value instanceof Float) {
                        Double doubleValue = ((Float) value).doubleValue();
                        field.set(entity, doubleValue);
                    } else {
                        field.set(entity, value);
                    }
                }
            }

            return entity;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Error while mapping ResultSet to entity", e);
        }
    }
}




