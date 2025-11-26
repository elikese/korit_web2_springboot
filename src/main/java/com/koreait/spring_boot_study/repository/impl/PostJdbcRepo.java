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
        return Optional.empty();
    }

    @Override
    public int insertPost(String title, String content) {
        return 0;
    }

    @Override
    public int deletePostById(int id) {
        return 0;
    }

    @Override
    public int updatePost(int id, String title, String content) {
        return 0;
    }
}
