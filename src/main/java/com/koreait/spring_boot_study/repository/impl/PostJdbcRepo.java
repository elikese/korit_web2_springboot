package com.koreait.spring_boot_study.repository.impl;

import com.koreait.spring_boot_study.entity.Post;
import com.koreait.spring_boot_study.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class PostJdbcRepo implements PostRepo {

    // jdbc 라이브러리
    private final DataSource dataSource;

    @Autowired
    public PostJdbcRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Post rsToPost(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String content = rs.getString("content");
        Post post = new Post(id, title, content);
        return post;
    }

    private void close(AutoCloseable ac) {
        if(ac != null) {
            try {
                ac.close();
            } catch (Exception ignored) { }
        }
    }

    // 실습) findAllPosts를 작성해주세요!
    @Override
    public List<Post> findAllPosts() {
        String sql = "select id, title, content from post";
        List<Post> posts = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                Post post = rsToPost(rs);
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }

        return posts;
    }

    // 실습) findPostById 작성해주세요!
    @Override
    public Optional<Post> findPostById(int id) {
        String sql = "select id, title, content from post where id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null; // select 할때만

        try {
            conn = dataSource.getConnection(); // 도로를깔고
            ps = conn.prepareStatement(sql); // 화물차에 sql문을 실어서 도로에 넣는다

            // ? 대신에 값을 넣어주세요
            // sql에서 왼쪽부터 시작해서 1번째 나오는 ?에다가 매개변수로 들어온 id값을 넣어주세요
            ps.setInt(1, id);

            rs = ps.executeQuery(); // 화물차 출발하고 결과물(rs:ResultSet)을 가져온다.
            
            // while(rs.next()) -> rs(테이블)에 다음줄이 존재한다면 계속 실행하세요
            while(rs.next()) {
                Post targetPost = rsToPost(rs);
                return Optional.of(targetPost); // targetPost를 Optional로 감싸서 리턴
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 콘솔에 에러스택을 모두 출력
        } finally {
            close(rs); // 결과 반납
            close(ps); // 화물차 반납
            close(conn); // 도로 반납
        }
        
        return Optional.empty(); // Optional이 비어있다는것을 명시적으로 리턴
        // 옵셔널.orElseThrow(() -> new 예외클래스()) 작동한다
        // 옵셔널.isEmpty() -> true
        // 옵셔널.isPresent() -> false
    }

    @Override
    public int insertPost(String title, String content) {
        // 실습) insertPost를 완성해 주세요!
        String sql = "insert into post (title, content) values (?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, content);

            int successCount = ps.executeUpdate();
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
    public int deletePostById(int id) {
        /*
        entity 클래스이름은 테이블명을 파스칼케이스로 작성
        칼럼명은 스네이크케이스로 작성
        필드명은 카멜케이스로 작성
        */
        String sql = "delete from post where id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return 0;
    }

    @Override
    public int updatePost(int id, String title, String content) {
        // sql문을 문자열로 작성하는게 위험하다.
        String sql = "update post set title = ?, content = ? where id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return 0;
    }
}
