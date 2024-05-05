package com.group.mission.repository.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
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

@Repository
public class FruitJdbcRepository implements FruitRepository {

    private static final String TABLE = "fruit";
    public static final RowMapper<Fruit> FRUIT_ROW_MAPPER = (rs, rowNum) -> new Fruit(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getObject("warehousingDate", LocalDate.class),
            rs.getLong("price"),
            rs.getBoolean("is_sold")
    );

    private final JdbcTemplate jdbcTemplate;

    public FruitJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 과일 정보 저장
     */
    @Override
    public Fruit save(Fruit fruit) {
        if (Objects.isNull(fruit.getId())) {
            return insert(fruit);
        }

        return update(fruit);
    }

    /**
     * ID로 과일 조회
     */
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

    /**
     * 특정 과일 기준으로 판린 금액, 팔리지 않은 금액 조회
     *
     * @param name 특정 과일 이름
     */
    @Override
    public FruitStatisticResponse findStatistic(String name) {
        String sql = String.format("SELECT is_sold, SUM(price) AS total_amount FROM %s WHERE name = ? GROUP BY is_sold", TABLE);

        return jdbcTemplate.query(
                sql,
                ps -> ps.setString(1, name),
                this::createFruitStatisticResponse
        );
    }

    /**
     * 쿼리 결과로 DTO{@link FruitStatisticResponse} 생성
     *
     * @param rs 쿼리 결과
     */
    private FruitStatisticResponse createFruitStatisticResponse(ResultSet rs) throws SQLException {
        long salesAmount = 0;
        long notSalesAmount = 0;

        while (rs.next()) {
            if (rs.getBoolean("is_sold")) {
                salesAmount += rs.getLong("total_amount");
            } else {
                notSalesAmount += rs.getLong("total_amount");
            }
        }

        return new FruitStatisticResponse(salesAmount, notSalesAmount);
    }

    /**
     * 과일 정보 삽입
     *
     * @return ID 값을 포함한 과일 정보
     */
    private Fruit insert(Fruit fruit) {
        String sql = String.format("INSERT INTO %s (name, warehousingDate, price, is_sold) VALUES (?, ?, ?, ?)", TABLE);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, fruit.getName());
                    ps.setObject(2, fruit.getWarehousingDate());
                    ps.setLong(3, fruit.getPrice());
                    ps.setBoolean(4, fruit.isSold());
                    return ps;
                },
                keyHolder);
        return createFruit(keyHolder.getKey(), fruit);
    }

    /**
     * 과일 도메인{@link Fruit} 생성(ID가 Null인 경우 예외발생)
     *
     * @param key 삽입된 ID
     */
    private Fruit createFruit(Number key, Fruit fruit) {
        if (Objects.isNull(key)) {
            throw new RuntimeException("데이터 삽입에 실패하였습니다.");
        }

        return new Fruit(
                key.longValue(),
                fruit.getName(),
                fruit.getWarehousingDate(),
                fruit.getPrice(),
                fruit.isSold()
        );
    }

    /**
     * 과일 정보 수정
     */
    private Fruit update(Fruit fruit) {
        String sql = String.format("UPDATE %s SET name = ?, warehousingDate = ?, price = ?, is_sold = ? WHERE id = ?", TABLE);

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, fruit.getName());
            ps.setObject(2, fruit.getWarehousingDate());
            ps.setLong(3, fruit.getPrice());
            ps.setBoolean(4, fruit.isSold());
            ps.setLong(5, fruit.getId());
            return ps;
        });
        return fruit;
    }
}
