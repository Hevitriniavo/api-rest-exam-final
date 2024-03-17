package hei.shool.bank.helpers;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class DatabaseHelper {
    public <T> String querySelectAll(Class<T> entityClass) {
        return String.format("SELECT %s FROM %s", getColumns(entityClass, true), getClassName(entityClass));
    }

    public <T> String querySelectById(Class<T> entityClass) {
        return String.format("SELECT %s FROM %s WHERE %s = ?", getColumns(entityClass, true), getClassName(entityClass), this.getIdentifyName(entityClass));
    }
    public <T> String querySelectByFieldName(Class<T> entityClass, String column) {
        return String.format("SELECT %s FROM %s WHERE %s = ?", getColumns(entityClass, true), getClassName(entityClass), this.toSnackCase(column));
    }

    public <T> String queryInsert(Class<T> entityClass) {
        return String.format("INSERT INTO %s ( %s) VALUES ( %s)", getClassName(entityClass), getColumns(entityClass, false), getValues(entityClass));
    }

    public <T> String queryUpdate(Class<T> entityClass) {
        return String.format("UPDATE %s SET %s WHERE %s = ?", getClassName(entityClass), getCloseSet(entityClass), getIdentifyName(entityClass));
    }

    public <T> String queryDeleteById(Class<T> entityClass) {
        return String.format("DELETE FROM %s WHERE %s = ?", getClassName(entityClass), getIdentifyName(entityClass));
    }

    private <T> String getIdentifyName(Class<T> entityClass) {
        for (Field field: entityClass.getDeclaredFields()){
            if (field.isAnnotationPresent(Id.class)){
                return toSnackCase(field.getName());
            }
        }
        return null;
    }
    private <T> String getCloseSet(Class<T> entityClass) {
        StringBuilder sets = new StringBuilder();
        for (Field field : entityClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(GeneratedValue.class)) {
                sets.append(this.toSnackCase(field.getName()))
                        .append(" = ?, ");
            }
        }
        if (!sets.isEmpty()) {
            sets.delete(sets.length() - 2, sets.length());
        }
        return sets.toString();
    }

    private <T> String getColumns(Class<T> entityClass, boolean isSelect){
        StringBuilder columns = new StringBuilder();
        for (Field field : entityClass.getDeclaredFields()) {
            if (isSelect || !field.isAnnotationPresent(GeneratedValue.class)) {
                columns.append(this.toSnackCase(field.getName()))
                        .append(", ");
            }
        }
        if (!columns.isEmpty()) {
            columns.delete(columns.length() - 2, columns.length());
        }
        return columns.toString();
    }
    public String toSnackCase(String pascalCaseOrCamelCase) {
        return Arrays.stream(pascalCaseOrCamelCase.split(""))
                .map(string -> (!string.equals(string.toUpperCase()) || pascalCaseOrCamelCase.indexOf(string) == 0) ? string.toLowerCase() : "_" + string.toLowerCase())
                .collect(Collectors.joining(""));
    }


    private <T> String getValues(Class<T> entityClass){
        StringBuilder values = new StringBuilder();
        for (Field field : entityClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(GeneratedValue.class)) {
                values.append("?, ");
            }
        }
        if (!values.isEmpty()) {
            values.delete(values.length() - 2, values.length());
        }
        return values.toString();
    }

    private <T> String getClassName(Class<T> entityClass) {
        String className = toSnackCase(entityClass.getSimpleName()).toLowerCase();
        if (className.endsWith("y")) {
            return className.substring(0, className.length() - 1) + "ies";
        } else {
            return className + "s";
        }
    }
    public void closeResources(PreparedStatement stmt, ResultSet generatedKeys) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (generatedKeys != null) {
                generatedKeys.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close resources", e);
        }
    }

    public void closeResources( PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close resources", e);
        }
    }
}

