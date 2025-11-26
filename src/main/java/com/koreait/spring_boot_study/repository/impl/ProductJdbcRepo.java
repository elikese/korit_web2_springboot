package com.koreait.spring_boot_study.repository.impl;

import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Qualifier("jdbc")
@Repository
public class ProductJdbcRepo implements ProductRepo {

    // DB 경로 or 비밀번호와 같이 민감한 정보들을 소스코드로 노출되지 않게
    // yaml에 적어둔 DB 설정값을 스프링이 자동으로 읽어서
    // 그 값을 가진 DataSource 객체를 자동으로 만들어 Bean으로 등록해준다.
    private final DataSource dataSource;

    @Autowired
    public ProductJdbcRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void close(AutoCloseable ac) {
        // conn, ps, rs -> AutoCloseable이라는 인터페이스를 이식받고 있음
        // 그 인테페이스에서 close라는 추상메서드가 있음
        if(ac != null) {
            try {
                ac.close();
            } catch (Exception ig) {}
        }
    }

    public List<Product> findAllProducts() {
        // 리턴해줄 List
        List<Product> products = new ArrayList<>();
        // db로 sql 전송 / 응답받기...

        // DB와 실제 연결을 수행하는 객체
        Connection conn = null;
        // Connection의 필드로 주입되어서 DB로 전송될 sql객체
        PreparedStatement ps = null;
        // DB에서 가져온 데이터를 자바에서 읽기 좋은 형태(자바객체)로 제공하는 객체
        // select 할때만 필요하다! -> 테이블을 결과로 받을때만 필요
        ResultSet rs = null;
        // DB에서 올라오는 에러를 try-catch로 잡아줘야한다.
        String sql = "select product_id, product_name, product_price from product";
        try{
            // db에서 제공하는 연결을 하나 대여해온다.
            conn = dataSource.getConnection();
            // preparedStatement에는 실제 문자열로 sql 쿼리가 들어가야 한다.
            ps = conn.prepareStatement(sql);
            // 작성한 ps를 db에 전달하고, 실행시킨 결과를 가져온다.
            // DB에서 조회한 결과를 rs안에 테이블 형태로 들고온다고 보면 된다.
            rs = ps.executeQuery(); // select -> executeQuery(): rs를 리턴
            // rs.next()는 테이블에서 한줄씩 읽어올건데, 그 다음 줄이 존재하는지 검사하는 메서드
            while(rs.next()) {
                // next가 true면 해당 줄의 컬럼 값들을 가져올 수 있음
                // product_id 컬럼값을 읽어 오세요
                int id = rs.getInt("product_id");
                // product_name 컬럼값을 읽어 오세요
                String name = rs.getString("product_name");
                // product_price 컬럼값을 읽어 오세요
                int price = rs.getInt("product_price");
                Product product = new Product(id, name, price);
                products.add(product);
            }

        } catch (SQLException e) {
            // String sql -> sql을 잘 못 작성하였거나, DB 에러처리
            e.printStackTrace(); // DB에러들을 출력
        } finally {
            // 에러가 일어나도, 에러가 일어나지 않아도 실행되는 코드블럭
            // 대여했던 객체들을 반납
            // 순서중요!) rs -> ps -> conn 순서로 close!
            close(rs); // 1등
            close(ps); // 2등
            close(conn); // 3등
        }

        return products;
    }

    public String findProductNameById(int id) {
        String sql = "select product_name from product where product_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            // sql 문자열 왼쪽부터 쭉 스캔해서 1번째로 나오는 ?에다가
            // 매개변수로 들어온 id값을 넣으세요
            // sql injection 공격 방지
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getString("product_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }

        return "해당 id의 상품은 존재하지 않습니다.";
    }

    @Override
    public int insertProduct(String name, int price) {
        String sql = "insert into product (product_name, product_price) values (?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);

            // sql문자열 왼쪽부터 스캔해서 1번째 나오는 ?에 매개변수 name값 주입
            ps.setString(1, name);
            // sql문자열 왼쪽부터 스캔해서 2번째 나오는 ?에 매개변수 price값 주입
            ps.setInt(2, price);
            
            int successCount = ps.executeUpdate(); // 영향받은 row의 수
            return successCount;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }

        return 0;
    }

    @Override
    public int deleteProductById(int id) {
        return 0;
    }

    @Override
    public int updateProduct(int id, String name, int price) {
        return 0;
    }

}
