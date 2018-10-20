package xg.inclass.second_springboot.common;

import org.apache.ibatis.annotations.Case;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookStatusTypeHandler implements TypeHandler<BookStatus> {

    @Override
    public void setParameter(PreparedStatement ps, int i, BookStatus parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public BookStatus getResult(ResultSet rs, String columnName) throws SQLException {
        BookStatus result = null;
        switch (rs.getInt(columnName)) {
            case 1:
                result = BookStatus.SALE;
                break;
            case 0:
                result = BookStatus.ON_SALE;
                break;
            default:
                result = BookStatus.ON_SALE;
        }
        return result;
    }

    @Override
    public BookStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
        BookStatus result = null;
        switch (rs.getInt(columnIndex)) {
            case 1:
                result = BookStatus.SALE;
                break;
            case 0:
                result = BookStatus.ON_SALE;
                break;
            default:
                result = BookStatus.ON_SALE;
        }
        return result;
    }

    @Override
    public BookStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
        BookStatus result = null;
        switch (cs.getInt(columnIndex)) {
            case 1:
                result = BookStatus.SALE;
                break;
            case 0:
                result = BookStatus.ON_SALE;
                break;
            default:
                result = BookStatus.ON_SALE;
        }
        return result;
    }
}
