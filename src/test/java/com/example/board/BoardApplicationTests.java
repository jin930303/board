package com.example.board;

import com.example.board.entity.Board2Entity;
import com.example.board.repository.Board2Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class BoardApplicationTests {



	@Autowired
	Board2Repository board2Repository;

	@Test
	void insertData() {
		for (int i = 1; i <= 300; i++) {

			Board2Entity board2Entity = new Board2Entity();
			board2Entity.setTitle(String.format("테스트 데이터:[%03d]", i));
			board2Entity.setContent("내용");
			board2Entity.setAuthor(String.format("user[%03d]", i));
			board2Entity.setInputDate(LocalDateTime.now());

			this.board2Repository.save(board2Entity);

		}
	}
}
