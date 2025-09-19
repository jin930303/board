package com.example.board;

import com.example.board.entity.Board2Entity;
import com.example.board.entity.Board3Entity;
import com.example.board.entity.Board5Entity;
import com.example.board.repository.Board2Repository;
import com.example.board.repository.Board3Repository;
import com.example.board.repository.Board5Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class BoardApplicationTests {



	@Autowired
	Board2Repository board2Repository;

	@Autowired
	Board3Repository board3Repository;

	@Autowired
	Board5Repository board5Repository;

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

	@Test
	void insertData2(){
		for(int i =0; i<=300; i++){
			Board3Entity board3Entity = new Board3Entity();
			board3Entity.setTitle(String.format("테스트 데이터[%03d]",i));
			board3Entity.setContent("내용");
			board3Entity.setAuthor(String.format("user[%03d]",i));
			board3Entity.setInputDate(LocalDateTime.now());

			this.board3Repository.save(board3Entity);
		}

	}

	@Test
	void insertBoard5(){
		for(int i = 0;i<=300;i++){
			Board5Entity entity = new Board5Entity();
			entity.setTitle(String.format("테스트 데이터[%03d]",i));
			entity.setContent(String.format("내용[%03d]",i));
			entity.setAuthor(String.format("작성자[%03d]",i));
			entity.setInputDate(LocalDateTime.now());

			this.board5Repository.save(entity);
		}
	}

}
