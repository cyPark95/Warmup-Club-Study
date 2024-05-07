package com.group.mission.repository.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.domain.FruitStatus;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
public class FruitMySqlRepository implements FruitRepository {

    private static final String TABLE = "fruit";
    public static final RowMapper<Fruit> FRUIT_ROW_MAPPER = (rs, rowNum) -> new Fruit(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getObject("warehousingDate", LocalDate.class),
            rs.getLong("price"),
            FruitStatus.valueOf(rs.getString("status"))
    );

    private final JdbcTemplate jdbcTemplate;

    public FruitMySqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Fruit save(Fruit fruit) {
        if (Objects.isNull(fruit.getId())) {
            return insert(fruit);
        }

        return update(fruit);
    }

    @Override
    public Optional<Fruit> findById(Long id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", TABLE);

        List<Fruit> fruit = jdbcTemplate.query(
                sql,
                ps -> ps.setLong(1, id),
                FRUIT_ROW_MAPPER
        );
        return DataAccessUtils.optionalResult(fruit);
    }

    @Override
    public FruitStatisticResponse findStatistic(String name) {
        String sql = String.format("SELECT status, SUM(price) AS total_amount FROM %s WHERE name = ? GROUP BY status", TABLE);

        return jdbcTemplate.query(
                sql,
                ps -> ps.setString(1, name),
                this::createFruitStatisticResponse
        );
    }

    private FruitStatisticResponse createFruitStatisticResponse(ResultSet rs) throws SQLException {
        long salesAmount = 0;
        long notSalesAmount = 0;

        while (rs.next()) {
            String status = rs.getString("status");
            if (FruitStatus.valueOf(status) == FruitStatus.SOLD) {
                salesAmount += rs.getLong("total_amount");
            } else {
                notSalesAmount += rs.getLong("total_amount");
            }
        }

        return new FruitStatisticResponse(salesAmount, notSalesAmount);
    }

    private Fruit insert(Fruit fruit) {
        String sql = String.format("INSERT INTO %s (name, warehousingDate, price, status) VALUES (?, ?, ?, ?)", TABLE);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, fruit.getName());
                    ps.setObject(2, fruit.getWarehousingDate());
                    ps.setLong(3, fruit.getPrice());
                    ps.setString(4, fruit.getStatus().name());
                    return ps;
                },
                keyHolder);
        return createFruit(keyHolder.getKey(), fruit);
    }

    private Fruit createFruit(Number key, Fruit fruit) {
        if (Objects.isNull(key)) {
            throw new RuntimeException("데이터 삽입에 실패하였습니다.");
        }

        return new Fruit(
                key.longValue(),
                fruit.getName(),
                fruit.getWarehousingDate(),
                fruit.getPrice(),
                fruit.getStatus()
        );
    }

    private Fruit update(Fruit fruit) {
        String sql = String.format("UPDATE %s SET name = ?, warehousingDate = ?, price = ?, status = ? WHERE id = ?", TABLE);

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, fruit.getName());
            ps.setObject(2, fruit.getWarehousingDate());
            ps.setLong(3, fruit.getPrice());
            ps.setString(4, fruit.getStatus().name());
            ps.setLong(5, fruit.getId());
            return ps;
        });
        return fruit;
    }
}
